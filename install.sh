#!/bin/bash

BIN_DIR=${HOME}/.local/bin/
VERSION=$(basename $(curl -Ls -o /dev/null -w %\{url_effective\} https://github.com/cbuschka/jmxtool/releases/latest))
INSTALL_BASE_DIR=${HOME}/.local/jmxtool

echo "Latest jmxtool version is: ${VERSION}"

INSTALL_DIR=${INSTALL_BASE_DIR}/${VERSION}
if [ ! -d "${INSTALL_DIR}" ]; then
  rm -rf ${INSTALL_DIR}.new
  TMP_FILE=$(mktemp)
  echo "Downloading jmxtool ${VERSION}..."
  curl -L --progress-bar -o ${TMP_FILE} https://github.com/cbuschka/jmxtool/releases/download/${VERSION}/jmxtool-bundle-${VERSION}.tgz
  echo "Installing jmxtool ${VERSION}..."
  mkdir -p ${INSTALL_DIR}.new
  tar xfz ${TMP_FILE} --strip-components=1 -C ${INSTALL_DIR}.new
  rm ${TMP_FILE}
  chmod 755 ${INSTALL_DIR}.new/bin/*.sh
  mv ${INSTALL_DIR}.new ${INSTALL_DIR}
  mkdir -p ${BIN_DIR}
  rm ${BIN_DIR}/jmxtool.sh
  ln -s ${INSTALL_DIR}/bin/jmxtool.sh ${BIN_DIR}/jmxtool.sh
  echo "jmxtool ${VERSION} successfully installed to ${INSTALL_DIR}. Start with 'jmxtool.sh help'."
else
  echo "jmxtool already is up to date."
fi
