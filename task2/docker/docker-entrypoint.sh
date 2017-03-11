#!/bin/bash

echo "StrictHostKeyChecking no" >> /etc/ssh/ssh_config
echo "UserKnownHostsFile=/dev/null" >> /etc/ssh/ssh_config

echo "yes \n" | service ssh restart
echo "yes \n" | ssh localhost
/usr/local/hadoop/bin/hadoop namenode -format
/usr/local/hadoop/sbin/start-dfs.sh

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi
