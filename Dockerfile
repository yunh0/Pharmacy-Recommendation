FROM ghcr.io/graalvm/graalvm-ce:ol8-java17
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","./app.jar"]
