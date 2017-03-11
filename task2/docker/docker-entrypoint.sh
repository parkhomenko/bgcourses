#!/bin/bash

/usr/local/hadoop/bin/hadoop namenode -format
/usr/local/hadoop/sbin/start-dfs.sh

if [[ $1 == "-d" ]]; then
  while true; do sleep 1000; done
fi

if [[ $1 == "-bash" ]]; then
  /bin/bash
fi
