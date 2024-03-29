# IM-Middleware
![image](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/4cbf6d5bac74474f9296e381bd840fed~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp)
![image](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/87800ca4d2bb4c098fd7faf9ceb6e61a~tplv-k3u1fbpfcp-zoom-in-crop-mark:1512:0:0:0.awebp?)
本项目是一个基于多进程的Android IM即时通信中间件，旨在帮助Android开发者了解如何在实现IM即时通信的同时，提高应用程序的稳定性和安全性。

通过本项目，您将了解到多进程在Android IM即时通信中的重要性，并学习到如何设计和实现一个基于WebSocket的中间件。我们将深入讲解多进程架构和流程设计，并展示详细的时序图和类图，让您更好地理解该中间件的实现过程。

此外，我们还将介绍如何通过多进程通信来实现消息传递和状态同步，提高应用程序的性能和稳定性。最后，我们将展示如何将该中间件集成到您的Android应用程序中，让您的用户可以更加快速、可靠地进行即时通信。

如果您是一个Android开发者，并且正在寻找一种稳定、安全、可扩展的即时通信解决方案，那么本项目将是您的不二选择。

Hit + 协程启动加载器 https://juejin.cn/editor/drafts/7219224572138930233

移动端即时通信的特点：
1. 即时传输：即时通信是指消息的传输速度非常快，消息可以在几乎同时发送和接收。
2. 实时反馈：移动端即时通信通常能够提供实时反馈，让用户可以迅速得到对方的回应，从而使得交流更加流畅。
3. 多媒体支持：移动端即时通信可以支持多种类型的媒体文件，如文字、图片、语音、视频等，使得用户能够更加方便地进行交流和分享。
4. 多设备同步：移动端即时通信通常能够支持多设备同步，即同一个用户可以在不同的设备上同时登录并进行交流，而且消息会自动同步到所有的设备上。
5. 安全性：移动端即时通信在数据传输过程中通常会采用加密技术，确保用户的通信内容不会被窃取或篡改。
6. 个性化设置：移动端即时通信通常能够提供个性化设置，例如自定义聊天背景、表情包、字体颜色等，让用户可以更加自由地表达自己。
7. 群组聊天：移动端即时通信通常能够支持群组聊天，用户可以创建一个群组并邀请其他用户加入，从而进行多人交流。
8. 在线状态：移动端即时通信通常能够显示用户的在线状态，让用户可以了解对方是否在线，并且可以方便地与对方建立联系。
   这些所有的特性都是在“可以实现快速、方便、实时的沟通和交流”的基础上实现的，然而在Android客户端，能否实时取决于进程是否存活，如果进程被回收了，那这些特性都将失效。

做Android的都知道，进程的回收APP是自己管控不了的，
Android 进程
在 Android 系统中，进程是指正在运行的应用程序的实例。每个进程都有自己独立的虚拟机和系统资源，它们之间相互隔离，互不干扰。
进程通常由一个或多个组件构成，如 Activity、Service、BroadcastReceiver 和 ContentProvider 等。每个组件都运行在它所在的进程中，但不同的组件可以运行在不同的进程中。
生命周期受到系统内存管理机制的影响。当系统内存不足时，系统会根据一定的策略来回收一些进程，以释放内存资源。Android 进程管理机制是基于 Linux 内核的，系统会根据进程的重要性和内存占用情况来进行进程回收和优先级调度。
Android 进程并不是一个静态的概念，它的状态是动态变化的。应用程序启动时会创建进程，当应用程序不再需要该进程时，系统会将其回收。
进程基本分为四种类型
1. 前台进程（Foreground Process）：这类进程是指正在与用户交互的应用程序进程，如正在显示在屏幕上的 Activity，或正在播放音乐的应用程序进程等。前台进程优先级最高，系统会尽可能保持这类进程的运行状态，以确保用户能够流畅地使用应用程序。
2. 可见进程（Visible Process）：这类进程是指虽然没有直接与用户进行交互，但是对用户当前操作有影响的应用程序进程，如正在后台播放音乐的应用程序进程等。可见进程的优先级次于前台进程，但仍然比较高，系统会尽可能保持这类进程的运行状态。
3. 后台进程（Background Process）：这类进程是指已经被用户关闭，但是仍然在后台运行的应用程序进程。后台进程的优先级较低，当系统内存占用过高时，系统会优先回收这类进程来释放内存。
4. 空进程（Empty Process）：这类进程是指系统为了加速应用程序的启动而创建的进程，但是没有实际运行任何应用程序代码的进程。这类进程的主要作用是为应用程序的启动提供一个空壳，以缩短启动时间。
   以上四类进程的优先级和占用资源等因素都会影响 Android 系统的性能和稳定性，因此，在开发应用程序时，需要尽可能地减少内存的占用和资源的浪费，以提高系统的响应速度和用户体验。

