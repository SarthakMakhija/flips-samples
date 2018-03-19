# flips-samples

[![Build Status](https://travis-ci.org/SarthakMakhija/flips-samples.svg?branch=master)](https://travis-ci.org/SarthakMakhija/flips-samples)

Sample **Spring Web &amp; Spring Boot** projects demonstrating the use of [Flips](https://github.com/Feature-Flip/flips) library.  

#### Versions of different libraries -
1. **flips-sample-spring-web**  uses Java 8, Spring version 4.2.3.RELEASE and Jackson version 2.7.5
2. **flips-sample-spring-boot** uses Java 8 and Spring Boot version 1.4.3.RELEASE
3. **flips-sample-spring-boot2** uses Java 8 and Spring Boot version 2.0.0.RELEASE

#### Running **flips-sample-spring-web** -
1. mvn clean package
2. java -jar flips-sample-spring-web/target/dependency/webapp-runner.jar flips-sample-spring-web/target/flips-sample-spring-web.war
3. Application should be up and running on 8080. URL - http://localhost:8080/index.html

Step 2 assumes that you are in the root (flips-samples) directory.

#### Running **flips-sample-spring-boot** -
1. mvn clean package
2. mvn spring-boot:run OR, java -Dspring.profiles.active=dev  -jar target/flips-sample-spring-boot.jar
3. Application should be up and running on 8080. URL - http://localhost:8080/index.html

Step 2 assumes that you are in the flips-sample-spring-boot directory.

#### Running **flips-sample-spring-boot2** -
1. mvn clean package
2. mvn spring-boot:run OR, java -Dspring.profiles.active=dev  -jar target/flips-sample-spring-boot2.jar
3. Application should be up and running on 8080. URL - http://localhost:8080/index.html

Step 2 assumes that you are in the flips-sample-spring-boot2 directory.


#### Notes -
1. Minimum Java version required to use Flips is Java 8
2. Though both these projects are WEB are projects, Flips provides **flips-core** JAR which can be used with NON-WEB projects. 
3. Both of these projects provide a simple **index.html** which serves as a simple documentation for the usage of various   @Flip annotations.

#### Screenshots - 
![Spring Web](https://github.com/SarthakMakhija/flips-samples/blob/master/flips-sample-spring-web/screenshot_running_app1.png)

![Spring Boot](https://github.com/SarthakMakhija/flips-samples/blob/master/flips-sample-spring-boot/screenshot_running_app.png)

![Spring Boot 2](https://github.com/SarthakMakhija/flips-samples/blob/master/flips-sample-spring-boot2/screenshot_running_app.png)