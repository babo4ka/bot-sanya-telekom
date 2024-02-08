#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
  target/BotSanyaGreatAgain-1.0.jar \
  root@89.111.170.24:/root

echo 'Restart server...'

ssh -i ~/.ssh/id_rsa root@89.111.170.24 << EOF

pgrep java | xargs kill -9
nohup java -jar BotSanyaGreatAgain-1.0.jar > log.txt &

EOF

echo 'Bye'