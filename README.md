# Android 应用基础架构组件

tags： Lifecycles ViewModel LiveData

---
## 前言
无论是什么应用的开发过程，都无法摆脱‘界面’、‘数据源’、‘数据的业务逻辑’三个部分以及这三个部分之间的爱恨纠葛。Android的应用开发亦是如此。google为了方便Android的应用开发者，已经提供非常优秀的Framework架构，开发者只需要遵循其架构规范就能够比较容易的开发出能够正常运行的Android应用。

不过，在实际的开发情况中，出现了一个非常普遍的问题：庞大的类，特别是庞大的UI类（Activity或者Fragment）。庞大的UI类给代码的质量带来很大的隐患：可读性差、可维护性差、高耦合导致的健壮性差。为了更好指导应用开发者开发出高质量的应用程序，简化开发流程，Google在Android平台上发布了**Android架构组件（Android Architecture Components）**
      
---
## 架构组件简介
Android架构组件中主要包含如下组件：

 1. Lifecycles： 生命周期管理，该组件是其它组件的基础，可由于跟踪UI的（Activity和Fragment）的生命周期
 2. LiveData： 一种可以被观察的以及可以感知生命周期的数据容器。
 3. ViewModel：它是UI，例如Activity、Fragment,与数据之间的桥梁；可以在其内部处理数据业务逻辑,例如从网络层或者数据持久层获取数据、更新数据等。
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
 
如下图，该图是Lifecycle组件的类结构图。
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
2. 直接实现`DefaultLifecycleObserver`接口，这种方法目前是Google推荐的方法，但是`DefaultLifecycleObserver`使用Java 8的语言特性，所以使用接口的话，编译的JDK必须大于等于JDK 1.8。 实现代码如下：
```
public class SelfDefaultLifecycleObserver implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
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
1. Lifecycle的状态中没有PAUSED、STOPED等状态。在`ON_STOP`触发，即`onStop`方法被调用，后Lifecycle的状态是回到CREATED; `onPause`是回到`STARTED`状态。
2. 在`onSaveInstanceState`方法在 `onStop`之前就被调用了，而且在`onSaveInstanceState`被调用后，Activity中就不能在进行UI上的操作，所以实际上在`onSaveInstanceState`调用后，Lifecycle已经进入了`CREATED`状态。但是这对开发者没有实际上影响。
3. 当`LifecycleOwer`处于`STARTED`或者`RESUMED`状态的时候，认为该`LifecycleOwner`处于**激活（Active）**的状态,如果处于其它状态，则认为是**非激活（Inactive）**状态
        
---
## LiveData
`LiveData`是一个可以**被观察的**数据容器类。而且LiveData能够感知`Activity`、`Fragment`、`Service`生命周期。
>因为`LiveData`能够感知Android组件的生命周期，所以`LiveData`能够做到只在Android组件处于激活状态时才给观察者发送通知。

使用了`LiveData`有以下的一些优点：

1. 避免内存泄漏，观察者对象都会和`Lifecycle`对象有关联，当Lifecycle处于`DESTROYED`状态时，观察者对象会被自动清除掉。
2. Activity处于后台时，不会出现UI变更产生的报错。因为在Activity处于后台时，`Lifecycle`为非激活状态,此时`LiveData`不会向观察者发送通知。
3.不需要额外的生命周期处理。由于LiveData能够感知组件的生命周期，LiveData对于生命周期的变化会在其内部自动处理。不需要额外的代码进行干预。UI组件只需要对其数据进行监听即可。
4. 总是能得到最新的数据。 在UI组件处于非激活状态时，`LiveData`不会向该组件的提供的观察发送通知；但如果该UI组件的状态变为激活状态，`LiveData`会立即向该UI组件的提供的观察者发送通知，以便UI组件即使更新数据。
5. 更友好的Android Configuration变化时的数据处理方式。
6. 能够利用`LiveData`共享资源。

### LiveData组成简介

1. `LiveData` 是 LiveData机制的核心类。其实现了LiveData机制的大部分功能：能够被观察、数据变化时能够通知观察者、感知`Lifecycle`的生命周期等等。
2. `MutableLiveData`，该类是`LiveData`的子类。它的扩展就是暴露了`setValue`和`postValue`方法。
3. `MediatorLiveData`， 该类是`MutableLiveData`的子类。该类的对象可以作为观察者，监听多个其它的`LiveData`对象，当其监听的其中一个`LiveData`对象发生数据变化时，能够它的观察者发送通知。
4. `Observer`，观察者的接口。可以通过`LiveData`的observe方法，让其对象实现对`LiveData`对象的监听。开发者需要实现该接口的`onChange`方法。

下图为LivData框架的类图
![LiveData类图](http://oq6yfhskd.bkt.clouddn.com/blog/livedata/class_livedata1.png)

### LiveData的简单使用
由于`LiveData`类的setValue和postValue方法是`protected`修饰，外部类不能使用。在一般情况下开发者要使用`MutableLiveData`来做为数据容器。如下面代码片段就是一个使用`MutableLiveData`的场景。当mUser的setValue方法被调用时，在mUser上注册的Observer对象的onChange方法就会被触发。

>Note:
 `LiveData`的setValue方法**必须在主线程中被调用**。如果想在非主线程中修改`LiveData`所包含的数据，需要使用`postValue`方法。

```
public class UserViewModel extends ViewModel {

