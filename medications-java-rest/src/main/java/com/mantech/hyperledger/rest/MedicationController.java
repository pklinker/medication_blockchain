package com.mantech.hyperledger.rest;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.protobuf.ByteString;
import com.mantech.hyperledger.client.AppUser;
import com.mantech.hyperledger.client.ChannelUtil;
import com.mantech.hyperledger.client.JavaSDKFabCarExample;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/distribution")
public class MedicationController {
    @Value("${blockchain.network.channel}")
    private String channelName;

    @Value("${blockchain.network.address}")
    private String networkAddress;

    private static final Logger log = Logger.getLogger(JavaSDKFabCarExample.class);

    private String urlRoot = null;
    private String grcpRoot = null;
    private static final String template = "Hello, %s!";
    private static final String MANUFACTURING = "manufacturing";
    private static final String SHIPPING = "shipping";
    private static final String HOSPITAL = "hospital";

    private static final String SAMPLE_MEDICATIONS = "[{\"Key\":\"TESTMED1\",\"Record\":{\"activeIngredient\":\"HYDROXYAMPHETAMINE HYDROBROMIDE\",\"applNo\":\"000004\",\"docType\":\"medication\",\"drugName\":\"PAREDRINE\",\"form\":\"SOLUTION/DROPS;OPHTHALMIC\",\"lotNo\":1,\"owner\":\"BigPharma\",\"productNo\":\"004\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"1%\"}},{\"Key\":\"TESTMED2\",\"Record\":{\"activeIngredient\":\"SULFAPYRIDINE\",\"applNo\":\"000159\",\"docType\":\"medication\",\"drugName\":\"SULFAPYRIDINE\",\"form\":\"TABLET;ORAL\",\"lotNo\":2,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"500MG\"}},{\"Key\":\"TESTMED3\",\"Record\":{\"activeIngredient\":\"HISTAMINE PHOSPHATE\",\"applNo\":\"000734\",\"docType\":\"medication\",\"drugName\":\"HISTAMINE PHOSPHATE\",\"form\":\"INJECTABLE;INJECTION\",\"lotNo\":11,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"EQ 1MG BASE/ML\"}},{\"Key\":\"MED-11\",\"Record\":{\"activeIngredient\":\"HISTAMINE PHOSPHATE\",\"applNo\":\"000734\",\"docType\":\"medication\",\"drugName\":\"HISTAMINE PHOSPHATE\",\"form\":\"INJECTABLE;INJECTION\",\"lotNo\":12,\"owner\":\"BigPharma\",\"productNo\":\"002\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"EQ 0.2MG BASE/ML\"}}]";
    private static final String SAMPLE_MEDICATION = "{\"activeIngredient\":\"VERARD\",\"applNo\":\"001504\",\"docType\":\"medication\",\"drugName\":\"VERARD\",\"form\":\"UNKNOWN\",\"lotNo\":16,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"UNKNOWN\"}";


