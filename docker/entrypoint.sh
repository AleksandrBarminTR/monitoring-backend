#!/bin/bash

OPTIONALS=

#if [ -n "${DASHBOARD_URL}" ]; then
#    OPTIONALS="${OPTIONALS} --monitoring.dashboard.url=${DASHBOARD_URL}"
#fi

java -jar ${JARFILE} \
    $OPTIONALS
