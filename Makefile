#!/usr/bin/bash

CLASSPATH = "lib/spigot-api-1.21.1-R0.1-SNAPSHOT.jar:lib/JDA-5.3.0.jar:src/main/resources/plugin.yml"

PKGPATH = net/ddns/haruhionly/haruhi
SRC = $(wildcard src/main/java/$(PKGPATH)/*.java)
CLS = $(patsubst src/main/java/%.java,%.class,$(SRC))
JAR = Haruhi.jar

.PHONY : default, build, clean

default: build

build:
	javac -cp $(CLASSPATH) $(SRC)
	cd src/main/java; \
	cp ../resources/plugin.yml plugin.yml; \
	jar -cf ../../../$(JAR) $(CLS) plugin.yml; \
	rm plugin.yml

clean:
	rm -f $(JAR)
	cd src/main/java; \
	rm -f $(CLS)
