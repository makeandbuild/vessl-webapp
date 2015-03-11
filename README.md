# Make and Build Vessl Web App

Welcome to the Make & Build Sample webapp for the <a href="https://github.com/makeandbuild/vessl">Vessl</a> framework which
* provides sample REST resources
* demonstrates the build concepts needed to get up and running quickly
* is gradle based for build mgmt

Make & Build Vessl Web App is licensed under the Apache Public License, Version 2.0 (see [LICENSE](./LICENSE))


# Pre-requisettes

In addition to running on a linux/mac envrionment, we assume that you've installed the following:
* gradle
* jetty (version 9.1.0)
* mysql


# Setup

Create your database

    mysql -u root
    create database vessl

Now create the user and event tables

    mysql -u root vessl < ./src/test/resources/create_user.sql
    mysql -u root vessl < ./src/test/resources/create_event.sql

# Starting the web app

There are a bunch of scripts in the ./scripts folder to make your life a little easier, the following kills jetty, runs the fixtures, builds the war, redeploys it, and tails your jetty logs

    ./scrtips/restart.sh


# Integration tests

Check out the tests in [EventResourceTest](./src/test/java/com/makeandbuild/vessl/sample/rest/EventResourceTest.java) and [UserResourceTest](./src/test/java/com/makeandbuild/vessl/sample/rest/UserResourceTest.java) for a http client to the REST resources


























