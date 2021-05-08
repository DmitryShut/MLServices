# shellcheck disable=SC2092
# shellcheck disable=SC2006
`#!/bin/sh
SERVICE_NAME=mlservice
PATH_TO_JAR=/root/mlservices/demo-SNAPSHOT-boot.jar 
PID_PATH_NAME=/root/mlservices/mlservice-pid
case $1 in 
start)
  echo "Starting $SERVICE_NAME ..."
  if [ ! -f $PID_PATH_NAME ]; then 	
	  export GOOGLE_APPLICATION_CREDENTIALS=/root/mlservices/cred.json
	  rm mlservice.out
    nohup java -jar $PATH_TO_JAR >> mlservice.out 2>&1&
    echo $! > $PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else 
    echo "$SERVICE_NAME is already running ..."
  fi
;;
stop)
  if [ -f $PID_PATH_NAME ]; then
    PID=$(cat $PID_PATH_NAME);
    echo "$SERVICE_NAME stoping ..."
    kill $PID;
    echo "$SERVICE_NAME stopped ..."
    rm $PID_PATH_NAME
  else          
    echo "$SERVICE_NAME is not running ..."
  fi    
;;    
restart)
  if [ -f $PID_PATH_NAME ]; then 
    PID=$(cat $PID_PATH_NAME);
    echo "$SERVICE_NAME stopping ...";
    kill $PID;
    echo "$SERVICE_NAME stopped ...";
    rm $PID_PATH_NAME
	  rm mlservice.out
    echo "$SERVICE_NAME starting ..."
	  export GOOGLE_APPLICATION_CREDENTIALS=/root/mlservices/cred.json
    nohup java -jar $PATH_TO_JAR >> mlservice.out 2>&1&
    echo $! > $PID_PATH_NAME
    echo "$SERVICE_NAME started ..."
  else           
    echo "$SERVICE_NAME is not running ..."
  fi
;;
 esac`