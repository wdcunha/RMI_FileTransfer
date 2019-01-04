FROM openjdk

RUN mkdir -p /serverdata
WORKDIR /serverdata

COPY . /serverdata

EXPOSE 1099

CMD ["java", "StartFileServer"]
