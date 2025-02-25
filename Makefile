#!/usr/bin/bash

CLASSPATH = "lib/spigot-api-1.21.1-R0.1-SNAPSHOT.jar"
SRC = $(wildcard src/main/*.java)
CLS = $(patsubst src/main/%.java,$(PKGPATH)/%.class,$(SRC))
BLDPATH = build
PKGPATH = $(BLDPATH)/net/ddns/haruhionly/haruhi_bot
JAR = Haruhi.jar

.PHONY : default, build, clean

default: build

build:
	javac -cp $(CLASSPATH) -d build $(SRC)
	echo $(SRC)
	echo $(OBJ)
	echo $(CLS)
	jar -cf $(BLDPATH)/$(JAR) $(CLS)

clean:
	rm -rf $(BLDPATH)
