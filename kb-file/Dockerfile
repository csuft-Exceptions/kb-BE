#使用的JDK版本
FROM java:8
# 作者
MAINTAINER csuft-exception
#镜像暴露的端口
EXPOSE 9009
#外部传参 传入微服务名字
ARG JAR_FILE
#将镜像添加到容器内  target/${JAR_FILE}这是服务编译打包后的jar文件打包路径  /app.jar是容器内的jar文件路径
ADD target/${JAR_FILE} /kb-file.jar
#容器内部 运行微服务
ENTRYPOINT ["nohup","java", "-jar","/kb-file.jar"]