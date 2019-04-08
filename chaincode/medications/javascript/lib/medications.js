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
          ApplNo: '000004',
          ProductNo: '004',
          Form: 'SOLUTION/DROPS;OPHTHALMIC',
          Strength: '1%',
          ReferenceDrug: '0',
          DrugName: 'PAREDRINE',
          ActiveIngredient: 'HYDROXYAMPHETAMINE HYDROBROMIDE',
          ReferenceStandard: '0',
          Owner: 'INOVA'
        },
        {
          ApplNo: '000159',
          ProductNo: '001',
          Form: 'TABLET;ORAL',
          Strength: '500MG',
          ReferenceDrug: '0',
          DrugName: 'SULFAPYRIDINE',
          ActiveIngredient: 'SULFAPYRIDINE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: '20,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '002',
          Form: 'INJECTABLE;INJECTION',
          Strength: '40,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '003',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '004',
          Form: 'INJECTABLE;INJECTION',
          Strength: '1,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '005',
          Form: 'INJECTABLE;INJECTION',
          Strength: '10,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '007',
          Form: 'INJECTABLE;INJECTION',
          Strength: '100 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN LOCK FLUSH',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '008',
          Form: 'INJECTABLE;INJECTION',
          Strength: '1,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'HEPARIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '009',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'HEPARIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '010',
          Form: 'INJECTABLE;INJECTION',
          Strength: '10,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'HEPARIN SODIUM',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '011',
          Form: 'INJECTABLE;INJECTION',
          Strength: '1,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM PRESERVATIVE FREE',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '012',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM PRESERVATIVE FREE',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000552',
          ProductNo: '013',
          Form: 'INJECTABLE;INJECTION',
          Strength: '10,000 UNITS/ML',
          ReferenceDrug: '0',
          DrugName: 'LIQUAEMIN SODIUM PRESERVATIVE FREE',
          ActiveIngredient: 'HEPARIN SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000734',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: 'EQ 1MG BASE/ML',
          ReferenceDrug: '0',
          DrugName: 'HISTAMINE PHOSPHATE',
          ActiveIngredient: 'HISTAMINE PHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000734',
          ProductNo: '002',
          Form: 'INJECTABLE;INJECTION',
          Strength: 'EQ 0.2MG BASE/ML',
          ReferenceDrug: '0',
          DrugName: 'HISTAMINE PHOSPHATE',
          ActiveIngredient: 'HISTAMINE PHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000734',
          ProductNo: '003',
          Form: 'INJECTABLE;INJECTION',
          Strength: 'EQ 0.1MG BASE/ML',
          ReferenceDrug: '0',
          DrugName: 'HISTAMINE PHOSPHATE',
          ActiveIngredient: 'HISTAMINE PHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000793',
          ProductNo: '002',
          Form: 'TABLET;ORAL',
          Strength: '15MG',
          ReferenceDrug: '0',
          DrugName: 'BUTISOL SODIUM',
          ActiveIngredient: 'BUTABARBITAL SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000793',
          ProductNo: '003',
          Form: 'TABLET;ORAL',
          Strength: '50MG',
          ReferenceDrug: '0',
          DrugName: 'BUTISOL SODIUM',
          ActiveIngredient: 'BUTABARBITAL SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000793',
          ProductNo: '004',
          Form: 'TABLET;ORAL',
          Strength: '30MG',
          ReferenceDrug: '1',
          DrugName: 'BUTISOL SODIUM',
          ActiveIngredient: 'BUTABARBITAL SODIUM',
          ReferenceStandard: '1'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '000793',
          ProductNo: '005',
          Form: 'TABLET;ORAL',
          Strength: '100MG',
          ReferenceDrug: '0',
          DrugName: 'BUTISOL SODIUM',
          ActiveIngredient: 'BUTABARBITAL SODIUM',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '001104',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5MG/ML',
          ReferenceDrug: '0',
          DrugName: 'DOCA',
          ActiveIngredient: 'DESOXYCORTICOSTERONE ACETATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '001504',
          ProductNo: '001',
          Form: 'UNKNOWN',
          Strength: 'UNKNOWN',
          ReferenceDrug: '0',
          DrugName: 'VERARD',
          ActiveIngredient: 'VERARD',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '001546',
          ProductNo: '001',
          Form: 'TABLET;ORAL',
          Strength: '125MG',
          ReferenceDrug: '0',
          DrugName: 'GUANIDINE HYDROCHLORIDE',
          ActiveIngredient: 'GUANIDINE HYDROCHLORIDE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002139',
          ProductNo: '003',
          Form: 'TABLET;ORAL',
          Strength: '5MG',
          ReferenceDrug: '0',
          DrugName: 'MENADIONE',
          ActiveIngredient: 'MENADIONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '002',
          Form: 'TABLET;ORAL',
          Strength: '65MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '004',
          Form: 'TABLET;ORAL',
          Strength: '325MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '005',
          Form: 'TABLET;ORAL',
          Strength: '32MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '007',
          Form: 'TABLET;ORAL',
          Strength: '200MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '008',
          Form: 'TABLET;ORAL',
          Strength: '100MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '009',
          Form: 'TABLET;ORAL',
          Strength: '16MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002245',
          ProductNo: '010',
          Form: 'TABLET;ORAL',
          Strength: '130MG',
          ReferenceDrug: '0',
          DrugName: 'PROLOID',
          ActiveIngredient: 'THYROGLOBULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002282',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: '100MG/ML',
          ReferenceDrug: '0',
          DrugName: 'INULIN AND SODIUM CHLORIDE',
          ActiveIngredient: 'INULIN',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002386',
          ProductNo: '002',
          Form: 'TABLET;ORAL',
          Strength: '100MG',
          ReferenceDrug: '0',
          DrugName: 'AMINOPHYLLIN',
          ActiveIngredient: 'AMINOPHYLLINE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002386',
          ProductNo: '003',
          Form: 'TABLET;ORAL',
          Strength: '200MG',
          ReferenceDrug: '0',
          DrugName: 'AMINOPHYLLIN',
          ActiveIngredient: 'AMINOPHYLLINE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '002918',
          ProductNo: '001',
          Form: 'POWDER;TOPICAL',
          Strength: '33.32%',
          ReferenceDrug: '0',
          DrugName: 'BENSULFOID',
          ActiveIngredient: 'SULFUR',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003158',
          ProductNo: '001',
          Form: 'TABLET;ORAL',
          Strength: '10MG',
          ReferenceDrug: '0',
          DrugName: 'ORETON METHYL',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003158',
          ProductNo: '002',
          Form: 'TABLET;ORAL',
          Strength: '25MG',
          ReferenceDrug: '0',
          DrugName: 'ORETON METHYL',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003240',
          ProductNo: '001',
          Form: 'TABLET;BUCCAL, SUBLINGUAL',
          Strength: '10MG',
          ReferenceDrug: '0',
          DrugName: 'METANDREN',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003240',
          ProductNo: '003',
          Form: 'TABLET;BUCCAL, SUBLINGUAL',
          Strength: '25MG',
          ReferenceDrug: '0',
          DrugName: 'METANDREN',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003240',
          ProductNo: '004',
          Form: 'TABLET;BUCCAL, SUBLINGUAL',
          Strength: '5MG',
          ReferenceDrug: '0',
          DrugName: 'METANDREN',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003240',
          ProductNo: '005',
          Form: 'TABLET;BUCCAL, SUBLINGUAL',
          Strength: '10MG',
          ReferenceDrug: '0',
          DrugName: 'METANDREN',
          ActiveIngredient: 'METHYLTESTOSTERONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003402',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5PRESSOR UNITS/ML **Federal Register determination that product was not discontinued or withdrawn for safety or efficacy reasons**',
          ReferenceDrug: '1',
          DrugName: 'PITRESSIN TANNATE',
          ActiveIngredient: 'VASOPRESSIN TANNATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003444',
          ProductNo: '001',
          Form: 'CAPSULE;ORAL',
          Strength: '50,000 IU',
          ReferenceDrug: '1',
          DrugName: 'DRISDOL',
          ActiveIngredient: 'ERGOCALCIFEROL',
          ReferenceStandard: '1'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003718',
          ProductNo: '004',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5MG/ML',
          ReferenceDrug: '0',
          DrugName: 'SYNKAYVITE',
          ActiveIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003718',
          ProductNo: '006',
          Form: 'INJECTABLE;INJECTION',
          Strength: '10MG/ML',
          ReferenceDrug: '0',
          DrugName: 'SYNKAYVITE',
          ActiveIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003718',
          ProductNo: '008',
          Form: 'INJECTABLE;INJECTION',
          Strength: '37.5MG/ML',
          ReferenceDrug: '0',
          DrugName: 'SYNKAYVITE',
          ActiveIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003718',
          ProductNo: '010',
          Form: 'TABLET;ORAL',
          Strength: '5MG',
          ReferenceDrug: '0',
          DrugName: 'SYNKAYVITE',
          ActiveIngredient: 'MENADIOL SODIUM DIPHOSPHATE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003977',
          ProductNo: '001',
          Form: 'INJECTABLE;INJECTION',
          Strength: '1MG/ML',
          ReferenceDrug: '0',
          DrugName: 'THEELIN',
          ActiveIngredient: 'ESTRONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003977',
          ProductNo: '002',
          Form: 'INJECTABLE;INJECTION',
          Strength: '2MG/ML',
          ReferenceDrug: '0',
          DrugName: 'THEELIN',
          ActiveIngredient: 'ESTRONE',
          ReferenceStandard: '0'
          , Owner: 'INOVA'
        },
        {
          ApplNo: '003977',
          ProductNo: '003',
          Form: 'INJECTABLE;INJECTION',
          Strength: '5MG/ML',
          ReferenceDrug: '0',
          DrugName: 'THEELIN',
          ActiveIngredient: 'ESTRONE',
          ReferenceStandard: '0',
          Owner: 'INOVA'
        },
        {
          ApplNo: '004039',
          ProductNo: '002',
          Form: 'TABLET, DELAYED RELEASE;ORAL',
          Strength: '0.1MG',
          ReferenceDrug: '0',
          DrugName: 'DIETHYLSTILBESTROL',
          ActiveIngredient: 'DIETHYLSTILBESTROL',
          ReferenceStandard: '0',
          Owner: 'INOVA'
        }
      ];

    for (let i = 0; i < medications.length; i++) {
      medications[i].docType = 'medication';
      await ctx.stub.putState('MED' + i, Buffer.from(JSON.stringify(medications[i])));
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

  async createMedication(ctx, medNumber, ApplNo, ProductNo, Form, Strength, ReferenceDrug, DrugName, ActiveIngredient, ReferenceStandard, Owner) {
    console.info('============= START : Create medication ===========');

    const medication = {
      docType: 'medication',
      ApplNo,
      ProductNo,
      Form,
      Strength,
      ReferenceDrug,
      DrugName,
      ActiveIngredient,
      ReferenceStandard,
      Owner
    };

    await ctx.stub.putState(medNumber, Buffer.from(JSON.stringify(medication)));
    console.info('============= END : Create medication ===========');
  }

  async queryAllMedications(ctx) {
    const startKey = 'MED0';
    const endKey = 'MED999';

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

  async prescribeMedication(ctx, medNumber, newOwner) {
    console.info('============= START : prescribeMedication ===========');

    const medAsBytes = await ctx.stub.getState(medNumber); // get the medication from chaincode state
    if (!medAsBytes || medAsBytes.length === 0) {
      throw new Error(`${medNumber} does not exist`);
    }
    const medication = JSON.parse(medAsBytes.toString());
    medication.owner = newOwner;

    await ctx.stub.putState(medNumber, Buffer.from(JSON.stringify(medication)));
    console.info('============= END : prescribeMedication ===========');
  }

}

module.exports = Medication;
