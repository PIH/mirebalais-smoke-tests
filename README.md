
PIH-EMR Smoke Tests
=======================

The smoke tests can be run the same way regular tests are run, either through your IDE (in IntelliJ select a test class and then click on the Run or Debug icon) or Maven ("mvn run install" to build the module and run the tests).

However, there are few configuration steps required:

# Chromedriver

Make sure the version of Chrome installed on your system is compatible with the version of Chromedriver bundled with the tests. Chromedriver for Linux, Mac, and Windows is bundled in this repor in /src/test/resources/chromedriver

You can run chromedriver (ie in Linux execute ./chromedriver from /src/test/resources/chromedriver/linux) to determine what version is currently installed (it is 2.37 as of this writing)

Go here to see what browser versions this version of the driver supports:
http://chromedriver.storage.googleapis.com/2.37/notes.txt

You can see what version of Chrome you are running:
google-chrome --version

You should be able to upgrade with:
sudo apt-get install google-chrome-stable

Note that the above two steps can also be used to update the version of Chrome running on Bamboo, which is necessary when running the tests as part of the CI pipelein

All versions of chromedriver are available here:
https://chromedriver.storage.googleapis.com/index.html

# Config

You need to set the following environmental varaiables to tell the smoke tests what server and database to connect to:

* webAppUrl
* databaseUrl
* databaseUsername
* databasePassword

Check ou SmokeTestProperties.java for the format and default values of these variables.  I also often just hack the values I want into a local copy of SmokeTestProperties rather than setting up global properties.


# Requirements for running

* Make sure that your "admin" user has their default locale set to "Kreyol"
* Make sure that all locations have default wristband, label and ID card printers assigned

