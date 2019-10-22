# SpringBoot Error

```shell
# 错误信息
Field redisUtil in com.swordsman.user.util.JwtUtil required a bean of type 'com.swordsman.common.redis.RedisUtil' that could not be found.

# 原因分析
SpringBoot 依赖 jar 包工程，依赖的jar 包IOC容器没有纳入本项目管理

# 解决方案
在启动类上加注解 @ComponentScan(basePackages = {"com.swordsman.common"})
```

