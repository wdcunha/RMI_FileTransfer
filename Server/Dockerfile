FROM openjdk

RUN mkdir -p /serverdata
WORKDIR /serverdata

COPY . /serverdata

EXPOSE 1099

RUN chmod +x runServer.sh

CMD ["runServer.sh"]
