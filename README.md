# Vert.x Java Helper
[![Build Status](https://travis-ci.org/rapatao/vertx-java-helper.svg?branch=master)](https://travis-ci.org/rapatao/vertx-java-helper) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/b18900caf6324d08840cd97897677592)](https://www.codacy.com/app/rapatao/vertx-java-helper?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=rapatao/vertx-java-helper&amp;utm_campaign=Badge_Grade)

Provides a simple and useful helpers to Vert.x

## Setup

Add the dependency to your project and use the provided methods to register and consume the service.
```xml
<dependency>
    <groupId>com.rapatao.vertx</groupId>
        <artifactId>vertx-java-helper</artifactId>
    <version>0.0.1-SNAPSHOT</version>
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