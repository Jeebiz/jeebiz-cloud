#FROM openjdk:11-jre-alpine
#FROM openjdk:11-jre-slim
#FROM openjdk:11-jdk-alpine
#FROM openjdk:11-jdk-slim
#FROM openjdk:11-jdk-oracle
#FROM openjdk:8-jre-alpine
#FROM openjdk:8-jre-slim
#FROM openjdk:8-jdk-alpine
#FROM openjdk:8-jdk-slim
#FROM openjdk:8-jdk-oracle
FROM wans-hz-docker.pkg.coding.net/wisdom-edu/base-image/openjdk:8-jdk-alpine
#FROM wans-hz-docker.pkg.coding.net/wisdom-edu/base-image/openjdk:11-jdk-alpine-skywalking
#FROM wans-hz-docker.pkg.coding.net/wisdom-edu/base-image/openjdk:11-jdk-alpine-pinpont

# 申明临时卷
VOLUME /logs
VOLUME /tmp

# 拷贝本地Jar包到根目录
ADD ./target/jeebiz-cloud-sample-webmvc.jar /

# 挂着启动脚本
CMD ["sh", "-c", "java -jar jeebiz-cloud-sample-webmvc.jar"]