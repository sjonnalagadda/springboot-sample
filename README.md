# Test project

This is a sample springboot project with health end-point

## Pre-requisites

+ JDK11
+ Maven 3.6.3+
+ Docker
+ JDK11 image with tag test/openjdk:11 on local machine. From DockerHub get this image ```openjdk:11``` and tag it as required.
+ Port 8080

## Setup quick test

+ Drop into shell prompt.
+ Execute command ```mvn -verison```.
+ One might see output similar to

    ```Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
    Maven home: /usr/local/apache-maven-3.6.3
    Java version: 11.0.6, vendor: Azul Systems, Inc., runtime: /Library/Java/JavaVirtualMachines/zulu-11.jdk/Contents/Home
    Default locale: en_US, platform encoding: UTF-8
    OS name: "mac os x", version: "10.16", arch: "x86_64", family: "mac"
    ```
## Quick test

+ Clone the project.
+ Navigate to project home directory from shell prompt.
+ Execute command ```mvn clean verify```. What happens ?
   * Docker image with tag ```test/example:0.0.1``` gets created. To validate use ```docker images``` command.
   * Docker container gets spun and [health](http://localhost:8080/actuator/health) end-point is accessed. 
        * Failure to access the health will result in build failure.
   * At the end of the process container gets shutdown.



