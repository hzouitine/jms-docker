# jms-docker
Testing JMS with ActiveMQ Docker Image

- We pull activemq docker image : 
docker pull rmohr/activemq
- then run : 
docker run -p 61616:61616 -p 8161:8161 rmohr/activemq
- We can access to the WebConsole using : http://localhost:8161 
The user/password are : admin/admin
- We connect to the Broker using : tcp://localhost:61616
- Run the Projet you will see that a message was sent and then recieved by ActiveMQ

-- Run the project with Maven Docker Image

sudo docker run -it --rm --name demo --network=host -v "$(pwd)":/usr/src/jms-docker -v "$HOME/.m2":/root/.m2 -w /usr/src/jms-docker maven:3.6.2-jdk-8 mvn clean compile assembly:single