进程回收
为了保证系统的稳定性和资源管理的效率，系统会对进程进行回收。进程回收的流程如下：
1. 当系统出现内存不足的情况时，系统会根据进程的优先级和占用的资源量等因素，选择一些进程进行回收。
2. 系统首先会杀掉进程中的所有后台进程和服务进程，释放它们占用的资源，但会保留前台进程和用户正在交互的应用程序进程。
3. 如果系统还是内存不足，那么就会继续回收前台进程和用户正在交互的应用程序进程，直到系统获得足够的内存为止。
   单进程和多进程的区别
4. 应用单进程回收：如果应用程序只有一个进程，那么当系统回收进程时，整个应用程序都会被回收，包括应用程序中的所有组件和数据等信息。这会导致应用程序需要重新启动，并重新加载数据等信息，用户体验不佳。
5. 应用多进程回收：如果应用程序采用多进程的方式来运行，那么当系统回收进程时，只有被回收的进程会被停止，其他进程仍然可以继续运行，从而保证应用程序的部分功能可以正常使用。这种情况下，用户体验相对较好，但同时也会增加系统的负担和内存占用量。

在开发中多进程拉活虽然不能保活了，但是也是伪保活的一种重要手段。


即时通信对于Android进程，改如何做
其实简单的过一下Android进程相关的东西，是为了说明APP对自身的生命周期基本没有控制力，要想保证即时通信成立，所在进程活着，显然是最主要的。

什么是服务进程
服务进程并不是进程的分类，而是进程的一种特殊用途。在 Android 中，服务进程是指运行服务组件的进程。服务进程通常会在应用程序启动时被创建，并在整个应用程序的生命周期内一直存在，直到应用程序被关闭或者系统内存不足时被回收。
服务进程通常用于执行一些需要在后台长期运行的任务，如播放音乐、下载文件、推送消息等。将这些任务放在服务进程中可以保证它们不会因为应用程序被关闭而中断，从而保证应用程序的持久连接和稳定性。

即时通信应该切进程
1. 为了确保即时通信的实时性，通常将即时通信放在服务进程中。服务进程拥有较高的优先级和稳定的生命周期，系统会优先保留服务进程，而且服务进程的优先级比普通应用程序进程的优先级高，能够更长时间地保留在内存中。因此，将即时通信放在服务进程中，可以保证即时通信的稳定性和实时性。
2. 服务进程与应用程序进程是分离的，即使应用程序进程被回收，服务进程仍然可以继续运行，从而保证即时通信的持续性。另外，服务进程还可以通过绑定方式，为应用程序进程提供即时通信的功能，从而提高了通信的效率和稳定性。
   切进程的优势
   看完上面的一长串介绍后应该理解，切进程的优势是非常巨大的，总结一下：
3. 提高响应速度：将即时通信相关的任务放在单独的进程中处理，可以避免应用程序主进程的阻塞，从而提高应用程序的响应速度和用户体验。
4. 提高稳定性：将即时通信相关的任务放在单独的进程中处理，可以减少应用程序崩溃的风险，从而提高应用程序的稳定性。
5. 方便管理：将即时通信相关的任务放在单独的进程中处理，可以方便地对其进行管理和调试，从而提高开发效率。 

