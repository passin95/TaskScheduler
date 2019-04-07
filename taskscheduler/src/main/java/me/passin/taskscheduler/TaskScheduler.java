package me.passin.taskscheduler;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import me.passin.taskscheduler.utils.Preconditions;

/**
 * @author: zbb 33775
 * @date: 2019/4/5 21:58
 * @desc:
 */
public class TaskScheduler {

    public static final String TAG = "TaskScheduler";

    private SchedulerCenter mSchedulerCenter;

    private TaskScheduler(Builder build) {
        mSchedulerCenter = new SchedulerCenter(build.mDependentTaskList, build.mExecutorService);
    }

    public void execute() {
        mSchedulerCenter.execute();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private List<Task> mDependentTaskList;
        private ExecutorService mExecutorService;

        public Builder taskList(@NonNull List<Task> dependentTaskList) {
            Preconditions.checkNotNull(dependentTaskList, "dependentTaskList == null");
            mDependentTaskList = dependentTaskList;
            return this;
        }

        public Builder addTask(@NonNull Task task) {
            Preconditions.checkNotNull(task, "task == null");
            if (mDependentTaskList == null) {
                mDependentTaskList = new ArrayList<>();
            }
            mDependentTaskList.add(task);
            return this;
        }

        public Builder executorService(@Nullable ExecutorService executorService) {
            mExecutorService = executorService;
            return this;
        }

        public TaskScheduler build() {
            Preconditions.checkNotNull(mDependentTaskList, "No tasks were added");
            return new TaskScheduler(this);
        }

    }

}