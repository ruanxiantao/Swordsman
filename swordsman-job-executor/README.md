# Swordsman Job Executor

## 简介

```shell
# Swordsman 项目定时任务执行器

# 采用 XXL-JOB 分布式定时任务平台
```

## XXL-JOB 安装步骤

```shell
# 本项目采用 Docker 安装 xxl-job-admin 调度中心

docker pull xuxueli/xxl-job-admin:2.1.0
	# 目前最新版 xxl-job 官方 docker 容器
	
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://39.100.114.202:13307/swordsman?useUnicode=true&characeterEncoding=utf-8&serverTimezone=UTC&useSSL=false --spring.datasource.username=root --spring.datasource.password=beluga@mysql. --spring.mail.username=18433216@qq.com --spring.mail.password=ajauqnyqkzqqbjfd" -p 10000:8080 -v /data/applogs:/data/applogs --name xxl-job-admin  -d xuxueli/xxl-job-admin
	# 用 PARAMS 修改配置文件属性，注意修改成自己的参数

# 调度中心启动成功后，在本项目配置调度中心地址，注册执行器

# 遵循XXL-JOB 官方文档，开始开发
```

[XXL-JOB 官方文档链接](https://github.com/xuxueli/xxl-job/blob/master/doc/XXL-JOB%E5%AE%98%E6%96%B9%E6%96%87%E6%A1%A3.md)