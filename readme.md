# Android 应用架构组件

标签（空格分隔）： Android Architecture Components Lifecycles ViewModel LiveData

---
## 前言
无论是什么应用的开发过程，都无法摆脱‘界面’、‘数据源’、‘数据的业务逻辑’三个部分以及这三个部分之间的爱恨纠葛。Android的应用开发亦是如此。google为了方便Android的应用开发者，已经提供非常优秀的Framework架构，开发者只需要遵循其架构规范就能够比较容易的开发出能够正常运行的Android应用。

不过，在实际的开发情况中，出现了一个非常普遍的问题：庞大的类，特别是庞大的UI类（Activity或者Fragment）。庞大的UI类给代码的质量带来很大的隐患：可读性差、可维护性差、高耦合导致的健壮性差。为了更好指导应用开发者开发出高质量的应用程序，简化开发流程，google在Android平台上发布了**Android架构组件（Android Architecture Components）**
      
---
## 架构组件简介
Android架构组件中主要包含如下组件：

 1. Lifecycles： 生命周期管理，该组件是其它组件的基础，可由于跟踪UI的（Activity和Fragment）的生命周期
 2. LiveData： 一种可以被观察的以及可以感知生命周期的数据容器。
 3. ViewModel： UI，例如Activity、Fragment与数据之间的桥梁；可以在其内部处理数据业务逻辑,例如从网络层或者数据持久层获取数据、更新数据等。
 4. Room： 一个简单好用的对象映射层；其对SqliteDatabase进行了封装，简化开发者对于数据持久层的开发工作量

如下图,是Google推荐的Android应用程序的基础架构：
![Android 应用程序的基础架构](https://developer.android.google.cn/topic/libraries/architecture/images/final-architecture.png)

---
## 生命周期管理组件(Lifecycle)
Lifecycle组件的重要目的是实现UI组件生命周期相关的逻辑控制与UI组件的生命周期方法的解耦。通俗的说，在以往实现生命周期管理的时候，总是需要往Activity或者Fragment中塞一堆的代码，在使用Lifecycle组件后会大大改善这种情况。

Lifecycle组件的按照观察者模式进行设计与实现。当UI组件的生命周期状态发生变化的时候，Lifecycle会通知已经注册的观察者对象正在执行的生命周期方法。

---
### Lifecycle组件的类
生命周期管理组件主要由以下几个类和接口组成：

 1. `Lifecycle` ： 该类是整个Lifecycle组件管家的核心类，用于增加或者移除观察者，以及获取当前生命周期的状态
 2. `LifecycleOwner`：该接口是一个重要的辅助接口，用于获取与UI组件关联的Lifecycle对象；在Android的支持包中，AppCompatActivity和Fragment实现了该接口
 3. `LifecycleObserver`：该接口是一个标记接口，要进行生命周期管理的类需要实现该接口。直接实现该接口的类，需要使用相关的事件（Event）注解来申明相应生命周期的方法。
 4. `DefaultLifecycleObserver`: 该接口是`LifecycleObserver`的子接口，如果是在Java 8的变异环境下，可以用该接口替代直接`LifecycleObserver`接口，从而避免使用**注解（annotations）**来申明周期方法.
 5. `LifecycleRegistry`: 该类是`Lifecycle`的具体实现类。
 6. `State`枚举： 定义生命周期的状态
 7. `Event`枚举：定义生命周期的事件
 8. `OnLifecycleEvent`: 用于申明响应周期方法的注解
 
如果下图，该图是Lifecycle组件的类结构图。
![Lifecycle组件类图](http://oq6yfhskd.bkt.clouddn.com/blog/lifecycle/class_lifecycle.png)

----

### Lifecycle的使用
因为Lifecycle的框架已经做了很多事情，所以Lifecycle使用比较简单，只需要实现一个LifecycleObserver接口，然后注册该实现的对象到Lifecycle即可。

#### 实现`LifecycleObserver`
实现`LifecycleObserver`有两种方式：
1. 直接实现`LifecycleObserver`界面，然后用`OnLifecycleEvent`来申明响应生命周期事件的方法，代码如下：
```
public class SelfLifeCycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onCreateEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onCreateEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onStartEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onStartEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onResumeEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onResumeEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPauseEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onPauseEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onPauseEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStopEx(LifecycleOwner owner){
        Logger.d("SelfLifeCycleObserver onStopEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onStopEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onDestroyEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onDestroyEx " + state.toString());
        }
    }
}
```
2. 直接实现`DefaultLifecycleObserver`接口，这种方法目前是Google推荐的方法，但是`DefaultLifecycleObserver`使用Java 8的语言特性，所以使用接口的话，编译的JDK必须大于等于JDK 8.0。 实现代码如下：
```
public class SelfDefaultLifecycleObserver implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onCreate");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onCreate " + state.toString());
        }
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onStart");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onStart " + state.toString());
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onResume");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onResume " + state.toString());
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onPause");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onPause " + state.toString());
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onStop");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onStop " + state.toString());
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onDestroy");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onDestroy " + state.toString());
        }
    }
}
```

#### 在`Lifecycle`中注册Observer
```
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new SelfLifeCycleObserver());
}
```
---
### Lifecycle的事件（Event）和状态（State）
在Lifecycle中定义了事件枚举，事件对应着UI组件的生命周期方法。其中包括：
1. `ON_CREATE` 
2. `ON_START`
3. `ON_RESUME`
4. `ON_PAUSE`
5. `ON_STOP`
6. `ON_DESTROY`
7. `ON_ANY`
从这些事件的名称上就可以看出其对应的生命周期方法。在`SelfLifeCycleObserver`类中就使用`OnLifecycleEvent` 和 `Lifecycle.Event`定义了能够响应这些事件的方法。
`ON_ANY`事件是一个比较特殊的事件。如果用它定义了事件响应方法，那么该方法在剩余的6个事件中都会被触发调用。

Lifecycle中定义的状态枚举:
1.`DESTROYED`: 如果处于该状态，Lifecycle不会再发出更多的事件。`ON_DESTROY`事件触发后立即会进入该状态；
2.`INITIALIZED`:  没有执行onCreate方法之前会处于该状态。在实际的开发过程中，无法获取的该状态
3.`CREATED`： 处理`ON_CREATE`或者`ON_STOP`事件时会进入该状态；
4.`STARTED`： 处理`ON_START`或者`ON_PAUSE`事件时会进入该状态；
5.`RESUMED`： 处理`ON_RESUME`事件时会进入该状态

如下图，该图描述了Lifecycle中事件与状态之间的关系：
![事件与状态之间的关系](https://developer.android.google.cn/images/topic/libraries/architecture/lifecycle-states.png)

>**Note:**
1. Lifecycle的状态中没有PAUSED、STOPED等状态。在`ON_STOP`触发，即`onStop`方法被调用，后Lifecycle的状态是回到CREATED; 'onPause'是回到`STARTED`状态。
2. 在`onSaveInstanceState`方法在 `onStop`之前就被调用了，而且在`onSaveInstanceState`被调用后，Activity中就不能在进行UI上的操作，所以实际上在`onSaveInstanceState`调用后，Lifecycle已经进入了`CREATED`状态。但是这对开发者没有实际上影响。
