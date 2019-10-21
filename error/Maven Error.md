# Maven Error

## mvn install error

```shell
# 异常信息
[ERROR] Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:2.1.4.RELEASE:repackage (repackage) on project swordsman-common: Execution repackage of goal org.springframework.boot:spring-boot-maven-plugin:2.1.4.RELEASE:repackage failed: Unable to find main class -> [Help 1]

# 原因分析
common 项目中用的是 SpringBoot Maven Plugin，所以在打包时会去找 SpringBoot 的 main 程序入口

# 解决办法
将 Common 中 Plugin 的配置删掉就好
```

```shell
# 错误信息
[ERROR]     Non-resolvable parent POM for com.swordsman:swordsman-common:[unknown-version]: Could not find artifact com.swordsman:swordsman-parent:pom:1.0.0-SNAPSHOT and 'parent.relativePath' points at wrong local POM @ line 7, column 12 -> [Help 2]

# 原因分析
在 Common 项目 pom 文件中，没有指定父工程的 pom.xml 文件路径

# 解决办法
在 <parent> 标签中添加 <relativePath>../swordsman-parent/pom.xml</relativePath>
```

