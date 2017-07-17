# SQLDroid4SQLCipher

SQLDroid4SQLCipher is a JDBC driver for Android's encrypted sqlite database using SQLCipher (https://www.zetetic.net/sqlcipher/). This is bases on original SQLDroid v1.0.1 version. See http://sqldroid.org/.

## Download

Binary release of the Jdbc driver, sqldroid4SQLCipher.jar, is avalaible in the app/lib/ directory of the project

## Usage

You can refer to the http://sqldroid.org for basic usage of the driver.

To specify the encryption password of the database you need to set the password of jdbc connection.

## Building the Jdbc driver

Basically this is an Android Studio Project, so you can use this IDE to build the jdbc driver.

In alternative you can use gradle to build the debug or release of the driver jar file.

You can find the project module of the driver in the directory sqldroid4SQLCipher where you can launch gradle using one of the custom tasks:

```createDebugJar```

```createReleaseJar```

## The Sample Application

In the app directory you can find a very basic app that use flyway to manage an encrypted database using this jdbc driver.
