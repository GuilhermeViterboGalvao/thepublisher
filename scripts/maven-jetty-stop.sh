#!/bin/bash
#

###########################################################
# Checking if user is runnig script as ROOT os using SUDO #
###########################################################
#if [ "$(id -u)" != "0" ]; then
#	echo "This script must be run as root" 1>&2
#	exit 1
#fi

#kill -9 `cat jetty.pid`

#########################
# Removing old log file #
#########################
#if [ -f "jetty.pid" ]; then
#	rm -f jetty.pid
#fi

mvn jetty:stop