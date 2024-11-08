FROM ubuntu:latest
LABEL authors="apkga"

ENTRYPOINT ["top", "-b"]