## Sample Service

#### Three-project build with API, Service and Client

API should contain representation classes and domain objects

Service will implement the API and should contain the Dropwizard resources and will be the executable application

Client will implement the API and make the calls to the service, to be used by other applications to communicate with sample-service


### Default contents

This is your Starter for Ten: a minimal build with only the basics for a running HTTP service with example metrics, capd-common and a healthcheck.

No database or DAL libraries are included to avoid inheriting libraries that aren't needed for your problem.

Go script provided will build the application and run the service class, or create an assembly to execute as a fat jar.