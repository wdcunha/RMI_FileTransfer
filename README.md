# RMI_FileTransfer

A simple system to send file from a server to the client using Java RMI.

## Instructions to run in a simple terminal screen:

* Open a session for running server side (Server directory) and use the command below:

`java StartFileServer`

* Open a different session and go to Client directory, so run the following command:

`java StartFileClient`

In this case it will be needed to change the server address in the StartFileClient.java to localhost.

## Instructions to run in a Docker Container:

* First of all, create the image running the command below that build the docker file inside Server directory:

`docker build -t servervol .`

* The same must have to be done in the Client directory:

`docker build -t clientvol .`

* Create a network to make the two containers communicate:

`docker network create rmi_fileTransfer`

* Run the servervol image giving a name and specifying a network to be visible to the client container and right after it should have ther server ready:

`docker run --network=rmi_fileTransfer --name server servervol`

* Run the clientvol image

`docker run --network=my_rmi_msgr_network -it clientvol `

Here there's a little trick that is to put the server address in the StartFileClient as the same name given in the previous command

Requirements
------------
Below is the list of technologies used to make it work:

* Docker Version 18.06.1-ce
* Java SE Development Kit 8
* Image openjdk from DockerHub
