# Make and Build vessl webapp

Welcome to the Make & Build sample webapp for the [vessl](https://github.com/makeandbuild/vessl) framework which
* provides sample REST resources
* provides sample mega fixtures
* provides simple JDBC persistence and validation logic
* demonstrates the build concepts needed to get up and running quickly
* is gradle based for build mgmt

Make & Build Vessl Web App is licensed under the Apache Public License, Version 2.0 (see [LICENSE](./LICENSE))

# Setup

In addition to running on a linux/mac envrionment as a pre-requesette, we assume that you've installed the following:
* gradle
* jetty (version 9.1.0)
* mysql

Create your database

    mysql -u root
    create database vessl

Now create the user and event tables

    mysql -u root vessl < ./src/test/resources/create_user.sql
    mysql -u root vessl < ./src/test/resources/create_event.sql

# Starting the web app

There are a bunch of scripts in the ./scripts folder to make your life a little easier, the following kills jetty, runs the fixtures, builds the war, redeploys it, and tails your jetty logs

    ./scripts/restart.sh

# Integration tests

Check out the tests in [EventResourceTest](./src/test/java/com/makeandbuild/vessl/sample/rest/EventResourceTest.java) and [UserResourceTest](./src/test/java/com/makeandbuild/vessl/sample/rest/UserResourceTest.java) for a http client to the REST resources.  Once you've started the web app above, you can run all of the integration tests via:

    gradle clean integrationTest

## Mega fxitures

The mega fxitures are defined in [resources/fixturesgen/com.makeandbuild.vessl.sample.domain.User.json](https://github.com/makeandbuild/vessl-webapp/blob/master/src/test/resources/fixturesgen/com.makeandbuild.vessl.sample.domain.User.json).  To regenerate them again:

    cd src/fixturesgen
    npm install
    ./load.sh

You can load them, which is not part of the restart script via:

    gradle megaLoad

Likewise, to remove them:

    gradle megaPurge
























