
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
ENTRYPOINT ["java", "-jar", "app.jar"]

COPY elastic-apm-agent-1.30.1.jar /apm-agent.jar

CMD ["/usr/bin/java","-javaagent:/apm-agent.jar", "-Delastic.apm.service_name=NUVI-release -Delastic.apm.secret_token=1234 -Delastic.apm.application_Aackages=com.example.nuvi_demo -Delastic.apm.server_urls=k8s-es-apmingre-6b3e599b30-1594117206.ap-northeast-2.elb.amazonaws.com","-jar", "/app.jar"]
