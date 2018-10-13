
## netty学习代码
1. 通过编写客户端服务端处理逻辑，引出了 pipeline 和 channelHandler 的概念。
2. channelHandler 分为 inBound 和 outBound 两种类型的接口，分别是处理数据读与数据写的逻辑，可与 tcp 协议栈联系起来。
3. 两种类型的 handler 均有相应的默认实现，默认会把事件传递到下一个，这里的传递事件其实说白了就是把本 handler 的处理结果传递到下一个 handler 继续处理。
4. inBoundHandler 的执行顺序与我们实际的添加顺序相同，而 outBoundHandler 则相反。

