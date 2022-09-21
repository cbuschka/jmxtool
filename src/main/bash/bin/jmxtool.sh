#!/bin/bash

INSTALL_DIR=$(cd `dirname "$0"`/.. && pwd -P)

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
