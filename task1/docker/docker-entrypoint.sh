#!/bin/bash

set -e

CASSANDRA_LISTEN_ADDRESS="$(hostname --ip-address)"
CASSANDRA_BROADCAST_ADDRESS="$(hostname --ip-address)"
CASSANDRA_BROADCAST_RPC_ADDRESS="$(hostname --ip-address)"
CASSANDRA_START_RPC="true"
CASSANDRA_RPC_ADDRESS="0.0.0.0"
CASSANDRA_SEEDS=$CASSANDRA_BROADCAST_ADDRESS

for (( i=1; i<=${#}; i++));
do
  if [ "${!i}" == "-seeds" ]
  then
    seedsIndex=$(( $i + 1 ))
    CASSANDRA_SEEDS=${!seedsIndex}
    break
  fi
done

sed -ri 's/^(# )?('"listen_address"':).*/\2 '"$CASSANDRA_LISTEN_ADDRESS"'/' "/etc/cassandra/cassandra.yaml"
sed -ri 's/^(# )?('"broadcast_address"':).*/\2 '"$CASSANDRA_BROADCAST_ADDRESS"'/' "/etc/cassandra/cassandra.yaml"
sed -ri 's/^(# )?('"start_rpc"':).*/\2 '"$CASSANDRA_START_RPC"'/' "/etc/cassandra/cassandra.yaml"
sed -ri 's/^(# )?('"rpc_address"':).*/\2 '"$CASSANDRA_RPC_ADDRESS"'/' "/etc/cassandra/cassandra.yaml"
sed -ri 's/^(# )?('"broadcast_rpc_address"':).*/\2 '"$CASSANDRA_BROADCAST_RPC_ADDRESS"'/' "/etc/cassandra/cassandra.yaml"
sed -ri 's/(- seeds:).*/\1 "'"$CASSANDRA_SEEDS"'"/' "/etc/cassandra/cassandra.yaml"

set -- cassandra -R -f
exec "$@"
