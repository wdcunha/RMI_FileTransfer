#!/bin/sh

java StartFileServer
RUN chmod +x runServer.sh

CMD ["runServer.sh"]
