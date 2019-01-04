FROM openjdk

RUN mkdir -p /clientdata
WORKDIR /clientdata

COPY . /clientdata

EXPOSE 1099

CMD ["java", "StartFileClient"]
