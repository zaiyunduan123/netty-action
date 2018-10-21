
## 总结
1. Netty 在这里的逻辑是：每次有新连接到来的时候，都会调用 ChannelInitializer 的 initChannel() 方法，然后这里 每个指令相关的 handler 都会被 new 一次。
2. 内部都是没有成员变量的 handler，也就是说是无状态的，完全可以使用单例模式，即调用 pipeline().addLast() 方法的时候，都直接使用单例，不需要每次都 new，提高效率，也避免了创建很多小的对象。
3. 如果一个 handler 要被多个 channel 进行共享，必须要加上 @ChannelHandler.Sharable 显示地告诉 Netty，这个 handler 是支持多个 channel 共享的，否则会报错。