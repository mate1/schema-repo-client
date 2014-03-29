# schema-repo-client

A strongly typed implementation of a caching schema repository client written in Scala.

See the [AVRO-1124 project](https://github.com/mate1/avro/tree/release-1.7.5-with-AVRO-1124) for the server this client interacts with.

## Getting started

Compile:

    $ ./activator compile

Package:

    $ ./activator package
    $ ls -l target/scala-2.10/schema-repo-client_2.10-0.1-SNAPSHOT.jar

Launch the test suite:

    $ ./activator test
    
For more features (such as point and click IDE integration), you can try the Typesafe activator UI:

    $ ./activator ui

## Integrating in your code

Look at the com.mate1.avro.repo.client.example.RepoExample object for an example on how to use the provided traits to create a singleton object that you can then use from anywhere in your code.
