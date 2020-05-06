
PIH-EMR Smoke Tests
=======================

The smoke tests can be run the same way regular tests are run, either through your IDE (in IntelliJ select a test 
class and then click on the Run or Debug icon) or Maven ("mvn run install" to build the module and run the tests).

These are the test suites available. Each suite expects to be run against a server that is using a
particular configuration repo and PIH Config.

| Suite                                                             | Config Repo                                                    | PIH Config                                  |
|-------------------------------------------------------------------|----------------------------------------------------------------|---------------------------------------------|
| [mirebalais](https://bamboo.pih-emr.org/browse/MIREBALAIS-STHC)   | [zl](https://github.com/PIH/openmrs-config-zl)                 | mirebalais,mirebalais-humci                 |
| [haiti](https://bamboo.pih-emr.org/browse/MIREBALAIS-STC)         | [zl](https://github.com/PIH/openmrs-config-zl)                 | haiti,haiti-thomonde,haiti-thomonde-ci      |
| [liberia](https://bamboo.pih-emr.org/browse/MIREBALAIS-STP)       | [pihliberia](https://github.com/PIH/openmrs-config-pihliberia) | liberia,liberia-harper,liberia-harper-kouka |
| [mentalHealth](https://bamboo.pih-emr.org/browse/MIREBALAIS-STMH) | [zl](https://github.com/PIH/openmrs-config-zl)                 | haiti-mentalhealth                          |
| [mexico](https://bamboo.pih-emr.org/browse/MIREBALAIS-STM)        | [ces](https://github.com/PIH/openmrs-config-ces)               | *not written yet*                           |

# Setup

## Chromedriver

Chromedriver is bundled with this repo in `/src/test/resources/chromedriver`. It is presently at version 2.37.
You must have a [compatible version](http://chromedriver.storage.googleapis.com/2.37/notes.txt) of Chrome installed.

To check your Chrome version in Linux, execute `google-chrome --version`. 
To upgrade Chrome on Ubuntu (including on the [Bamboo server](https://bamboo.pih-emr.org/allPlans.action))
you should be able to use `sudo apt install google-chrome-stable`.

Other versions of Chromedriver can be found [here](https://chromedriver.storage.googleapis.com/index.html).

## Config

The following environment variables configure connections to the server and database.

* WEBAPP_URL (default `http://localhost:8080/openmrs`)
* DATABASE_URL (default `jdbc:mysql://localhost:3306/openmrs`)
* DATABASE_USERNAME (default `openmrs`)
* DATABASE_PASSWORD (default `openmrs`)
* WEBAPP_NAME (default `openmrs`)

These variables are parsed by
[SmokeTestProperties.java](https://github.com/PIH/mirebalais-smoke-tests/blob/master/src/main/java/org/openmrs/module/mirebalais/smoke/helper/SmokeTestProperties.java),
which also provides their defaults. It may sometimes be more convenient to just change these default values locally
in lieu of setting the environment variables.

### Dockerized MySQL

If using the MySQL container used by the OpenMRS SDK, use the following settings.

```
DATABASE_URL=jdbc:mysql://localhost:3308/<server_name>
DATABASE_USERNAME=root
DATABASE_PASSWORD=Admin123
```

Where the database name, `<server_name>`, is the SDK server name.

## Additional setup

* The "admin" user must be a provider and have all roles.
  
  Go to the legacy Admin UI --> Manage Users --> admin. Check the box for
  "Create a provider...". Then check every role box (try using Tab
  and Spacebar).

### For Haiti

* The locale must be "ht"/"Kreyol", at least for the "admin" user.
* Patient Identifier Sources for ZL EMR ID must be configured as described in
    the [Mirebalais README](https://github.com/PIH/openmrs-module-mirebalais#step-7-create-a-local-identifier-source).
* Make sure that all locations have default wristband, label and ID card printers assigned.

# Running Tests

Tests can be run using `mvn clean verify -U -P<suite>`, where `<suite>` is one of the values in the
table above. It should also be possible to run the tests by navigating to them in IntelliJ and clicking on
the "Run" buttons in the left margin.

The tests must be configured to use a server that is compatible with the suite being run.
