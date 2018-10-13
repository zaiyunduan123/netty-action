
## 为什么会有粘包半包现象？
1. 尽管我们的应用层是按照 ByteBuf 为 单位来发送数据，但是到了底层操作系统仍然是按照字节流发送数据，因此，数据到了服务端，也是按照字节流的方式读入
2. 然后到了 Netty 应用层面，重新拼装成 ByteBuf，而这里的 ByteBuf 与客户端按顺序发送的 ByteBuf 可能是不对等的，所以会有粘包半包现象

## 解决
不断从 TCP 缓冲区中读取数据，每次读取完都需要判断是否是一个完整的数据包

## Netty 自带的拆包器
1. 固定长度的拆包器 FixedLengthFrameDecoder
2. 行拆包器 LineBasedFrameDecoder
3. 分隔符拆包器 DelimiterBasedFrameDecoder
4. 基于长度域拆包器 LengthFieldBasedFrameDecoder