    @RequestMapping(path="/getMedications")
    public String getMedications(@RequestParam(value = "org") String orgName) {
        String result = "";
        try {
            // create fabric-ca client

            HFClient client = getHFClient(orgName);
            Channel channel = getChannel(orgName, client);

            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, channelName, "medications", "queryAllMedications", null);
            for (ProposalResponse response : proposalResponses) {
                if (response.isVerified() && response.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                    ByteString payload = response.getProposalResponse().getResponse().getPayload();
                    String json = payload.toString();
                    Gson gson = new Gson();
                    try {
                        MedicalRecord[] record = gson.fromJson(json, MedicalRecord[].class);

                        log.info("MedicalRecord size: " + record.length);
                    } catch (JsonSyntaxException ex) {
                        log.warn("MedicalRecord deserialization error: " + ex.getMessage());
                    }
                } else {
                    log.error("response failed. status: " + response.getStatus().getStatus());
                }
            }

            if (proposalResponses == null) {
                log.info("No responses");
            } else {
                for (ProposalResponse proposalResponse : proposalResponses) {
                    result = new String(proposalResponse.getChaincodeActionResponsePayload());
                    result = result.replace("\\", "");
                    result = result.replace("\"[", "[");
                    result = result.replace("]\"", "]");
                    Gson gson = new Gson();
                    try {
                        Medication[] medications = gson.fromJson(result, Medication[].class);

                        log.info("Medications size: " + medications.length);
                    } catch (JsonSyntaxException ex) {
                        log.warn("Medication deserialization error: " + ex.getMessage());
                    }
                }
            }
        } catch (Exception ex) {
            log.error("Problem communicating with blockchain.", ex);
            result = SAMPLE_MEDICATIONS;
        } finally {
            return result;

        }
    }

    @RequestMapping(path="/getMedication")
    public String getMedication(
            @RequestParam(value = "org") String orgName,
            @RequestParam(value = "medNum") String medNum) {
        String response = "";
        if ("TESTMED".equalsIgnoreCase(medNum))
            return SAMPLE_MEDICATION;
        try {
            HFClient client = getHFClient(orgName);
            Channel channel = getChannel(orgName, client);
            ArrayList<String> args = new ArrayList<>();
            args.add(medNum);
            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, channelName, "medications", "queryMedication", args);
            if (proposalResponses == null) {
                log.info("No responses");
            } else {
                for (ProposalResponse proposalResponse : proposalResponses) {
                    response = new String(proposalResponse.getChaincodeActionResponsePayload());
                    response = response.replace("\\", "");
                    response = response.replace("\"[", "[");
                    response = response.replace("]\"", "]");
                }
            }
        } catch (Exception ex) {
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return response;

        }
    }


    @RequestMapping(method = RequestMethod.POST, path = "/transferMedication")
    public String transferMedication(
            @RequestParam(value = "org") String orgName,
            @RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
            @RequestParam(value = "owner") String owner) {
        log.info("Begin transferMedication");
        String result = "";
        if ("-1".equals(medNum)) {
            log.info("Got a test transferMedication call.");
            return "Transferred " + medNum + " to " + owner;
        }
        try {
            HFClient client = getHFClient(orgName);
            Channel channel = getChannel(orgName, client);
            TransactionProposalRequest tpr = client.newTransactionProposalRequest();
            ChaincodeID cid = ChaincodeID.newBuilder().setName("medications").build();
            tpr.setChaincodeID(cid);
            tpr.setFcn("transferMedicationLot");
            tpr.setArgs(new String[]{medNum, owner});

            Collection<ProposalResponse> txResponses = channel.sendTransactionProposal(tpr);

            if (txResponses != null && !txResponses.isEmpty()) {
                List<ProposalResponse> validPRs = txResponses.stream().filter(r -> !r.isInvalid()).collect(Collectors.toList());
                result = validPRs.get(0).getTransactionID();
            }
            channel.sendTransaction(txResponses);
        } catch (Exception ex) {
            result = "Problem communicating with blockchain: " + ex.getMessage();
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return result;

        }

    }

    @RequestMapping(method = RequestMethod.POST, path = "/dispenseMedication")
    public String dispenseMedication(
            @RequestParam(value = "org") String orgName,
            @RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
            @RequestParam(value = "patient") String patient,
            @RequestParam(value = "quantity") String quantity) {
        String result = "Unknown";
        try {
            HFClient client = getHFClient(orgName);
            Channel channel = getChannel(orgName, client);

            TransactionProposalRequest tpr = client.newTransactionProposalRequest();
            ChaincodeID cid = ChaincodeID.newBuilder().setName("medications").build();
            tpr.setChaincodeID(cid);
            tpr.setFcn("dispenseMedication");
            tpr.setArgs(new String[]{medNum, patient, quantity});
            Collection<ProposalResponse> txResponses = channel.sendTransactionProposal(tpr);

            if (txResponses != null && !txResponses.isEmpty()) {
                List<ProposalResponse> validPRs = txResponses.stream().filter(r -> !r.isInvalid()).collect(Collectors.toList());
                result = validPRs.get(0).getTransactionID();
            }
             channel.sendTransaction(txResponses);
        } catch (Exception ex) {
            result = "Problem communicating with blockchain: " + ex.getMessage();
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return result;

        }

    }

    private HFClient getHFClient(String org) throws Exception {
        if (urlRoot == null)
            urlRoot = "http://" + networkAddress;
        if (grcpRoot == null)
            grcpRoot = "grpc://" + networkAddress;

        HFCAClient caClient = getHfCaClient(org);
        String mspId =  "ManufacturingMSP";

        if (SHIPPING.equalsIgnoreCase(org)) {
            mspId = "ShippingMSP";
        }
        else if (HOSPITAL.equalsIgnoreCase(org)) {
            mspId = "HospitalMSP";
        }

        // enroll or load admin
        AppUser admin = ChannelUtil.getAdmin(caClient, org, mspId);
        log.info(admin);

        // register and enroll new user
        AppUser appUser = ChannelUtil.getUser(caClient, admin, "java1", org, mspId);
        log.info(appUser);

        // get HFC client instance
        HFClient client = ChannelUtil.getHfClient();
        // set user context
        client.setUserContext(admin);
        return client;
    }

    private Channel getChannel(String orgName, HFClient client) throws Exception {
        log.info("the channel" + channelName);


        if (MANUFACTURING.equalsIgnoreCase(orgName)) {
            return getManufacturingChannel(client);
        }
        else if (SHIPPING.equalsIgnoreCase(orgName)) {
            return getShippingChannel(client);
        }
        else if (HOSPITAL.equalsIgnoreCase(orgName)) {
            return getHospitalChannel(client);
        }
        return null;
    }

    public HFCAClient getHfCaClient(String orgName) throws Exception {
        log.info("the channel" + channelName);

        if (MANUFACTURING.equalsIgnoreCase(orgName)) {
            return ChannelUtil.getHfCaClient(urlRoot + ":7054", null);
        }
        else if (SHIPPING.equalsIgnoreCase(orgName)) {
            return ChannelUtil.getHfCaClient(urlRoot + ":8054", null);
        }
        else if (HOSPITAL.equalsIgnoreCase(orgName)) {
            return ChannelUtil.getHfCaClient(urlRoot + ":9054", null);
        }
        return null;
    }
    private Channel getManufacturingChannel(HFClient client) throws Exception {
        Channel channel = ChannelUtil.getChannel(client,
                "peer0.manufacturing.bigpharma.com",
                grcpRoot + ":7051",
                "orderer.bigpharma.com",
                grcpRoot + ":7050",
                channelName,
                "peer0",
                grcpRoot + ":7053");
        return channel;
    }

    private Channel getShippingChannel(HFClient client) throws Exception {
        Channel channel = ChannelUtil.getChannel(client,
                "peer0.shipping.shipstuff.com",
                grcpRoot + ":8051",
                "orderer.bigpharma.com",
                grcpRoot + ":7050",
                channelName,
                "peer0",
                grcpRoot + ":8053");
        return channel;
    }

    private Channel getHospitalChannel(HFClient client) throws Exception {
        Channel channel = ChannelUtil.getChannel(client,
                "peer0.hospital.health.org",
                grcpRoot + ":9051",
                "orderer.bigpharma.com",
                grcpRoot + ":7050",
                channelName,
                "peer0",
                grcpRoot + ":9053");
        return channel;
    }
}