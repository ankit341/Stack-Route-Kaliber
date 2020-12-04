#!/bin/bash

set -f

server=$DEPLOY_SERVER
user=$DEPLOY_USER
branch=$DEPLOY_BRANCH
gittoken=$DEPLOY_GITLAB_TOKEN
gituser=$DEPLOY_GITLAB_USER

echo "Deploying project on server ${server} as ${user} from branch ${branch}"

apt-get update && apt-get install -y openssh-client

# command="ls -ltr && \
# rm -rf /home/devuser/kaliber-itc-wave-02 && \
# cd /home/devuser && \
# rm -rf /home/devuser/kaliber-itc-wave-02 && \
# git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/kaliber-itc-wave-02/kaliber-itc-wave-02.git -b ${branch} && \
# echo 'Deploying the Application' && \
# cd /home/devuser/kaliber-itc-wave-02 && \
# ls -ltr && \
# docker-compose -f kaliber-platform-complete/docker-compose.yml up --build -d --remove-orphans && \
# echo 'DONE DEPLOYING'"

# cd /home/devuser/kaliber-itc-wave-02 && \
# less kaliber-platform-complete/docker-compose.yml && \
# docker-compose -f kaliber-platform-complete/docker-compose.yml down && \
# cd /home/devuser && \
# rm -rf /home/devuser/kaliber-itc-wave-02 && \

command="ls -ltr && \
 cd /home/devuser/kaliber-itc-wave-02 && \
 cd /home/devuser && \
 rm -rf /home/devuser/kaliber-itc-wave-02 && \
 git clone https://${gituser}:${gittoken}@gitlab.stackroute.in/kaliber-itc-wave-02/kaliber-itc-wave-02.git -b ${branch} && \
 cd /home/devuser/kaliber-itc-wave-02 && \
 echo 'Deploying the Application' && \
 docker-compose -f kaliber-platform-complete/docker-compose.yml up --build -d --remove-orphans && \
 echo 'DONE DEPLOYING'"


echo "About to run the command: " $command

ssh $user@$server $command

