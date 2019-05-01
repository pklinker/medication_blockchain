package com.mantech.hyperledger.rest;

import java.util.Objects;

public class MedicalRecord {
     String activeIngredient;
     String applNo;
     String docType;
     String drugName;
     String form;
     int lotNo;
     String owner;
     String productNo;
     int quantity;
     String recipient;
     String referenceDrug;
     String referenceStandard;
     String strength;

    public int getLotNo() {
        return lotNo;
    }

    public void setLotNo(int lotNo) {
        this.lotNo = lotNo;
    }
    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getApplNo() {
        return applNo;
    }

    public void setApplNo(String applNo) {
        this.applNo = applNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getReferenceDrug() {
        return referenceDrug;
    }

    public void setReferenceDrug(String referenceDrug) {
        this.referenceDrug = referenceDrug;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getReferenceStandard() {
        return referenceStandard;
    }

    public void setReferenceStandard(String referenceStandard) {
        this.referenceStandard = referenceStandard;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecord)) return false;
        MedicalRecord that = (MedicalRecord) o;
        return lotNo == that.lotNo &&
                activeIngredient.equals(that.activeIngredient) &&
                applNo.equals(that.applNo) &&
                docType.equals(that.docType) &&
                drugName.equals(that.drugName) &&
                form.equals(that.form) &&
                productNo.equals(that.productNo) &&
                referenceDrug.equals(that.referenceDrug) &&
                referenceStandard.equals(that.referenceStandard) &&
                strength.equals(that.strength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeIngredient, applNo, docType, drugName, form, lotNo, productNo, referenceDrug, referenceStandard, strength);
    }
}
