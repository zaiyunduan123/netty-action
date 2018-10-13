
## netty学习代码
1. 基于 ByteToMessageDecoder，实现自定义解码，而不用关心 ByteBuf 的强转和 解码结果的传递。
2. 基于 SimpleChannelInboundHandler，实现每一种指令的处理，不再需要强转，不再有冗长乏味的 if else 逻辑，不需要手动传递对象。
3. 基于 MessageToByteEncoder，实现自定义编码，而不用关心 ByteBuf 的创建，不用每次向对端写 Java 对象都进行一次编码。
