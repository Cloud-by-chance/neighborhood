

#docker image version1
#FROM openjdk:11
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]


#빌드 되는 시점에 JAR_FILE 경로
FROM openjdk:11


#빌드된 JAR_FILE을 app.jar로 복사
COPY ./build/libs/*SNAPSHOT.jar app.jar

#CMD ["mv", "/*.jar", "/app.jar"]
#ENTRYPOINT ["java", "-jar", "app.jar"]

# agent - 설치 경로 (현재 포트막혀있어서 이 방식으로 설치 x)
# RUN wget https://search.maven.org/remotecontent?filepath=co/elastic/apm/apm-agent-attach/1.30.1/apm-agent-attach-1.30.1.jar

COPY ./elastic-apm-agent-1.30.1.jar apm-agent.jar
#COPY ./apm-agent-attach-1.30.1.jar apm-agent.jar
#-Delastic.apm.secret_token=1234
ENTRYPOINT ["java","-javaagent:apm-agent.jar", "-Delastic.apm.service_name=NUVI-release -Delastic.apm.environment=production -Delastic.apm.application_Packages=com.example.nuvi_demo -Delastic.apm.server_urls=http://k8s-es-apmingre-6b3e599b30-1594117206.ap-northeast-2.elb.amazonaws.com","-jar", "app.jar"]

