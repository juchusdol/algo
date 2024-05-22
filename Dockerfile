FROM ubuntu:latest
LABEL authors="locko"

ENTRYPOINT ["top", "-b"]