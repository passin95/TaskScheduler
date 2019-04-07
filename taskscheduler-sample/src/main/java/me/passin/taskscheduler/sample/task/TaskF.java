package me.passin.taskscheduler.sample.task;

import android.support.annotation.Nullable;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import me.passin.taskscheduler.Task;
import me.passin.taskscheduler.TaskScheduler;
import me.passin.taskscheduler.sample.TaskNameConstant;

/**
 * @author: zbb 33775
 * @date: 2019/4/7 9:00
 * @desc:
 */
public class TaskF extends Task {

    @Override
    public String getTaskName() {
        return TaskNameConstant.TASKF;
    }

    @Nullable
    @Override
    public List<String> getDependsTaskNames() {
        List<String> dependsTaskNames = new ArrayList<>(2);
        dependsTaskNames.add(TaskNameConstant.TASKD);
        dependsTaskNames.add(TaskNameConstant.TASKE);
        return dependsTaskNames;
    }

    @Override
    public void configure() {
        Log.i(TaskScheduler.TAG, "configure：" + getTaskName());
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TaskScheduler.TAG, "execute：" + getTaskName() + " Thread：" + Thread.currentThread().getName()+" Sleep"
                + "：200ms");
    }
}
