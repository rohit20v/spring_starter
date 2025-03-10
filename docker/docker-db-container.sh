if [ "$1" = "stop" ]; then
  echo "Stopping " "$2"
  docker stop "$2"
else
  echo "Starting" "$2"
  docker start "$2"
fi


