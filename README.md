# Vert.x Java Helper
[![Build Status](https://travis-ci.org/rapatao/vertx-java-helper.svg?branch=master)](https://travis-ci.org/rapatao/vertx-java-helper) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b18900caf6324d08840cd97897677592)](https://www.codacy.com/app/rapatao/vertx-java-helper?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rapatao/vertx-java-helper&amp;utm_campaign=Badge_Grade) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rapatao.vertx/vertx-java-helper/badge.svg)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.rapatao.vertx%22%20AND%20a%3A%22vertx-java-helper%22)

Provides a simple and useful helpers to Vert.x

## Setup

Add the dependency to your project and use the provided methods to register and consume the service.
```xml
<dependency>
    <groupId>com.rapatao.vertx</groupId>
        <artifactId>vertx-java-helper</artifactId>
    <version>${vertx-java-helper.version}</version>
</dependency>
```

The following repository allows you to access the dependency in OSSRH directly, just add the configuration in your "pom.xml" to get the wanted version.
```xml
<repositories>
    <repository>
        <id>sonatype-public-repository</id>
        <name>oss.sonatype.org public repository</name>
        <url>https://oss.sonatype.org/content/groups/public/</url>
    </repository>
</repositories>
```

## Usage

The following example shows how to handle a Future<T>:

```java
final Future<Object> future = ...

future.setHandler(AsyncHandler.builder()
    .onSuccess(success -> {
        // handle success event
    })
    .onFail(fail -> {
        // handle fail event
    })
    .onComplete(() -> {
        // handle complete event
    }).build());
```