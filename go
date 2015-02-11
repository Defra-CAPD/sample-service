#/bin/bash

function outputBuildComment() {
 echo "************************************"
 echo ""
 echo " " $1
 echo ""
 echo "************************************"
}

function clean() {
	outputBuildComment "Cleaning assembly"
	./sbt clean
}

function run_assembly() {

	clean

	outputBuildComment "Building assembly"
	./sbt assembly

	outputBuildComment "Running assembly"
	java -jar sample-service/target/sample-service.jar server SampleServiceConfig.yml
}

function run_dev() {
	outputBuildComment "Running dev"
	./sbt "project sample-service" "run-main uk.gov.defra.capd.sample.SampleServiceApplication server SampleServiceConfig.yml"
}



REPO_DIR="$( cd "$( dirname "${BASH_SOURCE:-$0}" )" && pwd )"
cd $REPO_DIR	

if [ "$1" = "-a" ]; then
	run_assembly
else
	run_dev
fi