    MutableLiveData<User> mUser = new MutableLiveData<>();

    public UserViewModel() {
        mUser.setValue(null);
        loadUser();
    }

    public void observe(LifecycleOwner owner, Observer<User> observer) {
        mUser.observe(owner,observer);
    }

    void loadUser() {
        Observable.create((ObservableEmitter<User> e) -> {
            User user = MockUsers.getMockUsers().getUser(1);
            e.onNext(user);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread()).subscribe((user) -> mUser.setValue(user));
    }
}
```
### 添加观察者
`LiveData`的观察者可以通过其observe的方法进行注册。当Observer对象注册成功后，如果当Lifecycle处于激活状态，只要`LiveData`的setValue被调用，其会立即收到`LiveData`的通知。
```
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ...
    UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    viewModel.observe(this,user -> {
        if((user != null && getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            Toast.makeText(this,"the user is " + user.toString(), Toast.LENGTH_SHORT).show();
        }
    });
    ...
}
```

>Note:
- `LiveData`中有observeForever方法，通过该方法注册的观察者，只要在`LiveData`有数据变化时就会被通知，无论Lifecycle是否处于激活状态。
- `Observer`的onChange方法运行在主线程中。

### MediatorLiveData
`MediatorLiveData` 是 `MutableLiveData`的子类，从类的命名上就可以看出`MediatorLiveData`是一个中间类，类似于代理的角色：它是一个能够监听其它`LiveData`对象的`LiveData`。也就是说`MediatorLiveData`不但能够做为被观察者，向观察者发送通知，也能够做为观察者接收其它`LiveData`对象的通知。Google将被`MediatorLiveData`监听的`LiveData`称为“源”（Source LiveData）。可以通过`addSource`方法添加`MediatorLiveData`的源`LiveData`对象。

```public <S> void addSource(@NonNull LiveData<S> source, @NonNull Observer<S> onChanged)```

一个`MediatorLiveData`对象能够拥有多个“源”，当其中任意一个“源”发生变化时，与之对应的观察者就会被通知，正在观察者对象中如果修改`MediatorLiveData`对象的value，`MediatorLiveData`对象就会向其自身的观察者发送通知。
```
public class MainFragmentViewModel extends AndroidViewModel {
    private MediatorLiveData<List<UserEntry>> mUserEntriesLiveData;
    public MainFragmentViewModel(Application application) {
        super(application);
        mUserEntriesLiveData = new MediatorLiveData<>();
        LiveData<List<UserEntry>> liveData = UserDatabase.db(getApplication()).userDao().getAllUserLiveData();
        mUserEntriesLiveData.addSource(liveData, mUserEntriesLiveData::setValue);
    }

