# User API Dev Guide

## Building
##### Developer tools
* OpenJDK8
* Docker
* Gradle 6.x
* Recommended IDE: Intellij or STS

##### Build & Development
###### from project folder
* **./docker-compose up** - to run postgreSQL on docker. Ensure springboot db connection matches the db running on docker.
* **./gradlew installDist** - to pull dependency jars
* **./gradlew bootRun** - to run. By default, springboot will be running on [localhost:8080](http://localhost:8080)
* Please visit [localhost:8080](http://localhost:8080) to explore the OpenApi documentation during development.
* To generate a client SDK: copy the json from [http://localhost:8080/api-docs](http://localhost:8080/api-docs and paste to [https://editor.swagger.io/](https://editor.swagger.io/). 
The editor will ask if you want to convert Json format to Yaml format, choose yes for nice formatting. Expand the **Generate Client** menu and choose the language you want to client to be written in. *e.g. Swift5, Kotlin*. Once you have selected, the browser should start downloading a generated client SDK based on your YAML. 

## Testing
##### For service integration tests, we use h2 in-memory database
###### from project folder
* **./gradlew clean test** - to clean and run tests
* **./gradlew build jacocoTestReport** - to generate test coverage report. Report will be available at - *./build/reports/jacoco/test/html/index.html*

## Deploying
* Deployment process should be automated through **CICD** pipelines.
* The following are guidelines for steps to deploy:
##### Deployment as Docker Images
* **ENTRYPOINT** on Dockerfile should reference to using external **application.properties** for production
* Build a docker image using **Dockerfile** on the project using **docker build**
* Alternative use the built in command *./gradlew bootRunImage* from **Springboot 2.3+**.
* Push docker image to container registry
* Use docker cluster manager like **Kubernates** to update docker image from container registry

##### Deployment as Runnable FatJar
* **./gradle build** generates the executable fat jar at build/libs
* copy the fatjar, *e.g* **userapi-1.0.0-SNAPSHOT.jar** to a prod server instance.
* Ensure postgreSQL is running, and run it : *java -jar userapi-1.0.0-SNAPSHOT.jar --spring.config.location*=**<application.properties on prod machine>** 

## Additional Information
##### Architectural design notes
* Non-blocking IO is applied on API calls using **@Async** and **CompletableFuture**. The Springboot App does not rely on Thread-Pool to handle simultaneous requests.
* A simple ConcurrentHashMap is added for caching results from fetching **User** and **Account**. Cache invalidated whenever there is a new entry to corresponding table.
* OpenApi documentation can be used to generate API Clients for multiple platforms including Swift, Java and JS by using **OpenAPI CodeGen**.
 
##### Future Roadmap
* Enhance caching mechanism with external cache like **Redis** to reduce memory pressure on springboot application.
* Add administrative calls to clear caching.
* Use **@CachePut**, cache merge mechanism when we have new db entry. So we avoid clearing the whole cache.
* Add **JavaDoc** to codebase to improve documentation.
* Add a **pre-push** git hook to execute **./gradlew clean test** and check if all tests pass before allowing push to remote.
* Automate the generation of API Clients in Swift and Kotlin by gradle task.
* Apply Logging configuration with SFJ4