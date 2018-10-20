
## 总结
1. 定义一个会话类 Session 用户维持用户的登录信息，用户登录的时候绑定 Session 与 channel，用户登出或者断线的时候解绑 Session 与 channel。
2. 服务端处理消息的时候只是做转发，即通过消息接收方的标识，拿到消息接收方的 channel，调用 writeAndFlush() 将消息发送给消息接收方。
