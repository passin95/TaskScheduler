package me.passin.taskscheduler.sample.task;

import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;
import me.passin.taskscheduler.Task;
import me.passin.taskscheduler.TaskScheduler;
import me.passin.taskscheduler.sample.TaskNameConstant;

/**
 * @author: zbb 33775
 * @date: 2019/4/7 15:20
 * @desc:
 */
public class TaskO extends Task {

    @Override
    public String getTaskName() {
        return TaskNameConstant.TASKO;
    }

    @Nullable
    @Override
    public List<String> getDependsTaskNames() {
        return null;
    }

    @Override
    public void configure() {
        Log.i(TaskScheduler.TAG, "configure：" + getTaskName());
    }

    @Override
    public void execute() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TaskScheduler.TAG, "execute：" + getTaskName() + " Thread：" + Thread.currentThread().getName()+ " Sleep"
                + "：20ms");
    }
}
