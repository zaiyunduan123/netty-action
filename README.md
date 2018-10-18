
## channelHandler 的生命周期
1. handlerAdded() 与 handlerRemoved()
这两个方法通常可以用在一些资源的申请和释放

2. channelActive() 与 channelInActive()
对我们的应用程序来说，这两个方法表明的含义是 TCP 连接的建立与释放，通常我们在这两个回调里面统计单机的连接数，channelActive() 被调用，连接数加一，channelInActive() 被调用，连接数减一
另外，也可以在 channelActive() 方法中，实现对客户端连接 ip 黑白名单的过滤

3. channelRead()
服务端根据自定义协议来进行拆包在这个方法里面，每次读到一定的数据，都会累加到一个容器里面，然后判断是否能够拆出来一个完整的数据包，如果够的话就拆了之后，往下进行传递