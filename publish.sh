#!/usr/bin/env bash

set -ex

sbt "project core" +publishSigned
sbt sonatypeReleaseAll

docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD"
sbt docker:publishLocal
docker push indix/schemer-registry:${TRAVIS_TAG}
docker tag indix/schemer-registry:${TRAVIS_TAG} indix/schemer-registry:latest
docker push indix/schemer-registry:latest
