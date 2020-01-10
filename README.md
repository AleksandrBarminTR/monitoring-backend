Product backlog (outdated but still):
=

* ~~Increase test coverage, now only 44% of lines are covered. I don't think it's reliable enough~~
* Add Swagger to publish API correctly and smoothly
* Jenkins job statutes:
  * ~~Entities to store the status~~
  * ~~Service to retrieve jobs by head hash~~
  * ~~Create a job to check for new pull requests~~
  * ~~Create a job to check status of existing jobs~~
  * Access Jenkins using REST API in order to check jobs
  * Post comments when something happens with jobs
  * Create a scheduler to run jobs
  * Connect everything to the database
  * ~~Get rid of H2 dependency~~
* Deployment
  * Pack everything to the Docker container
  * Pack MySql somewhere near
* Integration
  * Configure GitHub Actions to run tests regularly
* Improvements
  * Switch to Gradle instead of Maven
