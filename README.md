# UPOS Config Tool

Tiny util-application for Sberbank engineers with Java 17+, JavaFX 17+ and GraalVM 21.

## Description

The main purpose is to facilitate the work of Sberbank technical specialists working with the UPOS (universal software for POS terminals), which is installed in all models of Sberbank POS-terminals. The application allows to fine-tune the terminal configuration and perform all the necessary test and auxiliary operations.

## Documentation

A proper user's instruction you can find [here](https://github.com/pavelbelonosov/UPOS_Config_Tool/blob/249c2cc5892aadd2631383029218d79e00fe270c/web/guidePDF/%D0%98%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BA%D1%86%D0%B8%D1%8F%20UPOS%20Config-Tool.pdf).

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
## Testing and checkstyle

Run unit tests and see results in /target/site/jacoco/index.html
```bash
mvn test jacoco:report
```
Run checkstyle test and see results in /target/site/checkstyle.html
```bash
mvn jxr:jxr checkstyle:checkstyle
```
## Most recent version

UPOS Config Tool v.0.3
