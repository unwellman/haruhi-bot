#!/usr/bin/bash

CLASSPATH = "lib/spigot-api-1.21.1-R0.1-SNAPSHOT.jar"
SRC = src/main/*.java
JAR = bot.jar

.PHONY : default, build, clean

default: build

build:
	javac -cp $(CLASSPATH) -d build $(SRC)
	jar -cf build/$(JAR) build/net/ddns/haruhionly/haruhi/Haruhi.class

clean:
	rm -rf build
