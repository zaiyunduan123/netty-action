## netty绑定端口启动服务源码分析
本篇主要讲述的是netty是如何绑定端口，启动服务。启动服务的过程中，会了解到netty各大核心组件，各大组件是怎么串起来组成netty的核心

[一个简单的服务端启动代码](https://github.com/zaiyunduan123/netty-study/tree/master/src/com/jesper/netty/debug/SimpleServer.java)


重点关注下面这段代码
```java
b.bind(8888).sync();
```
跟进去
```java
public ChannelFuture bind(int inetPort) {
    return bind(new InetSocketAddress(inetPort));
} 
```
通过端口号创建一个 InetSocketAddress，然后继续跟进去
```java
public ChannelFuture bind(SocketAddress localAddress) {
    validate();
    if (localAddress == null) {
        throw new NullPointerException("localAddress");
    }
    return doBind(localAddress);
}
```
可用看出先通过validate()验证服务启动需要的必要参数，然后调用doBind()，我们再进入doBind()
```java
private ChannelFuture doBind(final SocketAddress localAddress) {
    //...
    final ChannelFuture regFuture = initAndRegister();
    //...
    final Channel channel = regFuture.channel();
    //...
    doBind0(regFuture, channel, localAddress, promise);
    //...
    return promise;
}
```
我们重点关注上面两个方法，initAndRegister()，以及doBind0()

##  initAndRegister()
```java
final ChannelFuture initAndRegister() {
    Channel channel = null;
    // ...
    channel = channelFactory.newChannel();
    //...
    init(channel);
    //...
    ChannelFuture regFuture = config().group().register(channel);
    //...
    return regFuture;
}
```
initAndRegister里面做了三件事情
1. new一个channel
2. init这个channel
3. 将这个channel 注册到某个对象

我们逐步分析这三件事情

### 1. new一个channel
这里的channel，由于是在服务启动的时候创建，我们可以和普通Socket编程中的ServerSocket对应上，表示服务端绑定的时候经过的一条流水线

我们发现这条channel是通过一个 channelFactory new出来的，跟进入最终是调用到 ReflectiveChannelFactory.newChannel() 方法
```java
public class ReflectiveChannelFactory<T extends Channel> implements ChannelFactory<T> {
    @Override
    public T newChannel() {
        try {
            return clazz.newInstance();
        } catch (Throwable t) {
            throw new ChannelException("Unable to create Channel from class " + clazz, t);
        }
    }
}

```
通过反射的方式来创建一个对象，而这个class就是我们在ServerBootstrap中传入的NioServerSocketChannel.class



>接下来我们就可以将重心放到 NioServerSocketChannel的默认构造函数

```java
private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();
public NioServerSocketChannel() {
    this(newSocket(DEFAULT_SELECTOR_PROVIDER));
}

private static ServerSocketChannel newSocket(SelectorProvider provider) {
    //...
    return provider.openServerSocketChannel();
}
```


通过SelectorProvider.openServerSocketChannel()创建一条server端channel，然后进入到以下方法

```java
public NioServerSocketChannel(ServerSocketChannel channel) {
    super(null, channel, SelectionKey.OP_ACCEPT);
    config = new NioServerSocketChannelConfig(this, javaChannel().socket());
}
```
一个服务端channel创建完毕了，将这些细节串起来的时候，我们顺带提取出netty的几大基本组件，先总结如下

1. Channel
2. ChannelConfig
3. ChannelId
4. Unsafe
5. Pipeline
6. ChannelHander


总结一下，用户调用方法 Bootstrap.bind(port) 第一步就是通过反射的方式new一个NioServerSocketChannel对象，并且在new的过程中创建了一系列的核心组件，


### 2. init这个channel

```java
@Override
void init(Channel channel) throws Exception {
  

    /**
    1.设置option和attr,先调用options0()以及attrs0()，然后将得到的options和attrs注入到channelConfig或者channel中
     */
    final Map<ChannelOption<?>, Object> options = options0();
        synchronized (options) {
            channel.config().setOptions(options);
     }
    final Map<AttributeKey<?>, Object> attrs = attrs0();
    synchronized (attrs) {
        for (Entry<AttributeKey<?>, Object> e: attrs.entrySet()) {
            @SuppressWarnings("unchecked")
            AttributeKey<Object> key = (AttributeKey<Object>) e.getKey();
            channel.attr(key).set(e.getValue());
        }
    }

    ChannelPipeline p = channel.pipeline();
     
    /**
    2.设置新接入channel的option和attr
    */
    final EventLoopGroup currentChildGroup = childGroup;
    final ChannelHandler currentChildHandler = childHandler;
    final Entry<ChannelOption<?>, Object>[] currentChildOptions;
    final Entry<AttributeKey<?>, Object>[] currentChildAttrs;
    synchronized (childOptions) {
        currentChildOptions = childOptions.entrySet().toArray(newOptionArray(childOptions.size()));
    }
    synchronized (childAttrs) {
        currentChildAttrs = childAttrs.entrySet().toArray(newAttrArray(childAttrs.size()));
    }

    /**
      3.加入新连接处理器
    */
    p.addLast(new ChannelInitializer<Channel>() {
        @Override
        public void initChannel(Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            ChannelHandler handler = config.handler();
            if (handler != null) {
                pipeline.addLast(handler);
            }

            ch.eventLoop().execute(new Runnable() {
                @Override
                public void run() {
                    pipeline.addLast(new ServerBootstrapAcceptor(
                            currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
                }
            });
        }
    });
}
```
### 3.将这个channel 注册某个对象
这一步，我们是分析如下方法
```java
ChannelFuture regFuture = config().group().register(channel);

```
调用到 NioEventLoop 中的register

```java
@Override
public ChannelFuture register(final ChannelPromise promise) {
    ObjectUtil.checkNotNull(promise, "promise");
    promise.channel().unsafe().register(this, promise);
    return promise;
}
``` 
跟进入
```java
@Override
public final void register(EventLoop eventLoop, final ChannelPromise promise) {
    // ...
    AbstractChannel.this.eventLoop = eventLoop;
    // ...
    register0(promise);
}
```
先将EventLoop事件循环器绑定到该NioServerSocketChannel上，然后调用 register0()
```java
private void register0(ChannelPromise promise) {
    try {
        boolean firstRegistration = neverRegistered;
        doRegister();
        neverRegistered = false;
        registered = true;

        pipeline.invokeHandlerAddedIfNeeded();

        safeSetSuccess(promise);
        pipeline.fireChannelRegistered();
        if (isActive()) {
            if (firstRegistration) {
                pipeline.fireChannelActive();
            } else if (config().isAutoRead()) {
                beginRead();
            }
        }
    } catch (Throwable t) {
        closeForcibly();
        closeFuture.setClosed();
        safeSetFailure(promise, t);
    }
}
```

## doBind0()
```java
private static void doBind0(
            final ChannelFuture regFuture, final Channel channel,
            final SocketAddress localAddress, final ChannelPromise promise) {
        channel.eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                if (regFuture.isSuccess()) {
                    channel.bind(localAddress, promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
                } else {
                    promise.setFailure(regFuture.cause());
                }
            }
        });
    }
```
我们发现，在调用doBind0(...)方法的时候，是通过包装一个Runnable进行异步化的，关于异步化task,我们进入到它的bind方法
```java
@Override
public final void bind(final SocketAddress localAddress, final ChannelPromise promise) {
    // ...
    boolean wasActive = isActive();
    // ...
    doBind(localAddress);

    if (!wasActive && isActive()) {
        invokeLater(new Runnable() {
            @Override
            public void run() {
                pipeline.fireChannelActive();
            }
        });
    }
    safeSetSuccess(promise);
}
```

doBind()方法
```java
protected void doBind(SocketAddress localAddress) throws Exception {
    if (PlatformDependent.javaVersion() >= 7) {
        //noinspection Since15
        javaChannel().bind(localAddress, config.getBacklog());
    } else {
        javaChannel().socket().bind(localAddress, config.getBacklog());
    }
}
```

最终调到了jdk里面的bind方法，这行代码过后，正常情况下，就真正进行了端口的绑定。

## 总结
netty启动一个服务所经过的流程

1. 设置启动类参数，最重要的就是设置channel
2. 创建server对应的channel，创建各大组件，包括ChannelConfig,ChannelId,ChannelPipeline,ChannelHandler,Unsafe等
3. 初始化server对应的channel，设置一些attr，option，以及设置子channel的attr，option，给server的channel添加新channel接入器，并出发addHandler,register等事件
4. 调用到jdk底层做端口绑定，并触发active事件，active触发的时候，真正做服务端口绑定
