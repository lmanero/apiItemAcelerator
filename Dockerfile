FROM java:8
EXPOSE 8080
ADD /target/api-item-acelerator.jar api-item-acelerator.jar
ENTRYPOINT ["java","-jar","api-item-acelerator.jar"]