#!/usr/bin/env bash
set -e -x

git clone basic-api-code basic-api

cd basic-api

mvn clean

mvn install
