#!/bin/bash

SCRIPT=$0
if [ -s "${SCRIPT}" ]; then
  SCRIPT=$(readlink -f "$0")
fi

INSTALL_DIR=$(cd `dirname "$SCRIPT"`/.. && pwd -P)

LIB_DIR=${INSTALL_DIR}/lib/

CLASSPATH=
for j in ${LIB_DIR}/*.jar; do
  CLASSPATH=${CLASSPATH}:${j}
done

JMXTOOL_JAVA_OPTS=
OPT_ENV_FILE=${HOME}/.jmxtool/env
if [ -f "${OPT_ENV_FILE}" ]; then
  source ${OPT_ENV_FILE}
fi

exec java -cp ${CLASSPATH} ${JMXTOOL_JAVA_OPTS} com.github.cbuschka.jmxtool.Main "$@"
