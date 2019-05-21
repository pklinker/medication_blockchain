#!/bin/bash
#
# SPDX-License-Identifier: Apache-2.0
#
# Exit on first error, print all commands.
set -ev
rm -rf wallet
rm -rf shippingwallet
rm -rf healthwallet

node enrollAnyAdmin.js -c ca.bigpharma.com -w wallet
node enrollAnyAdmin.js -c ca.shipstuff.com -w shippingwallet
node enrollAnyAdmin.js -c ca.health.org -w healthwallet
node registerUser.js -n user1 -c ca.bigpharma.com -m ManufacturingMSP -w wallet
node registerUser.js -n user1 -c ca.shipstuff.com -m ShippingMSP -w shippingwallet
node registerUser.js -n user1 -c ca.health.org -m HospitalMSP -w healthwallet
