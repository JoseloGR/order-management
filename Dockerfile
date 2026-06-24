FROM ubuntu:latest
LABEL authors="joselo"

ENTRYPOINT ["top", "-b"]