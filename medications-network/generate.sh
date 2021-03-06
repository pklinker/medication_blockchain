#!/bin/sh
#
# SPDX-License-Identifier: Apache-2.0
#
export PATH=$GOPATH/src/github.com/hyperledger/fabric/build/bin:${PWD}/../bin:${PWD}:$PATH
export FABRIC_CFG_PATH=${PWD}
CHANNEL_NAME=distribution

# remove previous crypto material and config transactions
rm -fr config/*
rm -fr crypto-config/*

# generate crypto material
cryptogen generate --config=./crypto-config.yaml
if [ "$?" -ne 0 ]; then
  echo "Failed to generate crypto material..."
  exit 1
fi

# generate genesis block for orderer, currently using solo concensus type
configtxgen -profile TwoOrgsOrdererGenesis -outputBlock ./config/genesis.block
if [ "$?" -ne 0 ]; then
  echo "Failed to generate two orgs orderer genesis block..."
  exit 1
fi

# generate channel configuration transaction channel.tx
configtxgen -profile TwoOrgsChannel -outputCreateChannelTx ./config/channel.tx -channelID $CHANNEL_NAME
if [ "$?" -ne 0 ]; then
  echo "Failed to generate two orgs channel configuration transaction..."
  exit 1
fi

# generate anchor peer transaction for manufacturing organization
configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./config/ManufacturingMSPanchors.tx -channelID $CHANNEL_NAME -asOrg ManufacturingMSP
if [ "$?" -ne 0 ]; then
  echo "Failed to generate anchor peer update for ManufacturingMSP..."
  exit 1
fi

# generate anchor peer transaction for shipping organization
configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./config/ShippingMSPanchors.tx -channelID $CHANNEL_NAME -asOrg ShippingMSP
if [ "$?" -ne 0 ]; then
  echo "Failed to generate anchor peer update for ShippingMSP..."
  exit 1
fi

# generate anchor peer transaction for pharmacy 1 organization
configtxgen -profile TwoOrgsChannel -outputAnchorPeersUpdate ./config/HospitalMSPanchors.tx -channelID $CHANNEL_NAME -asOrg HospitalMSP
if [ "$?" -ne 0 ]; then
  echo "Failed to generate anchor peer update for HospitalMSP..."
  exit 1
fi
