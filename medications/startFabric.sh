#!/bin/bash
#
# Copyright ManTech International All Rights Reserved
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error
set -e

# don't rewrite paths for Windows Git Bash users
export MSYS_NO_PATHCONV=1
starttime=$(date +%s)
CC_SRC_LANGUAGE=${1:-"javascript"}
CC_SRC_LANGUAGE=`echo "$CC_SRC_LANGUAGE" | tr [:upper:] [:lower:]`
CC_RUNTIME_LANGUAGE=node # chaincode runtime language is node.js
CC_SRC_PATH=/opt/gopath/src/github.com/medications/javascript

# clean the keystore
rm -rf ./hfc-key-store

# launch network; create channel and join peer to channel
cd ../medications-network
./start.sh

# Now launch the CLI container in order to install, instantiate chaincode
# and prime the ledger with our 10 cars
docker-compose -f ./docker-compose.yml up -d cli

docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/manufacturing.bigpharma.com/users/Admin@manufacturing.bigpharma.com/msp" cli peer chaincode install -n medications -v 1.0 -p "$CC_SRC_PATH" -l "$CC_RUNTIME_LANGUAGE"
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/manufacturing.bigpharma.com/users/Admin@manufacturing.bigpharma.com/msp" cli peer chaincode instantiate -o orderer.example.com:7050 -C mychannel -n medications -l "$CC_RUNTIME_LANGUAGE" -v 1.0 -c '{"Args":[]}' -P "OR ('ManufacturingMSP.member','Org2MSP.member')"
sleep 10
docker exec -e "CORE_PEER_LOCALMSPID=ManufacturingMSP" -e "CORE_PEER_MSPCONFIGPATH=/opt/gopath/src/github.com/hyperledger/fabric/peer/crypto/peerOrganizations/manufacturing.bigpharma.com/users/Admin@manufacturing.bigpharma.com/msp" cli peer chaincode invoke -o orderer.example.com:7050 -C mychannel -n medications -c '{"function":"initLedger","Args":[]}'

cat <<EOF

Total setup execution time : $(($(date +%s) - starttime)) secs ...

Next, use the Medication applications to interact with the deployed Medication contract.
The Medication applications are available in multiple programming languages.
Follow the instructions for the programming language of your choice:

JavaScript:

  Start by changing into the "javascript" directory:
    cd javascript

  Next, install all required packages:
    npm install

  Then run the following applications to enroll the admin user, and register a new user
  called user1 which will be used by the other applications to interact with the deployed
  Medication contract:
    node enrollAdmin
    node registerUser

  You can run the invoke application as follows. By default, the invoke application will
  create a new car, but you can update the application to submit other transactions:
    node invoke

  You can run the query application as follows. By default, the query application will
  return all cars, but you can update the application to evaluate other transactions:
    node query


EOF
