package com.mantech.hyperledger.rest;

import java.util.Objects;

public class Medication {
    String Key;
    MedicalRecord Record;

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public MedicalRecord getRecord() {
        return Record;
    }

    public void setRecord(MedicalRecord record) {
        this.Record = record;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medication)) return false;
        Medication that = (Medication) o;
        return Key.equals(that.Key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Key);
    }

}
