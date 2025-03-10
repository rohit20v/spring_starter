read -p "Enter db username" username
username=${username}

read -p "Enter db password" password
password=${password}

read -p "Enter DB URL" url
url=${url}


docker-compose up $url $username $password -d