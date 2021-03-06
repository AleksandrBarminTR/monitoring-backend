FROM amazoncorretto:11
LABEL maintainer="aleksandr.barmin@thomsonreuters.com"

ARG service_name
ARG jar_path=/opt/${service_name}.jar

ADD target/${service_name}*.jar ${jar_path}
ADD docker/entrypoint.sh /opt/entrypoint.sh

EXPOSE 8080

ENV JARFILE ${jar_path}
ENV DB_HOST localhost
ENV DB_NAME monitoring
ENV DB_USERNAME monitoring
ENV DB_PASSWORD monitoring

ENV GITHUB_LOGIN AleksandrBarminTR
ENV GITHUB_TOKEN 8a6d36a9a819b9037af91c1ef0e1461a07fb5bcb

ENTRYPOINT ["/opt/entrypoint.sh"]
