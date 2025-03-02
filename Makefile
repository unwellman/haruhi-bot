#!/usr/bin/bash

CLASSPATH = "lib/spigot-api-1.21.1-R0.1-SNAPSHOT.jar:lib/JDA-5.3.0.jar:src/main/resources/plugin.yml"

LIB = lib/spigot-api-1.21.1-R0.1-SNAPSHOT.jar
LIB += lib/JDA-5.3.0.jar
LIB += src/main/resources/plugin.yml"

PKGPATH = net/ddns/haruhionly/haruhi
SRC = $(wildcard src/main/java/$(PKGPATH)/*.java)
CLS = $(patsubst src/main/java/%.java,%.class,$(SRC))
JAR = Haruhi.jar

### CHANGE THIS TO RELEVANT PLUGINS DIRECTORY ###
SERVER = ../haruhi/plugins/
#################################################

.PHONY : default, build, clean

default: build

Haruhi.jar: build

build: $(SRC)
	javac -Xdiags:verbose -cp $(CLASSPATH) $(SRC)
	cd src/main/java; \
	link ../resources/plugin.yml plugin.yml; \
	link ../resources/defaults.yml defaults.yml; \
	jar -xf ../../../lib/JDA-5.3.0.jar; \
	jar -cf ../../../$(JAR) $(CLS) plugin.yml *; \
	rm -rf club com gnu google javax kotlin META-INF natives net/dv8tion okhttp3 okio org tomp2p; \
	rm plugin.yml defaults.yml; \

install: Haruhi.jar
	cp $(JAR) $(SERVER)

clean:
	rm -f $(JAR)
	cd src/main/java; \
	rm -f $(CLS)
