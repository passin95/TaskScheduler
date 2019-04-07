/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.passin.taskscheduler.sample;

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import me.passin.taskscheduler.Task;
import me.passin.taskscheduler.TaskScheduler;
import me.passin.taskscheduler.sample.task.TaskA;
import me.passin.taskscheduler.sample.task.TaskB;
import me.passin.taskscheduler.sample.task.TaskC;
import me.passin.taskscheduler.sample.task.TaskD;
import me.passin.taskscheduler.sample.task.TaskE;
import me.passin.taskscheduler.sample.task.TaskF;
import me.passin.taskscheduler.sample.task.TaskG;
import me.passin.taskscheduler.sample.task.TaskH;
import me.passin.taskscheduler.sample.task.TaskI;
import me.passin.taskscheduler.sample.task.TaskJ;
import me.passin.taskscheduler.sample.task.TaskK;
import me.passin.taskscheduler.sample.task.TaskL;
import me.passin.taskscheduler.sample.task.TaskM;
import me.passin.taskscheduler.sample.task.TaskN;
import me.passin.taskscheduler.sample.task.TaskO;
import me.passin.taskscheduler.sample.task.TaskP;

/**
 * @author : passin
 * @date: 2019/4/15 16:35
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.enableDefaults();

        long startTime = System.currentTimeMillis();

        List<Task> tasks = new ArrayList<>(16);
        tasks.add(new TaskA());
        tasks.add(new TaskB());
        tasks.add(new TaskC());
        tasks.add(new TaskD());
        tasks.add(new TaskE());
        tasks.add(new TaskF());
        tasks.add(new TaskG());
        tasks.add(new TaskH());
        tasks.add(new TaskI());
        tasks.add(new TaskJ());
        // 以下任务不依赖其它任务，不被其他任务使用，会放在其它线程执行。
        tasks.add(new TaskK());
        tasks.add(new TaskL());
        tasks.add(new TaskM());
        tasks.add(new TaskN());
        tasks.add(new TaskO());
        tasks.add(new TaskP());

        TaskScheduler.newBuilder()
                .taskList(tasks)
                .build()
                .execute();
        long endTime = System.currentTimeMillis();

        Log.i(TaskScheduler.TAG, "总共耗时：" + (endTime - startTime) + "ms");

    }

}
