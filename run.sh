#!/bin/bash

PWD=$(dirname $0)
source ${PWD}/env.sh
Jar_Path=$1

usage()
{
    echo "Usage: $0 <Jar_Path> {start|stop|restart}  "
    exit 1
}

start()
{
    echo "start $Jar_Path"
    java -Djava.security.egd=file:/dev/.urandom -jar $Jar_Path --spring.profiles.active=sit --jasypt.encryptor.password=${JASYPT_PASSWORD} >$Jar_Path.log &
    echo $?
}

stop()
{
    echo "stop $Jar_Path ........"
    ps -ef | grep java | grep -w $Jar_Path
    num=`ps -ef | grep java | grep -w $Jar_Path | wc -l`
    echo $num
    if [ $num -ne 0 ];then
        pid=`ps -ef | grep java | grep -w $Jar_Path | awk '{print $2}'`
        kill -9 $pid
        echo "$Jar_Path stoped"
    else
        echo "$Jar_Path not run"
    fi
}
case "$2" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    *)
        usage
esac
