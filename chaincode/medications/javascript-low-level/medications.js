/*
# Copyright IBM Corp. All Rights Reserved.
#
# SPDX-License-Identifier: Apache-2.0
*/

'use strict';
const shim = require('fabric-shim');
const util = require('util');

let Chaincode = class {

  // The Init method is called when the Smart Contract 'medications' is instantiated by the blockchain network
  // Best practice is to have any Ledger initialization in separate function -- see initLedger()
  async Init(stub) {
    console.info('=========== Instantiated medications chaincode ===========');
    return shim.success();
  }

  // The Invoke method is called as a result of an application request to run the Smart Contract
  // 'medications'. The calling application program has also specified the particular smart contract
  // function to be called, with arguments
  async Invoke(stub) {
    let ret = stub.getFunctionAndParameters();
    console.info(ret);

    let method = this[ret.fcn];
    if (!method) {
      console.error('no function of name:' + ret.fcn + ' found');
      throw new Error('Received unknown function ' + ret.fcn + ' invocation');
    }
    try {
      let payload = await method(stub, ret.params);
      return shim.success(payload);
    } catch (err) {
      console.log(err);
      return shim.error(err);
    }
  }

  async queryCar(stub, args) {
    if (args.length != 1) {
      throw new Error('Incorrect number of arguments. Expecting CarNumber ex: CAR01');
    }
    let carNumber = args[0];

    let carAsBytes = await stub.getState(carNumber); //get the car from chaincode state
    if (!carAsBytes || carAsBytes.toString().length <= 0) {
      throw new Error(carNumber + ' does not exist: ');
    }
    console.log(carAsBytes.toString());
    return carAsBytes;
  }

  async initLedger(stub, args) {
    console.info('============= START : Initialize Ledger ===========');
    let medications = [];
    medications.push({
          ApplNo: '000004',
          ProductNo: '004',
          Form: 'SOLUTION/DROPS;OPHTHALMIC',
          Strength: '1%',
          ReferenceDrug: '0',
          DrugName: 'PAREDRINE',
          ActiveIngredient: 'HYDROXYAMPHETAMINE HYDROBROMIDE',
          ReferenceStandard: '0',
          Owner: 'INOVA'
    });

    for (let i = 0; i < cars.length; i++) {
      medicationss[i].docType = 'medication';
      await stub.putState('MED' + i, Buffer.from(JSON.stringify(medications[i])));
      console.info('Added <--> ', medications[i]);
    }
    console.info('============= END : Initialize Ledger ===========');
  }

  async createMedication(stub, args) {
    console.info('============= START : Create Medication ===========');
    if (args.length != 10) {
      throw new Error('Incorrect number of arguments. Expecting 10');
    }

      var medication = {
      docType: 'medication',
	  ApplNo: args[1],
      ProductNo: args[2],
      Form: args[3],
      Strength: args[4],
      ReferenceDrug: args[5],
      DrugName: args[6],
      ActiveIngredient: args[7],
      ReferenceStandard: args[8],
      Owner: args[9]
    };

    await stub.putState(args[0], Buffer.from(JSON.stringify(car)));
    console.info('============= END : Create Medication ===========');
  }

  async queryAllMedications(stub, args) {

    let startKey = 'MED0';
    let endKey = 'MED999';

    let iterator = await stub.getStateByRange(startKey, endKey);

    let allResults = [];
    while (true) {
      let res = await iterator.next();

      if (res.value && res.value.value.toString()) {
        let jsonRes = {};
        console.log(res.value.value.toString('utf8'));

        jsonRes.Key = res.value.key;
        try {
          jsonRes.Record = JSON.parse(res.value.value.toString('utf8'));
        } catch (err) {
          console.log(err);
          jsonRes.Record = res.value.value.toString('utf8');
        }
        allResults.push(jsonRes);
      }
      if (res.done) {
        console.log('end of data');
        await iterator.close();
        console.info(allResults);
        return Buffer.from(JSON.stringify(allResults));
      }
    }
  }

  async changeMedicationOwner(stub, args) {
    console.info('============= START : changeMedicationOwner ===========');
    if (args.length != 2) {
      throw new Error('Incorrect number of arguments. Expecting 2');
    }

    let medAsBytes = await stub.getState(args[0]);
    let med = JSON.parse(medAsBytes);
    med.owner = args[1];

    await stub.putState(args[0], Buffer.from(JSON.stringify(med)));
    console.info('============= END : changeMedicationOwner ===========');
  }
};

shim.start(new Chaincode());