客户端组件物理结构设计
   将APP作为客户端，依赖IM中间件提供的接口实现即时通信。中间件作为服务进程独立运行，负责长连接、Push、Https等数据源处理，将处理后的数据通过AIDL回调到APP中进行显示和处理。
   可以有效避免进程回收对通信质量的影响，提高即时通信的稳定性和可靠性。同时，中间件作为服务进程独立于应用进程运行，可以保证即使应用进程被回收，也不会影响即时通信服务的运行。这种设计方案可以让数据处理逻辑与应用程序逻辑分离，提高代码的可维护性和扩展性。
   如下图（箭头方向为依赖方向）

具体文章请看： https://juejin.cn/post/7208023562807459896


## 剥离长连接，让组件职责更单一

在Android [IM即时通信多进程中间件设计与实现](https://juejin.cn/post/7208023562807459896) 一文中主要的点有两个

1. 多进程完成客户端和服务端的通信
2. 通信的介质

## 让SDK更通用

整个实现中其实我是不Core你到底使用websocket还是自己撸的TCP还是其他，我关心的是多进程中间件的实现。

所以我们要做一个设计，我希望在我的整个Demo 中不关心长链接的具体实现，但是我能在Demo 对整个长连接进行管理、使用，我的目标是：

1. 在使用Demo时不需要考虑长连接的实现方式
2. 在任何已经存在的长连接代码中，此Demo 均能实现其多进程中间件的职责

这样设计的优势在哪里？

1. 可以更多的作为一个开放平台
2. 不管以后发展中出现多牛逼的长连接方式，都不影响我们的业务

这个设计会具备以下特点

1. 涉及核⼼功能统⼀管理升级；
2. 保证核⼼功能具备⾼灵活替换性；
3. Demo与长连接框架完全剥离，项⽬⾯向中间层编码，⽆须了解实际实现SDK

## 物理结构

中间件形成独⽴SDK，项⽬⾯向图⽚加载框架编码，所有功能统⼀管理；依赖关系⻅下图：

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0be76bda9a3c408f832b10dab1950cc5~tplv-k3u1fbpfcp-watermark.image?)

## 多进程中间件

当前的组件设计核⼼思想是彻底弱化**长连接**在项⽬中的定义，达到项⽬直接与IMClient接触，终极⽬标是项⽬只知道IMClient⽽不知道长连接，所以整体SDK呈现出了如下图的组件架构模式：
可以包含了多个不同的组件

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d4c42b425a894c66b765cca2be7eb8d6~tplv-k3u1fbpfcp-watermark.image?)

## 具体实现

### 使用依赖反转原则进行高层逻辑实现


1. LongConnectService

```kotlin
/**
 * Create by kpa(billkp@yeah.net) on 2023/3/13
 * 16:58
 * Describe ：整个实现中其实我是不Core你到底使用websocket还是自己撸的TCP还是其他，
 * 我关心的是多进程中间件的实现。所以我们对这部分做一个
 * 抽象连接器
 */
interface LongConnectionService {
    /**
     * 初始化长连接实例
     */
    fun initLongConnection()

    /**
     * 连接长连接
     */
    fun connect()

    /**
     * 断开长连接
     */
    fun disConnect()

    /**
     * 重新连接长连接
     */
    fun reConnect()

}
```

然后我们使用抽象工厂模式提供对长连接器的统一创建


![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/ac1515339b8b42bfb482bdb2224c0653~tplv-k3u1fbpfcp-watermark.image?)

### 修改中间件IMClient

对于SDK⽽⾔，长连接器操作、配置等，需要提供统⼀的配置类以供APP进⾏配，可使用Builder模式，封装其职责，总的来说他将变为APP对其配置和操作的工具

