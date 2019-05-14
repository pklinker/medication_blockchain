/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { Contract } = require('fabric-contract-api');

class Medication extends Contract {

  async initLedger(ctx) {
    console.info('============= START : Initialize Ledger ===========');
    const medications =
      [
        {
          lotNo: 1,
          applNo: '000004',
          productNo: '004',
          form: 'SOLUTION/DROPS;OPHTHALMIC',
          strength: '1%',
          referenceDrug: '0',
          drugName: 'PAREDRINE',
          activeIngredient: 'HYDROXYAMPHETAMINE HYDROBROMIDE',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:2,
          applNo: '000159',
          productNo: '001',
          form: 'TABLET;ORAL',
          strength: '500MG',
          referenceDrug: '0',
          drugName: 'SULFAPYRIDINE',
          activeIngredient: 'SULFAPYRIDINE',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:3,
          applNo: '000552',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: '20,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:4,
          applNo: '000552',
          productNo: '002',
          form: 'INJECTABLE;INJECTION',
          strength: '40,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:5,
          applNo: '000552',
          productNo: '003',
          form: 'INJECTABLE;INJECTION',
          strength: '5,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:6,
          applNo: '000552',
          productNo: '004',
          form: 'INJECTABLE;INJECTION',
          strength: '1,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:7,
          applNo: '000552',
          productNo: '007',
          form: 'INJECTABLE;INJECTION',
          strength: '100 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN LOCK FLUSH',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:8,
          applNo: '000552',
          productNo: '008',
          form: 'INJECTABLE;INJECTION',
          strength: '1,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'HEPARIN SODIUM',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:9,
          applNo: '000552',
          productNo: '011',
          form: 'INJECTABLE;INJECTION',
          strength: '1,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM PRESERVATIVE FREE',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:10,
          applNo: '000552',
          productNo: '012',
          form: 'INJECTABLE;INJECTION',
          strength: '5,000 UNITS/ML',
          referenceDrug: '0',
          drugName: 'LIQUAEMIN SODIUM PRESERVATIVE FREE',
          activeIngredient: 'HEPARIN SODIUM',
          referenceStandard: '0',
         quantity:1000, 
         recipient:'', 
         owner: 'BigPharma'
        },
        {
          lotNo:11,
          applNo: '000734',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: 'EQ 1MG BASE/ML',
          referenceDrug: '0',
          drugName: 'HISTAMINE PHOSPHATE',
          activeIngredient: 'HISTAMINE PHOSPHATE',
          referenceStandard: '0',
         quantity:1000, 
         recipient:'', 
         owner: 'BigPharma'
        },
        {
          lotNo:12,
          applNo: '000734',
          productNo: '002',
          form: 'INJECTABLE;INJECTION',
          strength: 'EQ 0.2MG BASE/ML',
          referenceDrug: '0',
          drugName: 'HISTAMINE PHOSPHATE',
          activeIngredient: 'HISTAMINE PHOSPHATE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:13,
          applNo: '000734',
          productNo: '003',
          form: 'INJECTABLE;INJECTION',
          strength: 'EQ 0.1MG BASE/ML',
          referenceDrug: '0',
          drugName: 'HISTAMINE PHOSPHATE',
          activeIngredient: 'HISTAMINE PHOSPHATE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:14,
          applNo: '000793',
          productNo: '002',
          form: 'TABLET;ORAL',
          strength: '15MG',
          referenceDrug: '0',
          drugName: 'BUTISOL SODIUM',
          activeIngredient: 'BUTABARBITAL SODIUM',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:15,
          applNo: '001104',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: '5MG/ML',
          referenceDrug: '0',
          drugName: 'DOCA',
          activeIngredient: 'DESOXYCORTICOSTERONE ACETATE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:16,
          applNo: '001504',
          productNo: '001',
          form: 'UNKNOWN',
          strength: 'UNKNOWN',
          referenceDrug: '0',
          drugName: 'VERARD',
          activeIngredient: 'VERARD',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:17,
          applNo: '001546',
          productNo: '001',
          form: 'TABLET;ORAL',
          strength: '125MG',
          referenceDrug: '0',
          drugName: 'GUANIDINE HYDROCHLORIDE',
          activeIngredient: 'GUANIDINE HYDROCHLORIDE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:18,
          applNo: '002139',
          productNo: '003',
          form: 'TABLET;ORAL',
          strength: '5MG',
          referenceDrug: '0',
          drugName: 'MENADIONE',
          activeIngredient: 'MENADIONE',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        },
        {
          lotNo:19,
          applNo: '002245',
          productNo: '002',
          form: 'TABLET;ORAL',
          strength: '65MG',
          referenceDrug: '0',
          drugName: 'PROLOID',
          activeIngredient: 'THYROGLOBULIN',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'',
          owner: 'BigPharma'
        },
        {
          lotNo:20,
          applNo: '002282',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: '100MG/ML',
          referenceDrug: '0',
          drugName: 'INULIN AND SODIUM CHLORIDE',
          activeIngredient: 'INULIN',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:21,
          applNo: '002386',
          productNo: '002',
          form: 'TABLET;ORAL',
          strength: '100MG',
          referenceDrug: '0',
          drugName: 'AMINOPHYLLIN',
          activeIngredient: 'AMINOPHYLLINE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:22,
          applNo: '002386',
          productNo: '003',
          form: 'TABLET;ORAL',
          strength: '200MG',
          referenceDrug: '0',
          drugName: 'AMINOPHYLLIN',
          activeIngredient: 'AMINOPHYLLINE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:23,
          applNo: '002918',
          productNo: '001',
          form: 'POWDER;TOPICAL',
          strength: '33.32%',
          referenceDrug: '0',
          drugName: 'BENSULFOID',
          activeIngredient: 'SULFUR',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:24,
          applNo: '003158',
          productNo: '001',
          form: 'TABLET;ORAL',
          strength: '10MG',
          referenceDrug: '0',
          drugName: 'ORETON METHYL',
          activeIngredient: 'METHYLTESTOSTERONE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:25,
          applNo: '003158',
          productNo: '002',
          form: 'TABLET;ORAL',
          strength: '25MG',
          referenceDrug: '0',
          drugName: 'ORETON METHYL',
          activeIngredient: 'METHYLTESTOSTERONE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },

        {
          lotNo:26,
          applNo: '003240',
          productNo: '004',
          form: 'TABLET;BUCCAL, SUBLINGUAL',
          strength: '5MG',
          referenceDrug: '0',
          drugName: 'METANDREN',
          activeIngredient: 'METHYLTESTOSTERONE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:27,
          applNo: '003240',
          productNo: '005',
          form: 'TABLET;BUCCAL, SUBLINGUAL',
          strength: '10MG',
          referenceDrug: '0',
          drugName: 'METANDREN',
          activeIngredient: 'METHYLTESTOSTERONE',
          referenceStandard: '0',
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:28,
          applNo: '003402',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: '5PRESSOR UNITS/ML **Federal Register determination that product was not discontinued or withdrawn for safety or efficacy reasons**',
          referenceDrug: '1',
          drugName: 'PITRESSIN TANNATE',
          activeIngredient: 'VASOPRESSIN TANNATE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:29,
          applNo: '003444',
          productNo: '001',
          form: 'CAPSULE;ORAL',
          strength: '50,000 IU',
          referenceDrug: '1',
          drugName: 'DRISDOL',
          activeIngredient: 'ERGOCALCIFEROL',
          referenceStandard: '1', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:30,
          applNo: '003718',
          productNo: '004',
          form: 'INJECTABLE;INJECTION',
          strength: '5MG/ML',
          referenceDrug: '0',
          drugName: 'SYNKAYVITE',
          activeIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:31,
          applNo: '003718',
          productNo: '006',
          form: 'INJECTABLE;INJECTION',
          strength: '10MG/ML',
          referenceDrug: '0',
          drugName: 'SYNKAYVITE',
          activeIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        
        {
          lotNo:32,
          applNo: '003718',
          productNo: '010',
          form: 'TABLET;ORAL',
          strength: '5MG',
          referenceDrug: '0',
          drugName: 'SYNKAYVITE',
          activeIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:33,
          applNo: '003977',
          productNo: '001',
          form: 'INJECTABLE;INJECTION',
          strength: '1MG/ML',
          referenceDrug: '0',
          drugName: 'THEELIN',
          activeIngredient: 'ESTRONE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:34,
          applNo: '003977',
          productNo: '002',
          form: 'INJECTABLE;INJECTION',
          strength: '2MG/ML',
          referenceDrug: '0',
          drugName: 'THEELIN',
          activeIngredient: 'ESTRONE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          lotNo:35,
          applNo: '003977',
          productNo: '003',
          form: 'INJECTABLE;INJECTION',
          strength: '5MG/ML',
          referenceDrug: '0',
          drugName: 'THEELIN',
          activeIngredient: 'ESTRONE',
          referenceStandard: '0', 
          quantity:1000, 
          recipient:'', 
          owner: 'BigPharma'
        },
        {
          applNo: '004039',
          productNo: '002',
          form: 'TABLET, DELAYED RELEASE;ORAL',
          strength: '0.1MG',
          referenceDrug: '0',
          drugName: 'DIETHYLSTILBESTROL',
          activeIngredient: 'DIETHYLSTILBESTROL',
          referenceStandard: '0'
          , quantity:1000, recipient:'', owner: 'BigPharma'
        }
      ];

    for (let i = 0; i < medications.length; i++) {
      medications[i].docType = 'medication';
      await ctx.stub.putState('MED-'+i, Buffer.from(JSON.stringify(medications[i])));
      console.info('Added <--> ', medications[i]);
    }
    console.info('============= END : Initialize Ledger ===========');
  }

  async queryMedication(ctx, medNumber) {
    const medAsBytes = await ctx.stub.getState(medNumber); // get the medication from chaincode state
    if (!medAsBytes || medAsBytes.length === 0) {
      throw new Error(`${medNumber} does not exist`);
    }
    console.log(medAsBytes.toString());
    return medAsBytes.toString();
  }

  async createMedication(ctx, medNumber, lotNo, applNo, productNo, form, strength, 
    referenceDrug, drugName, activeIngredient, referenceStandard, quantity, 
    recipient, owner) {
    console.info('============= START : Create medication ===========');

    const medication = {
      docType: 'medication',
      lotNo,
      applNo,
      productNo,
      form,
      strength,
      referenceDrug,
      drugName,
      activeIngredient,
      referenceStandard,
      quantity,
      recipient,
      owner
    };

    await ctx.stub.putState(medNumber, Buffer.from(JSON.stringify(medication)));
    console.info('============= END : Create medication ===========');
  }

  async queryAllMedications(ctx) {
    const startKey = 'MED-0';
    const endKey = 'MED-999999999999999999';

   // const iterator = await ctx.stub.getStateByRange(startKey, endKey);
   const iterator = await ctx.stub.getStateByRange(startKey, endKey);
    const allResults = [];
    while (true) {
      const res = await iterator.next();

      if (res.value && res.value.value.toString()) {
        console.log(res.value.value.toString('utf8'));

        const Key = res.value.key;
        let Record;
        try {
          Record = JSON.parse(res.value.value.toString('utf8'));
        } catch (err) {
          console.log(err);
          Record = res.value.value.toString('utf8');
        }
        allResults.push({ Key, Record });
      }
      if (res.done) {
        console.log('end of data');
        await iterator.close();
        console.info(allResults);
        return JSON.stringify(allResults);
      }
    }
  }

  // Create a new medication and prescribe to the patient. Reduce quantity of the existing medication.
  async dispenseMedication(ctx, medNumber, patient, quantity) {
  
    console.info('============= START : dispenseMedication ===========');

    const medAsBytes = await ctx.stub.getState(medNumber); // get the medication from chaincode state
    if (!medAsBytes || medAsBytes.length === 0) {
      throw new Error(`${medNumber} does not exist`);
    }
    const medication = JSON.parse(medAsBytes.toString());
    medication.quantity = medication.quantity - quantity;

    await ctx.stub.putState(medNumber, Buffer.from(JSON.stringify(medication)));
    console.info('============= : updated medication inventory ===========');

    // create new medication
    const med_number = 'MED-' + Date.now();
    medication.medNumber = med_number;
    medication.recipient = patient;
    medication.quantity = quantity;
    await ctx.stub.putState(med_number, Buffer.from(JSON.stringify(medication)));
    console.info('============= END : dispenseMedication ===========');
  }

    // Transfer the entire medication block to a new owner.
    async transferMedicationLot(ctx, medNumber, owner) {
  
      console.info('============= START : transferMedication ===========');
  
      const medAsBytes = await ctx.stub.getState(medNumber); // get the medication from chaincode state
      if (!medAsBytes || medAsBytes.length === 0) {
        throw new Error(`${medNumber} does not exist`);
      }
      const medication = JSON.parse(medAsBytes.toString());
      medication.owner = owner
  
      await ctx.stub.putState(medNumber, Buffer.from(JSON.stringify(medication)));
  
      console.info('============= END : transferMedication ===========');
    }
  
}

module.exports = Medication;
