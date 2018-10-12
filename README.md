
## netty学习代码
1. 通过给逻辑处理链 pipeline 添加逻辑处理器，来编写数据的读写逻辑
2. 不管是服务端还是客户端，收到数据之后都会调用到 channelRead 方法
3. 调用writeAndFlush方法写数据到对端
4. 客户端与服务端交互的二进制数据载体为 ByteBuf，ByteBuf 通过连接的内存管理器创建，字节数据填充到 ByteBuf 之后才能写到对端