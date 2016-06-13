#!/bin/bash
current_dir=$PWD
cd ./eureka-server
./gradlew bootRun &
cd $current_dir

sleep 10s

cd ./usersrv
./gradlew bootRun &
cd $current_dir

cd ./user-proxy
./gradlew bootRun &
cd $current_dir

cd ./productsrv
./gradlew bootRun &
cd $current_dir

cd ./product-proxy
./gradlew bootRun &
cd $current_dir

cd ./usersrv
./gradlew bootRun &
cd $current_dir

cd ./categorysrv
./gradlew bootRun &
cd $current_dir


wait
echo "all closed"