    public void addObserver(LifecycleOwner owner, Observer<List<UserEntry>> observer) {
        if (mUserEntriesLiveData != null) {
            mUserEntriesLiveData.observe(owner, observer);
        }
    }
}
```

### Transformations
在`LiveData`框架中，Google还给开发者提供了`Transformations`工具类。该类拥有两个静态方法：map和switchMap:

```
LiveData<Y> map (LiveData<X> source, Function<X, Y> func)
LiveData<Y> switchMap (LiveData<X> trigger, Function<X, LiveData<Y>> func)
```
其中Function是一个接口，定义如下：
```
public interface Function<I, O> {
    /**
     * Applies this function to the given input.
     *
     * @param input the input
     * @return the function result.
     */
    O apply(I input);
}
```
map和switchMap这两个方法作用是从一个`LiveData`对象(称为Source)得到另外一个`LiveData`对象（称为Target）。当Source对象发生变化时，会触发执行Function的方法从而修改Target的值，Target也就会向它的观察者发送通知（是不是觉得这个是`MediatorLiveData`的特点？是的，map和switchMap就是通过MediatorLiveData来实现的）。另外，`Function`的`apply`方法是运行主线程上。

下面通过具体的例子说明两者的使用情况。

考虑我们需要通过用户Id得到一个用户数据的场景:
首先需要一个保存Id的`LiveData`对象`mUserIdentification`，因为外部需要修改Id的值，以便获取不同的User数据，所以其被申明为`MutableLiveData`类型;当外部通过mUserIdentification.setValue修改Id的值时，`Function`的`apply`方法会被调用以便获取新的User值。

下面是利用map方法的转化代码，其`Function`的`apply`方法直接返回的是一个User对象。如果`MockUsers.getMockUsers().getUser(input.toInt())`的不是耗时的操作，那么用map方法完全能够胜任转化的工作。但是如果该获取User数据比较耗时，那么由于apply方法是要运行在主线程，必然会影响应用的整体体验，甚至会报出ANR错误。

```
    //使用map转化，从UserIdentification到User
    //保存Id的LiveData对象，当外部提供mUserIdentification.setValue修改Id的值
    MutableLiveData<UserIdentification> mUserIdentification = new MutableLiveData<>();
    LiveData<User> mUser = Transformations.map(mUserIdentification,
            new Function<UserIdentification, User>() {
                @Override
                public User apply(UserIdentification input) {
                    User user = MockUsers.getMockUsers().getUser(input.toInt());
                    return user;
                }
            });
```
再看使用switchMap转化代码，其Function的方法返回的是一个`LiveData<User>`对象(称为liveData)。实际上liveData是switchMap返回对象的一个“源”（上面map和switchMap都返回的其实是`MediatorLiveData`对象)，所以我们可以使用异步的方式获取数据，然后再修改liveData的值，已到达通知最终的观察者的目的。

```
    //使用switchMap转化，从UserIdentification到User
    MutableLiveData<UserIdentification> mUserIdentification = new MutableLiveData<>();
    final LiveData<User> mUser = Transformations.switchMap(mUserIdentification,
            new Function<UserIdentification, LiveData<User>>() {
                @Override
                public LiveData<User> apply(UserIdentification input) {
                    final int id = input.toInt();
                    final MutableLiveData<User> liveData = new MutableLiveData<>();
                    //异步获取User数据
                    Observable.create((ObservableEmitter<User> e) -> {
                        User user = MockUsers.getMockUsers().getUser(id);
                        e.onNext(user);
                        e.onComplete();
                    }).subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread()).subscribe(user ->
                            //设置获取到User到liveData
                            liveData.setValue(user)
                    );
                    return liveData;
                }
            });

