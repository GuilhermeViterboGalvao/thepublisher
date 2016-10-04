#!/bin/bash
#

###########################################################
# Checking if user is runnig script as ROOT os using SUDO #
###########################################################
#if [ "$(id -u)" != "0" ]; then
#	echo "This script must be run as root" 1>&2
#	exit 1
#fi

#########################
# Removing old log file #
#########################
if [ -f "jetty-out.log" ]; then
	rm -f jetty-out.log
fi

##################
# Starting Jetty #
##################
profile=$1

mavenCommand=""
jettyParams=""
appParams=""
uploadFilesDir=""
photosDir=""
tempDir=""
staticsDir=""

exec="true"

if [ "$profile" == "prd" ]; then

	export JAVA_OPTS="$JAVA_OPTS -server -Xmx5120m -Xmn1280m -XX:+UseParallelGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/tomcat/thepublisher/dumps"
	mavenCommand="mvn clean jetty:run-exploded"
	jettyParams="-Djetty.port=8080"
	appParams="-Dpublisher.log.path=/u2/the-publisher-files/logs -Drunning.context=prd -Dis.jetty.server=true"
	uploadFilesDir="-Dupload.files.dir=/u2/the-publisher-files/upload-files"
	photosDir="-Dphotos.dir=/u2/the-publisher-files/photos"
	tempDir="-Dtemp.dir=/u2/the-publisher-files/temp"
	staticsDir="-Dstatics.dir=/u2/the-publisher-files/statics"
	
elif [ "$profile" == "hlg" ]; then

	export JAVA_OPTS="$JAVA_OPTS -server -Xmx5120m -Xmn1280m -XX:+UseParallelGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/tomcat/thepublisher/dumps"
	mavenCommand="mvn clean jetty:run-exploded"
	jettyParams="-Djetty.port=8080"
	appParams="-Dpublisher.log.path=/u2/the-publisher-files/logs -Drunning.context=hlg -Dis.jetty.server=true"
	uploadFilesDir="-Dupload.files.dir=/u2/the-publisher-files/upload-files"
	photosDir="-Dphotos.dir=/u2/the-publisher-files/photos"
	tempDir="-Dtemp.dir=/u2/the-publisher-files/temp"
	staticsDir="-Dstatics.dir=/u2/the-publisher-files/statics"
	
elif [ "$profile" == "wladimir" ]; then

	mavenCommand="mvn clean jetty:run-exploded"
	jettyParams="-Djetty.port=8080"
	appParams="-Dpublisher.log.path=/Users/Wlad/publisher-data-files/logs -Drunning.context=wladimir -Dis.jetty.server=true"
	uploadFilesDir="-Dupload.files.dir=/Users/Wlad/publisher-data-files/upload-files"
	photosDir="-Dphotos.dir=/Users/Wlad/publisher-data-files/photos"
	tempDir="-Dtemp.dir=/Users/Wlad/publisher-data-files/temp"
	staticsDir="-Dstatics.dir=/Users/Wlad/publisher-data-files/statics"
	
elif [ "$profile" == "guilherme" ]; then

	mavenCommand="mvn clean jetty:run-exploded"
	jettyParams="-Djetty.port=8080"
	appParams="-Dpublisher.log.path=/Users/Guilherme/publisher-data-files/logs -Drunning.context=guilherme -Dis.jetty.server=true"
	uploadFilesDir="-Dupload.files.dir=/Users/Guilherme/publisher-data-files/upload-files"
	photosDir="-Dphotos.dir=/Users/Guilherme/publisher-data-files/photos"
	tempDir="-Dtemp.dir=/Users/Guilherme/publisher-data-files/temp"
	staticsDir="-Dstatics.dir=/Users/Guilherme/publisher-data-files/statics"
	
else

	exec="false"
	echo "***********************************************************"
	echo "*                                                         *"
	echo "*                         WARNING                         *"
	echo "*                                                         *"
	echo "*                                                         *"
	echo "* The application need a profile to start Jetty Server... *"
	echo "*                                                         *"
	echo "***********************************************************"
	
fi

if [ $exec ]; then
	nohup $mavenCommand $jettyParams $appParams $uploadFilesDir $photosDir $tempDir $staticsDir >> jetty-out.log 2>&1&
fi

#######################
# Setting PID on file #
#######################
#if [ -f "jetty.pid" ]; then
#	rm -f jetty.pid
#fi
#echo $! > jetty.pid