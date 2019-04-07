package me.passin.taskscheduler;

import android.support.annotation.Nullable;
import java.util.List;

/**
 * @author: zbb 33775
 * @date: 2019/4/7 14:55
 * @desc:
 * 有可能需要依赖其他任务先执行的，
 * 或有可能被其他任务依赖执行的，请使用该类
 */
public abstract class Task {

    public abstract String getTaskName();

    @Nullable
    public abstract List<String> getDependsTaskNames();

    public void configure(){}

    public abstract void execute();

}
