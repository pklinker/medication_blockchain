#!/bin/bash
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error, print all commands.
set -ev

# don't rewrite paths for Windows Git Bash users
export MSYS_NO_PATHCONV=1

docker-compose -f docker-compose.yml down

docker-compose -f docker-compose.yml up -d ca.bigpharma.com ca.shipstuff.com ca.health.org orderer.bigpharma.com peer0.manufacturing.bigpharma.com peer1.manufacturing.bigpharma.com peer0.shipping.shipstuff.com peer0.hospital.health.org peer1.hospital.health.org couchdb

# wait for Hyperledger Fabric to start
# incase of errors when running later commands, issue export FABRIC_START_TIMEOUT=<larger number>
export FABRIC_START_TIMEOUT=20
#echo ${FABRIC_START_TIMEOUT}
sleep ${FABRIC_START_TIMEOUT}

# Create the channel for the manufacturing organization
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer0.manufacturing.bigpharma.com peer channel create -o orderer.bigpharma.com:7050 -c distribution -f /etc/hyperledger/configtx/channel.tx
# Join peer0.manufacturing.bigpharma.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer0.manufacturing.bigpharma.com peer channel join -b distribution.block

# fetch the channel for the manufacturing peer1 organization
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer1.manufacturing.bigpharma.com peer channel fetch newest distribution.block -o orderer.bigpharma.com:7050 -c distribution 
# Join peer1.manufacturing.bigpharma.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@manufacturing.bigpharma.com/msp" peer1.manufacturing.bigpharma.com peer channel join -b distribution.block

# fetch the channel for the shipping organization
docker exec -e "CORE_PEER_LOCALMSPID=ShippingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@shipping.shipstuff.com/msp" peer0.shipping.shipstuff.com peer channel fetch newest distribution.block -o orderer.bigpharma.com:7050 -c distribution 
# Join peer0.shipping.shipstuff.com to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=ShippingMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@shipping.shipstuff.com/msp" peer0.shipping.shipstuff.com peer channel join -b distribution.block

# fetch the channel for the pharmacy peer0 organization
docker exec -e "CORE_PEER_LOCALMSPID=HospitalMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@hospital.health.org/msp" peer0.hospital.health.org peer channel fetch newest distribution.block -o orderer.bigpharma.com:7050 -c distribution 
# Join peer0.hospital.health.org to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=HospitalMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@hospital.health.org/msp" peer0.hospital.health.org peer channel join -b distribution.block

# fetch the channel for the pharmacy peer1 organization
docker exec -e "CORE_PEER_LOCALMSPID=HospitalMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@hospital.health.org/msp" peer1.hospital.health.org peer channel fetch newest distribution.block -o orderer.bigpharma.com:7050 -c distribution 
# Join peer1.hospital.health.org to the channel.
docker exec -e "CORE_PEER_LOCALMSPID=HospitalMSP" -e "CORE_PEER_MSPCONFIGPATH=/etc/hyperledger/msp/users/Admin@hospital.health.org/msp" peer1.hospital.health.org peer channel join -b distribution.block
