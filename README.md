# TaskScheduler

可控制任务依赖关系的任务调度器，并可自动对无依赖关系的任务异步同时执行，加快执行速度。

笔者主要应用于 Android 多模块间的初始化任务调度和优化 APP 启动速度。
## 使用方式

#### 构建任务

```java
public class TaskB extends Task {

    @Override
    public String getTaskName() {
        return TaskNameConstant.TASKB;
    }

    @Nullable
    @Override
    public List<String> getDependsTaskNames() {
        // 添加该任务依赖的其它任务名
        ArrayList<String> dependsTaskNames = new ArrayList<>();
        dependsTaskNames.add(TaskNameConstant.TASKA);
        return dependsTaskNames;
    }

    @Override
    public void configure() {
        // 执行在所有任务开启之前
    }

    @Override
    public void execute() {
        // 任务执行的具体逻辑
    }

}
```

#### 执行任务

```java
List<Task> tasks = new ArrayList<>(16);
// 此处模拟添加任务
TaskScheduler.newBuilder()
        .taskList(tasks)
        .build()
        .execute();
```

## 安装

```gradle
implementation 'me.passin:taskscheduler:0.0.1'
```

## License

    Copyright (C) 2019 Passin

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
