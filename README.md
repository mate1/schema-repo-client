# schema-repo-client

A strongly typed implementation of a caching schema repository client written in Scala.

See the [AVRO-1124 project](https://github.com/mate1/avro/tree/release-1.7.5-with-AVRO-1124) for the server this client interacts with.

## Getting started

Compile:

    $ ./activator compile

Package:

    $ ./activator package
    $ ls -l target/scala-2.10/schema-repo-client_2.10-0.1-SNAPSHOT.jar
    
Launch the Typesafe activator UI:

    $ ./activator ui
