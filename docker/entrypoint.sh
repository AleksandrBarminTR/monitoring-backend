#!/bin/bash

OPTIONALS=

#if [ -n "${DASHBOARD_URL}" ]; then
#    OPTIONALS="${OPTIONALS} --monitoring.dashboard.url=${DASHBOARD_URL}"
#fi

DB_URL=jdbc:mysql://${DB_HOST}:3306/${DB_NAME}

java -jar ${JARFILE} \
    --spring.datasource.url=${DB_URL} \
    --spring.datasource.username=${DB_USERNAME} \
    --spring.datasource.password=${DB_PASSWORD} \
    --github.username=${GITHUB_LOGIN} \
    --github.password=${GITHUB_TOKEN}
    $OPTIONALS
