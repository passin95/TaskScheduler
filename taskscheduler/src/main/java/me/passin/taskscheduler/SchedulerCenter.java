package me.passin.taskscheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: zbb 33775
 * @date: 2019/4/6 23:35
 * @desc:
 */
public class SchedulerCenter {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private Map<String, Task> mTaskMap;
    private DirectedAcyclicGraph<Task> mDirectedAcyclicGraph;
    private List<Task> mTasks;
    private List<Task> mNoDependentTasks;
    private ExecutorService mExecutorService;
    private CountDownLatch mCounter;

    SchedulerCenter(List<Task> tasks, ExecutorService executorService) {
        mTasks = Collections.unmodifiableList(tasks);
        mDirectedAcyclicGraph = new DirectedAcyclicGraph<>(mTasks.size());
        mTaskMap = new HashMap<>(mTasks.size());
        initData();
        initExecutorService(executorService);
    }

    private void initData() {
        // 打上标记
        for (Task task : mTasks) {
            mTaskMap.put(task.getTaskName(), task);
            task.configure();
        }
        // 获取依赖关系的任务
        for (Task task : mTasks) {
            List<String> dependsTaskNames = task.getDependsTaskNames();
            if (dependsTaskNames != null) {
                for (String dependsTaskName : dependsTaskNames) {
                    mDirectedAcyclicGraph.addEdge(task, mTaskMap.get(dependsTaskName));
                }
            }
        }

        // 获取无依赖关系的任务
        mNoDependentTasks = new ArrayList<>(mTasks.size() - mDirectedAcyclicGraph.size());
        for (Task task : mTasks) {
            if (!mDirectedAcyclicGraph.contains(task)) {
                mNoDependentTasks.add(task);
            }
        }
        mNoDependentTasks = Collections.unmodifiableList(mNoDependentTasks);
    }

    private void initExecutorService(ExecutorService executorService) {
        if (executorService == null) {
            int corePoolSize = Math.min(mNoDependentTasks.size() / 2, CPU_COUNT / 2);
            int maximumPoolSize = Math.max(1, corePoolSize * 2);
            mExecutorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 30L, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(16), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread result = new Thread(runnable, "Task Scheduler Executor");
                    result.setDaemon(false);
                    return result;
                }
            });
        } else {
            mExecutorService = executorService;
        }
    }

    public void execute() {
        // 先执行可异步的任务再执行必须在 main 线程的任务
        exexuteNoDependentTasks();
        exexuteDependentTasks();
        if (mCounter != null) {
            try {
                mCounter.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void exexuteDependentTasks() {
        if (mDirectedAcyclicGraph.size() == 0) {
            return;
        }
        ArrayList<Task> sortedList = mDirectedAcyclicGraph.getSortedList();
        for (Task task : sortedList) {
            task.execute();
        }
    }

    private void exexuteNoDependentTasks() {
        int size = mNoDependentTasks.size();
        if (size == 0) {
            return;
        }
        mCounter = new CountDownLatch(size);
        for (final Task task : mNoDependentTasks) {
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    task.execute();
                    mCounter.countDown();
                }
            });
        }
    }

}
