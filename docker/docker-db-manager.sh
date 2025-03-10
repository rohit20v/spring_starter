if [ "${1,,}" = "stop" ] || [ "${1,,}" = "start" ]; then
  sh docker-db-container.sh "$1" "$2"

elif [ "${1,,}" = "rm" ]; then
  echo "Removing container"
  docker stop "$2"
  docker rm "$2"

elif [ "${1,,}" = "rm -v " ]; then
  echo "Removing container and its associated volumes"
  docker stop "$2"
  docker rm -v "$2" "$3"

fi