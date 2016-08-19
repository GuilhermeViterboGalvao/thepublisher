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
mavenCommand="mvn clean jetty:run-exploded"
appParams="-Djetty.port=8080 -Dpublisher-log-path=/Users/Guilherme/publisher-data-files/logs -Drunning-context=guilherme"
nohup $mavenCommand $appParams >> jetty-out.log 2>&1&

#######################
# Setting PID on file #
#######################
#if [ -f "jetty.pid" ]; then
#	rm -f jetty.pid
#fi
#echo $! > jetty.pid