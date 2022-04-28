FROM openjdk:11

#빌드 되는 시점에 JAR_FILE 경로
ARG JAR_FILE=build/libs/*.jar

#빌드된 JAR_FILE을 app.jar로 복사
COPY ${JAR_FILE} /
CMD ["mv", "/*.jar", "/app.jar"]

ENTRYPOINT ["java", "-jar", "/app.jar"]

#COPY elastic-apm-agent-1.30.1.jar /apm-agent.jar

#CMD ["/usr/bin/java","-javaagent:/apm-agent.jar", "-Delastic.apm.service_name=NUVI-Service -Delastic.apm.application_Aackages=com.example.nuvi_demo -Delastic.apm.server_urls=http://k8s-es-kibanain-71666d7ab6-10649648.ap-northeast-2.elb.amazonaws.com","-jar", "/app.jar"]
