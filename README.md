# Spring Boot and Ubisecure SSO integration with OpenID Connect

## Introduction

This is a sample Spring Boot application to illustrate integration with OpenID Connect Auhthorization Code flow. 

## Configuration

An OpenID Connect Client needs to be configured with information about the OpenID Connect Provider and client credentials. This sample app puts these configuration items into [application.yml](src/main/resources/application.yml) file.

### application.yml

This configuration supports `default` profile for local testing. 

```yml
spring:
  config.activate.on-profile: default
  security.oauth2.client.provider.ubisecure:
    issuer-uri: https://login.example.ubidemo.com/uas
  security.oauth2.client.registration.ubisecure:
    clientId: 41081527-13dd-49c3-b8ae-9b1ff5db656b
    clientSecret: cCmm0k1soIRIv1K3kYxEisvMi1nrYO95
    scope: openid
server:
  port: 8080
```

Replace values for `issuer-uri`, `clientId` and `clientSecret` when integrationg with an other OpenID Connect provider. Value of `redirect_uri` is defined by Spring Boot middleware

```
http://localhost/login/oauth2/code/ubisecure
```

## Code review

This application is loosely based on the [Spring Boot starter application](https://github.com/spring-guides/gs-spring-boot). The files modified or created for this integration are

* [Application.java](src/main/java/com/example/springboot/Application.java)
* [HomeController.java](src/main/java/com/example/springboot/HomeController.java)
* [home.html](src/main/resources/templates/home.html)
* [pom.xml](pom.xml)

### Application.java

The main `Application` class of a Spring Boot application

```java
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

### HomeController.java

This app has a single controller bound to `/`. The purpose of `@RolesAllowed` is to make sure only authenticated users are allowed. `OAuth2User` represents the authenticated user and is passeed as model attribute to `home.html` view.

```java
@RolesAllowed("ROLE_USER")
@Controller
public class HomeController {
	@GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal, Model model) {
		model.addAttribute("principal", principal);
		return "home";
	}
}
```

### home.html

The `home.html` view creates a list with user claims.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
</head>
<body>
	<h1>Welcome</h1>
	<dl>
		<th:block th:each="i : ${principal.attributes}">
			<dt>
				<b th:text="${i.key}"></b>
			</dt>
			<dd>
				<i th:text="${i.value}"></i>
			</dd>
		</th:block>
	</dl>
</body>
</html>
```

### pom.xml

This app is based on `spring-boot-starter-parent` and adds dependencies to `spring-boot-starter-web`, `spring-boot-starter-thymeleaf` and `spring-boot-starter-oauth2-client`.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.0</version>
		<relativePath />
	</parent>
	<groupId>com.example</groupId>
	<artifactId>spring-boot-sample</artifactId>
	<version>0.0.1-SNAPSHOT</version>

	<properties>
		<java.version>8</java.version>
		<exec.mainClass>com.example.springboot.Application</exec.mainClass>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-thymeleaf</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<configuration>
					<source>8</source>
					<target>8</target>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>
```

## Launching

Use your favorite IDE to launch this application on http://localhost:8080

This application is also deployed live on Azure Web Apps at https://ubi-spring-boot-sample.azurewebsites.net

### Command line

You first need to install [Git tools](https://git-scm.com/downloads), [Java JDK](https://github.com/AdoptOpenJDK/openjdk8-upstream-binaries/releases) and [Apache Maven](https://maven.apache.org/install.html)

The following will launch the application on http://localhost:8080

```
git clone https://github.com/psteniusubi/spring-boot-sample.git
cd spring-boot-sample
mvn exec:java
```
