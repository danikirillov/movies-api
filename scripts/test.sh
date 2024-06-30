#!/bin/bash

cd ..

mvn clean package verify -T 2C

echo "Press smth"
read