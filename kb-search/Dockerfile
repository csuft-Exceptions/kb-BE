FROM java:8
MAINTAINER csuft-exception
EXPOSE 9008
ARG JAR_FILE
ADD target/${JAR_FILE} /kb-search-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","/kb-search-1.0-SNAPSHOT.jar"]