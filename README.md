
## 总结
对于 Netty 的设计来说，handler 其实可以看做是一段功能相对聚合的逻辑，然后通过 pipeline 把这些一个个小的逻辑聚合起来，串起一个功能完整的逻辑链。既然可以把逻辑串起来，也可以做到动态删除一个或多个逻辑。