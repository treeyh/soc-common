#!/bin/bash

#取当前目录
BASE_PATH=`cd "$(dirname "$0")"; pwd`

cd $BASE_PATH

# 打包发布
#mvn clean deploy -X -Dmaven.test.skip=true
mvn clean install