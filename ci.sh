#! /bin/bash
set -e

IMAGE=kelog/tracking-app:latest

docker build -t ${IMAGE} . && docker push ${IMAGE}

kubectl config use-context ovh

kubectl get pods | grep tracking-app | awk '{print $1}' | xargs kubectl delete pod
