
if [ "${1,,}" = "stop" ] || [ "${1,,}" = "start" ]; then
  sh docker-db-container.sh "$1" "$2"

elif [ "${1,,}" = "rm" ]; then
  echo "Removing container"
  docker stop "$2"
  docker rm "$2"

elif [ "${1,,}" = "rm -v" ]; then
  echo "Removing container and its associated volumes"
  docker stop "$2"
  docker rm -v "$2" "$3"

elif [ "${1,,}" = "-h" ] || [ "${1,,}" = "-help" ]; then
  printf "Usage:
    1) stop [container-name]
    2) start [container-name]
    3) rm [container-name]
    4) rm -v [container-name] [volume-name]"

else
  echo "Invalid command. Use -h or -help for help."

fi