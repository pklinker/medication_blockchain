#!/bin/bash
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error, print all commands.
set -ev

# don't rewrite paths for Windows Git Bash users
export MSYS_NO_PATHCONV=1

docker-compose -f docker-compose.yml down

docker-compose -f docker-compose.yml up -d ca.bigpharma.com ca.shipstuff.com orderer.example.com peer0.manufacturing.bigpharma.com peer0.shipping.shipstuff.com couchdb

# wait for Hyperledger Fabric to start
# incase of errors when running later commands, issue export FABRIC_START_TIMEOUT=<larger number>
export FABRIC_START_TIMEOUT=10
#echo ${FABRIC_START_TIMEOUT}
sleep ${FABRIC_START_TIMEOUT}

# Create the channel for
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer0.manufacturing.bigpharma.com peer channel create -o orderer.example.com:7050 -c mychannel -f /etc/hyperledger/configtx/channel.tx
# Join peer0.manufacturing.bigpharma.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer0.manufacturing.bigpharma.com peer channel join -b mychannel.block

# Create the channel for
#docker exec -e "CORE_PEER_LOCALMSPID=ShippingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@shipping.shipstuff.com/msp" peer0.shipping.shipstuff.com peer channel create -o orderer.example.com:7050 -c mychannel -f /etc/hyperledger/configtx/channel.tx
# fetch a channel
docker exec -e "CORE_PEER_LOCALMSPID=ShippingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@shipping.shipstuff.com/msp" peer0.shipping.shipstuff.com peer channel fetch newest mychannel.block -o orderer.example.com:7050 -c mychannel 
# Join peer0.manufacturing.bigpharma.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ShippingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@shipping.shipstuff.com/msp" peer0.shipping.shipstuff.com peer channel join -b mychannel.block
