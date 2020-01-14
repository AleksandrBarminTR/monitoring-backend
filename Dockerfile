FROM amazoncorretto:11
LABEL maintainer="aleksandr.barmin@thomsonreuters.com"

ARG service_name
ARG jar_path=/opt/${service_name}.jar

ADD target/${service_name}*.jar ${jar_path}
ADD docker/entrypoint.sh /opt/entrypoint.sh

EXPOSE 8080

ENV JARFILE ${jar_path}

ENTRYPOINT ["/opt/entrypoint.sh"]
