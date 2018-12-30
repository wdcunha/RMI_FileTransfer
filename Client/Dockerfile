FROM openjdk

RUN mkdir -p /clientdata
WORKDIR /clientdata

COPY . /clientdata

EXPOSE 1099

RUN chmod +x runClient.sh

CMD ["runClient.sh"]
