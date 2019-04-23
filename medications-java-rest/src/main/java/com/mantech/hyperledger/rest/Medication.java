package com.mantech.hyperledger.rest;

public class Medication {
    private  String docType;
    private  String applNo;
    private  String productNo;
    private  String form;
    private  String strength;
    private  String referenceDrug;
    private  String drugName;
    private  String activeIngredient;
    private  String referenceStandard;
    private  String owner;

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
}
