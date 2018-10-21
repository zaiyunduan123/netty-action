
## 总结
1. Netty每次有新连接到来的时候，都会调用 ChannelInitializer 的 initChannel() 方法，然后这里 每个指令相关的 handler 都会被 new 一次。
2. 内部都是没有成员变量的 handler，也就是说是无状态的，完全可以使用单例模式，即调用 pipeline().addLast() 方法的时候，都直接使用单例，不需要每次都 new，提高效率，也避免了创建很多小的对象。
3. 如果一个 handler 要被多个 channel 进行共享，必须要加上 @ChannelHandler.Sharable 显示地告诉 Netty，这个 handler 是支持多个 channel 共享的，否则会报错。
4. 随着指令相关的 handler 越来越多，handler 链越来越长，在事件传播过程中性能损耗会被逐渐放大，因为解码器解出来的每个 Packet 对象都要在每个 handler 上经过一遍,合并平行 handler成一个handler，缩短事件传播路径。