```
总的来说，如果是简单的LiveData数据的转化，可以使用map方法；如果是比较复杂，耗时的操作请考虑使用switchMap的方法。

---

## ViewModel

按照Google的定义,`ViewModel`被用来保存和管理与UI相关的数据，所以说它是UI和数据之间的桥梁。
1. `ViewModel`可以解放UI组件中与数据相关的业务逻辑，让UI组件更专注于UI的显示、与用户的交互、与系统Framework的通信。也就是说把以前写在Activity和Fragment中的数据逻辑代码重构到ViewModel中吧。
2. 当设备的相关配置发生改变，导致Activity,Fragment等UI组件被销毁重建时，保存在`ViewModel`对象中的数据可以一直存在，而不需要重新获取这些数据。

### ViewModel的使用

Google给开发者提供了两种类型的`ViewModel`:
1. `ViewModel`类，是所有`ViewModel`的基类。如果一个类A从该类派生而来，那么A一定要提供一个public的默认构造方法（`AndroidViewModel`除外，ViewModel的框架对`AndroidViewModel`提供另外初始化支持）。
2. `AndroidViewModel`类，该类是`ViewModel`的一个子类。如果一个类A从`AndroidViewModel`派生而来，那么A一定要提供一个public的形参为Application类型的构造方法。

从上面的描述看，如果在ViewModel中实现的业务需要Android的Context对象，可以直接从`AndroidViewModel`派生一个类。

```
public class MainFragmentViewModel extends AndroidViewModel {
    private MediatorLiveData<List<UserEntry>> mUserEntriesLiveData;
    public MainFragmentViewModel(Application application) {
        super(application);
        mUserEntriesLiveData = new MediatorLiveData<>();
        LiveData<List<UserEntry>> liveData = UserDatabase.db(getApplication()).userDao().getAllUserLiveData();
        mUserEntriesLiveData.addSource(liveData, mUserEntriesLiveData::setValue);
    }
}
```
```
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
    mViewModel.addObserver(this, userEntries -> {
        if (mMainRecyclerViewAdapter == null) {
            mMainRecyclerViewAdapter = new MainRecyclerViewAdapter(userEntries);
            mRecyclerView.setAdapter(mMainRecyclerViewAdapter);
        } else {
            mMainRecyclerViewAdapter.setUserData(userEntries);
        }
    });
}
```

> **Note**:
在`ViewModel`对象中不要引用UI相关的对象，例如Activity，View，Fragment等。因为`ViewModel`对象生命周期的影响，可能导致这些对象的内存泄露问题。

### ViewModel生命周期

如下图，说明的是`ViewModel`对象生命周期与Activity生命周期的对应关系。在图中一个需要注意的地方是由于屏幕旋转（Activity rotated）Activity被销毁了，但是`ViewModel`对象并没有被清除，当Activity被重新创建时，`ViewModel`对象仍然存在；直到Activity执行了Finish方法被销毁之后，`ViewModel`对象才会被清除。

![`ViewModel`生命周期](https://developer.android.google.cn/images/topic/libraries/architecture/viewmodel-lifecycle.png)

那么ViewModel这种机制是如何实现的呢？

先看看ViewModel框架的类图:

![ViewModel框架的类图](http://oq6yfhskd.bkt.clouddn.com/blog/viewmodel/class_viewmodel_1.png)

- `ViewModelProviders`已经在上面的代码中使用过，它是一个工具类，用于获取UI组件对应的`ViewModelProvider`对象。
- `ViewModelProvider`这个类仍然是一个工具类，它的对象用生成`ViewModel`对象，并且该生成的对象保存到`ViewModelStore`对象中。
- `ViewModelStoreOwner`这是一个接口(类图中未标出)。实现了该接口的类需要有能力获取到UI组件对象的`ViewModelStore`对象。`android.support.v4.app.FragmentActivity`和`android.support.v4.app.Fragment`实现了该接口。
- `ViewModelStore` 是该框架的核心类，用于保存`ViewModel`对象。 另外该类对其持有者做了要求:如果一个UI组件对象持有一个`ViewModelStore`对象，当UI组件因为设备的配置变化而被销毁和重新创建时，新创建的UI组件需要引用到其被销毁之前相同的`ViewModelStore`的对象。
- `ViewModelStores`是产生`ViewModelStore`的工厂类。

再来看看ViewModel框架是如何实现在配置变化中一直存在的：
1. `android.support.v4.app`包中的`FragmentActivity`和`Fragment`实现了`ViewModelStoreOwner`的情况：
在`android.support.v4.app.FragmentActivity`这个类中覆盖了Activity的` onRetainNonConfigurationInstance()`方法：

```
public final Object onRetainNonConfigurationInstance() {
    Object custom = this.onRetainCustomNonConfigurationInstance();
    FragmentManagerNonConfig fragments = this.mFragments.retainNestedNonConfig();
    if (fragments == null && this.mViewModelStore == null && custom == null) {
        return null;
    } else {
        FragmentActivity.NonConfigurationInstances nci = 
                                new FragmentActivity.NonConfigurationInstances();
        nci.custom = custom;
        nci.viewModelStore = this.mViewModelStore;
        nci.fragments = fragments;
        return nci;
    }
}
```
> `android.support.v7.app.AppCompatActivity`是`FragmentActivity`的子类.

该方法是在Activity因为设备配置变化导致Activity被销毁前调用（在onStop之后，onDestroy之前），其返回的`Object`对象,会一直存在，而且可以通过重新创建的Activity的`getLastNonConfigurationInstance()`方法获取。

2. `android.support.v4.app`包中的`FragmentActivity`和`Fragment`没有实现`ViewModelStoreOwner`的情况：
看下`ViewModelStores`的代码，如果of方法中传入的UI组件不是`ViewModelStoreOwner`对象，那么就会调用`holderFragmentFor`获取一个`HolderFragment`对象。

```
public class ViewModelStores {
...
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull FragmentActivity activity) {
        if (activity instanceof ViewModelStoreOwner) {
            return ((ViewModelStoreOwner) activity).getViewModelStore();
        }
        return holderFragmentFor(activity).getViewModelStore();
    }
    @NonNull
    @MainThread
    public static ViewModelStore of(@NonNull Fragment fragment) {
        if (fragment instanceof ViewModelStoreOwner) {
            return ((ViewModelStoreOwner) fragment).getViewModelStore();
        }
        return holderFragmentFor(fragment).getViewModelStore();
    }
