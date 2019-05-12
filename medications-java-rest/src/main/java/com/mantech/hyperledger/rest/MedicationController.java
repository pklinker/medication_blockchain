package com.mantech.hyperledger.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.protobuf.ByteString;
import com.mantech.hyperledger.client.AppUser;
import com.mantech.hyperledger.client.ChannelUtil;
import com.mantech.hyperledger.client.JavaSDKFabCarExample;
import org.apache.log4j.Logger;
import org.codehaus.plexus.component.configurator.converters.composite.ObjectWithFieldsConverter;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@RestController

public class MedicationController {
    private static final Logger log = Logger.getLogger(JavaSDKFabCarExample.class);
    private static final String HOSTNAME = "172.20.180.33";
    private static final String URL_ROOT = "http://" + HOSTNAME;
    private static final String GRCP_ROOT = "grpc://" + HOSTNAME;
    private static final String template = "Hello, %s!";

private static final String SAMPLE_MEDICATIONS = "[{\"Key\":\"TESTMED1\",\"Record\":{\"activeIngredient\":\"HYDROXYAMPHETAMINE HYDROBROMIDE\",\"applNo\":\"000004\",\"docType\":\"medication\",\"drugName\":\"PAREDRINE\",\"form\":\"SOLUTION/DROPS;OPHTHALMIC\",\"lotNo\":1,\"owner\":\"BigPharma\",\"productNo\":\"004\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"1%\"}},{\"Key\":\"TESTMED2\",\"Record\":{\"activeIngredient\":\"SULFAPYRIDINE\",\"applNo\":\"000159\",\"docType\":\"medication\",\"drugName\":\"SULFAPYRIDINE\",\"form\":\"TABLET;ORAL\",\"lotNo\":2,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"500MG\"}},{\"Key\":\"TESTMED3\",\"Record\":{\"activeIngredient\":\"HISTAMINE PHOSPHATE\",\"applNo\":\"000734\",\"docType\":\"medication\",\"drugName\":\"HISTAMINE PHOSPHATE\",\"form\":\"INJECTABLE;INJECTION\",\"lotNo\":11,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"EQ 1MG BASE/ML\"}},{\"Key\":\"MED-11\",\"Record\":{\"activeIngredient\":\"HISTAMINE PHOSPHATE\",\"applNo\":\"000734\",\"docType\":\"medication\",\"drugName\":\"HISTAMINE PHOSPHATE\",\"form\":\"INJECTABLE;INJECTION\",\"lotNo\":12,\"owner\":\"BigPharma\",\"productNo\":\"002\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"EQ 0.2MG BASE/ML\"}}]";
private static final String SAMPLE_MEDICATION="{\"activeIngredient\":\"VERARD\",\"applNo\":\"001504\",\"docType\":\"medication\",\"drugName\":\"VERARD\",\"form\":\"UNKNOWN\",\"lotNo\":16,\"owner\":\"BigPharma\",\"productNo\":\"001\",\"quantity\":1000,\"recipient\":\"\",\"referenceDrug\":\"0\",\"referenceStandard\":\"0\",\"strength\":\"UNKNOWN\"}";
    @RequestMapping("/getMedications")
    public String getMedications() {
        String result = "";
        try {
            // create fabric-ca client

            HFClient client = getHFClient();
            Channel channel = getChannel(client);

            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, "mychannel", "medications", "queryAllMedications", null);
            for (ProposalResponse response : proposalResponses) {
                if (response.isVerified() && response.getStatus() == ChaincodeResponse.Status.SUCCESS) {
                    ByteString payload = response.getProposalResponse().getResponse().getPayload();
                    String json = payload.toString();
                    Gson gson = new Gson();
                    try {
                        MedicalRecord[] record = gson.fromJson(json, MedicalRecord[].class);

                        log.info("MedicalRecord size: " + record.length);
                    } catch (JsonSyntaxException ex){
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
                    result = result.replace("\\","");
                    result = result.replace("\"[","[");
                    result = result.replace("]\"","]");
                    Gson gson = new Gson();
                    try {
                        Medication[] medications = gson.fromJson(result, Medication[].class);

                        log.info("Medications size: " + medications.length);
                    } catch (JsonSyntaxException ex){
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

    @RequestMapping("/getMedication")
    public String getMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum) {
        String response = "";
        if ("TESTMED".equalsIgnoreCase(medNum))
            return SAMPLE_MEDICATION;
        try {
            HFClient client = getHFClient();
            Channel channel = getChannel(client);
            ArrayList<String> args = new ArrayList<>();
            args.add(medNum);
            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, "mychannel", "medications", "queryMedication", args);
            if (proposalResponses == null) {
                log.info("No responses");
            } else {
                for (ProposalResponse proposalResponse : proposalResponses) {
                    response = new String(proposalResponse.getChaincodeActionResponsePayload());
                    response = response.replace("\\","");
                    response = response.replace("\"[","[");
                    response = response.replace("]\"","]");
                }
            }
        } catch (Exception ex) {
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return response;

        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/transferMedication")
    public String transferMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
                                       @RequestParam(value = "owner") String owner) {
        log.info("Begin transferMedication");
        String result = "";
        if ("-1".equals(medNum)) {
            log.info("Got a test transferMedication call.");
            return "Transferred " + medNum + " to " + owner;
        }
        try {
            HFClient client = getHFClient();
            Channel channel = getChannel(client);
            TransactionProposalRequest tpr = client.newTransactionProposalRequest();
            ChaincodeID cid = ChaincodeID.newBuilder().setName("medications").build();
            tpr.setChaincodeID(cid);
            tpr.setFcn("transferMedicationLot");
            tpr.setArgs(new String[]{medNum, owner});

            Collection<ProposalResponse> txResponses = channel.sendTransactionProposal(tpr);

            List<ProposalResponse> invalid = txResponses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    log.error(response.getMessage());
                });
                throw new RuntimeException("invalid response(s) found");
            }
            BlockEvent.TransactionEvent event = channel.sendTransaction(txResponses).get(20, TimeUnit.SECONDS);
            ;

            if (event.isValid()) {
                result = "Transaction tx: " + event.getTransactionID() + " is completed.";
                log.info(result);
            } else {
                result = "Transaction tx: " + event.getTransactionID() + " is failed.";
                log.error(result);
            }
        } catch (TimeoutException te) {
            result = "Transaction is pending.";
        } catch (Exception ex) {
            result = "Problem communicating with blockchain: " + ex.getMessage();
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return result;

        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/dispenseMedication")
    public String dispenseMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
                                     @RequestParam(value = "patient") String patient,
                                     @RequestParam(value = "quantity") String quantity) {
        String result = "Unknown";
        try {
            HFClient client = getHFClient();
            Channel channel = getChannel(client);

            TransactionProposalRequest tpr = client.newTransactionProposalRequest();
            ChaincodeID cid = ChaincodeID.newBuilder().setName("medications").build();
            tpr.setChaincodeID(cid);
            tpr.setFcn("dispenseMedication");
            tpr.setArgs(new String[]{medNum, patient, quantity});
            Collection<ProposalResponse> txResponses = channel.sendTransactionProposal(tpr);

            List<ProposalResponse> invalid = txResponses.stream().filter(r -> r.isInvalid()).collect(Collectors.toList());
            if (!invalid.isEmpty()) {
                invalid.forEach(response -> {
                    log.error(response.getMessage());
                });
                throw new RuntimeException("invalid response(s) found");
            }
            BlockEvent.TransactionEvent event = channel.sendTransaction(txResponses).get(20, TimeUnit.SECONDS);
            ;

            if (event.isValid()) {
                result = "Transaction tx: " + event.getTransactionID() + " is completed.";
                log.info(result);
            } else {
                result = "Transaction tx: " + event.getTransactionID() + " is failed.";
                log.error(result);
            }
        } catch (TimeoutException te) {
            result = "Transaction is pending.";
        } catch (Exception ex) {
            result = "Problem communicating with blockchain: " + ex.getMessage();
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return result;

        }

    }

    private HFClient getHFClient() throws Exception {
        HFCAClient caClient = ChannelUtil.getHfCaClient(URL_ROOT + ":7054", null);

        // enroll or load admin
        AppUser admin = ChannelUtil.getAdmin(caClient);
        log.info(admin);

        // register and enroll new user
        AppUser appUser = ChannelUtil.getUser(caClient, admin, "sales1");
        log.info(appUser);

        // get HFC client instance
        HFClient client = ChannelUtil.getHfClient();
        // set user context
        client.setUserContext(admin);
        // get HFC channel using the client, even though the variable isn't used,  this function must be called to
        // initialize it for the HFClient.


        return client;
    }

    private Channel getChannel(HFClient client) throws Exception {
        Channel channel = ChannelUtil.getChannel(client,
                "peer0.manufacturing.bigpharma.com",
                GRCP_ROOT + ":7051",
                "orderer.example.com",
                GRCP_ROOT + ":7050",
                "mychannel",
                "peer0",
                GRCP_ROOT + ":7053");
        log.info("Channel: " + channel.getName());
        return channel;
    }
}