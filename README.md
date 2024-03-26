# zapicito
Zapicito


1. Run docker login -u fiphiker

2. At the password prompt, enter the personal access token.

docker login -u fiphiker -p {{ACCESS_TOKEN}}

docker build --tag=zapicito:latest .

docker-compose up --build