...
}
```
`HolderFragment`是一个Fragment，它实现了`ViewModelStoreOwner`接口，因此其拥有获取`ViewModelStore`对象的能力。`HolderFragment`的`holderFragmentFor`方法是一个静态(static)方法，该方法调用了`HolderFragment$HolderFragmentManager`对象的`holderFragmentFor`方法。可以看看`HolderFragment$HolderFragmentManager`对象中`holderFragmentFor`的实现：

```
HolderFragment holderFragmentFor(FragmentActivity activity) {
    FragmentManager fm = activity.getSupportFragmentManager();
    HolderFragment holder = findHolderFragment(fm);
    if (holder != null) {
        return holder;
    }
    holder = mNotCommittedActivityHolders.get(activity);
    if (holder != null) {
        return holder;
    }

    if (!mActivityCallbacksIsAdded) {
        mActivityCallbacksIsAdded = true;
        activity.getApplication().registerActivityLifecycleCallbacks(mActivityCallbacks);
    }
    holder = createHolderFragment(fm);
    mNotCommittedActivityHolders.put(activity, holder);
    return holder;
}
```

看到这里，应该大体明白了，在`FragmentActivity`和`Fragment`没有实现`ViewModelStoreOwner`的情况下，是通过加入`HolderFragment`来管理`ViewModelStore`对象的。再看看`HolderFragment`的构造方法：
```
public HolderFragment() {
    setRetainInstance(true);
}
```

当`setRetainInstance`设置`true`时，会影响Fragment的生命周期：在Activity因为设备配置变化而被重建时，该Fragment的`onDestroy`方法不会被调用；因为没有销毁，重新加入Activity或者父Fragment时，其`onCreate`也不会再调用。Fragment对象没有销毁，其拥有的`ViewModelStore`对象也就一直存在。

---

## 总结：
虽然写了比较长，其实`LiveData`, `ViewModel`在实际的使用中还是比较容易。Google设计这几个框架的目的就是希望开发者能够更方便地处理UI和数据之间的耦合。


## 参考文献
[1. Handling Lifecycles with Lifecycle-Aware Components](https://developer.android.google.cn/topic/libraries/architecture/lifecycle)
[2. LiveData Overview](https://developer.android.google.cn/topic/libraries/architecture/livedata)
[3. ViewModel Overview](https://developer.android.google.cn/topic/libraries/architecture/viewmodel)
[4. Android 架构组件（一）——Lifecycle](https://blog.csdn.net/zhuzp_blog/article/details/78871374)
[5. Android 架构组件（二）——LiveData](https://blog.csdn.net/zhuzp_blog/article/details/78871527)
[6. Android 架构组件（三）——ViewModel](https://blog.csdn.net/zhuzp_blog/article/details/78910535)



