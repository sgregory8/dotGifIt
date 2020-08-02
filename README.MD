### DotGifIt

A simple screen recorder made in java. This application will create a directory called DotGifIt in your 
user.home! This file contains config info and can be edited directly or using the application.

## To run

Should be directly runnable from an IDE but would advise the following to run without such over head

## Maven

```bash
cd <dotGifIt-root-directory>
mvn clean install
java -jar target/dot-gif-it-<version>.jar
```

## Running JAR directly

JAR available [here](https://github.com/sgregory8/dotGifIt/releases)

```bash
java -jar target/dot-gif-it-<version>.jar
```

## Known Issues

This was developed on mac and as such looks nicer and runs better on mac. Windows adds additional functionality of being able to 'click through' 
transparent windows. This seems to bug out the application when actions such as click and drag are attempted.