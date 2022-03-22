# UPOS Config Tool

A powerful util-application for Sberbank engineers with Java 17+, JavaFX 17+ and GraalVM 21.

## Description

The main purpose of the application is to facilitate the work of Sberbank technical specialists working with the UPOS (universal software for POS terminals), which is installed in all models of Sberbank POS-terminals. The application allows to fine-tune the terminal configuration and perform all the necessary test and auxiliary operations.

## Documentation

A proper user's instruction you can find [here]().

## Quick guide to Gluon

GluonFX plugin is used to build a native image for Desktop platform. More information about Gluon Substrate Project you can find [here](https://docs.gluonhq.com/).

Run the application on JVM/HotSpot:
```bash
mvn gluonfx:run
```
Run the application and explore all scenarios to generate config files for the native image with:
```bash
mvn gluonfx:runagent
```
Build a native image using:
```bash
mvn gluonfx:build
```
Run the native image app:
```bash
mvn gluonfx:nativerun
```
## Most recent version

UPOS Config Tool v.0.2
