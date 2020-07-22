FROM java:8
LABEL maintainer=“harshanand@rsystems.com”
RUN apt-get install -y curl
EXPOSE 8085
ADD target/gateway-0.0.1-SNAPSHOT.war gateway-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java","-jar","gateway-0.0.1-SNAPSHOT.war","--spring.profiles.active=prod"]
