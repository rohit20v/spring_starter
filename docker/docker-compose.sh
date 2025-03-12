#!/bin/bash
if [ "${1,,}" = "reset" ]; then
  docker-compose -f ./database/docker-compose.yml down
  docker image rm database-backend-service
fi

read -p "Enter db username: " username
read -sp "Enter db password: " password
echo
read -p "Enter DB URL: " url

export MARIADB_USER=$username
export MARIADB_ROOT_PASSWORD=$password
export DB_URL=$url

#declare -a args
#count=0
#
#while IFS= read -r i || [[ -n "$i" ]]; do
#    echo "Reading line: '$i'"
#    args[count]="$i"
#    ((count++))
#done < credentials.txt
#
#MARIADB_USER="${args[0]}"
#MARIADB_ROOT_PASSWORD="${args[1]}"
#DB_URL="${args[2]}"

#MARIADB_USER="${args[0]}" MARIADB_ROOT_PASSWORD="${args[1]}" DB_URL="${args[2]}" \
#export MARIADB_USER=${args[0]}
#export MARIADB_ROOT_PASSWORD=${args[1]}
#export DB_URL=${args[2]}


docker-compose -f ./database/docker-compose.yml up -d

#MARIADB_USER=$MARIADB_USER MARIADB_ROOT_PASSWORD=$MARIADB_ROOT_PASSWORD DB_URL=$DB_URL docker-compose -f ./database/docker-compose.yml up -d
#docker-compose --env-file MARIADB_USER="$MARIADB_USER" MARIADB_ROOT_PASSWORD="$MARIADB_ROOT_PASSWORD" DB_URL="$DB_URL" -f ./database/docker-compose.yml up -d