#!/bin/bash
read -p "Enter db username: " username
read -sp "Enter db password: " password
echo
read -p "Enter DB URL: " url


export MARIADB_USER=$username
export MARIADB_ROOT_PASSWORD=$password
export DB_URL=$url

docker-compose up -d