```
class IMClient private constructor(builder: Builder) {

  
    private var longConnectionFactory: IMLongConnectionFactory<LongConnectionService>? = null

    init {
        longConnectionFactory = builder.getFactory()
    }

    //...
    companion object {
        @Volatile
        private lateinit var mInstance: IMClient


        fun isInstalled() = this::mInstance.isInitialized

        @JvmStatic
        fun init(imClient: IMClient): IMClient {
            synchronized(IMClient::class) {
                if (!isInstalled()) {
                    mInstance = imClient
                } else {
                    throw RuntimeException("已经初始化")
                }
            }
            return mInstance;
        }

        @JvmStatic
        fun with(): IMClient {
            if (isInstalled()) {
                throw RuntimeException("未初始化")
            }
            return mInstance;
        }


    }

    class Builder {
        private var mFactory: IMLongConnectionFactory<LongConnectionService>? = null


        fun withFactory(factory: IMLongConnectionFactory<LongConnectionService>) = apply {
            this.mFactory = factory
        }

//如果不设置长连接器，将默认本Demo 中的DefaultWebsocketFactory
        fun getFactory(): IMLongConnectionFactory<LongConnectionService> {
            if (mFactory == null) {
                mFactory = DefaultWebsocketFactory.create()
            }
            return mFactory!!
        }

        fun build(): IMClient {
            return init(IMClient(this))
        }
    }

    //...

}
```

### 中间件使用
```

// Context of the app under test.
val appContext = InstrumentationRegistry.getInstrumentation().targetContext
// 初始化
IMClient.Builder()
    .withFactory(DefaultWebsocketFactory.create()).build()

// 使用
IMClient.with().connect()
IMClient.with().send("我是发送的消息内容")
```

### 怎么接入自己的长连接代码

通过上面的类图，相信大家已经知道怎么接入了。

1. 实现LongConnectionService 高级抽象
```
class DefaultLongConnectionImpl(private val mContext: Context) : LongConnectionService {
    override fun initLongConnection() {
        TODO("Not yet implemented")
    }

    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun disConnect() {
        TODO("Not yet implemented")
    }

    override fun reConnect() {
        TODO("Not yet implemented")
    }
}
```
2. 创建对应的工厂创建器
```
class DefaultWebsocketFactory : IMLongConnectionFactory<DefaultLongConnectionImpl>() {

    companion object {
        @JvmStatic
        fun create(): DefaultWebsocketFactory {
            return DefaultWebsocketFactory()
        }
    }

    override fun createLongConnection(context: Context): DefaultLongConnectionImpl {
        return DefaultLongConnectionImpl(context)
    }
}
```

### 详细的流程设计


![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0d0d6d412c3447b0910a5db5ff0e67d3~tplv-k3u1fbpfcp-watermark.image?)

### 拒绝侵入三方SDK，让你的业务独立起来
在⽅案设计中，完成了项⽬与IMClient 的连接，也设计了核⼼长连接的的切换、功能抽象等⼯作，但是还存在以下问题：

1. 长连接的代码对于使用自己代码的同学，他是对于的无用的
2. 组件的职责不明确

但是处理方式极其简单，我们只需要将抽象的高层逻辑部分移动到其他组件中，在组件层面，也就是代码仓库中隔离开，这将不再存在以上问题。

## 总结

这种设计方式在开发中应用场景众多，基本上所有使用第三方组件的场景都应该按照如此设计，这样既能保证业务不受侵害，在将来业务走上正轨也不影响你替换组件，这样我们就做到了：


1. 控制反转
2. 组件层级分明
3. 各组件间物理隔离
4. 根据需求单独打包 (⽐如项⽬研发接⼊了2款长连接框架，上线时只需要其中之一，即将需要的进⾏物
   理依赖，其他的取消依赖即可)
5. 去除IMClinet中⼼化，多⼈开发不受影响(开发中间件和长连接将变成两个业务)
6. 提升了组件稳定性

当然，组件的业务复杂程度不是我关心的，但是大家可以进行提取，丰富自己的业务。





