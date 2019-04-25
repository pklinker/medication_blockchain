package com.mantech.hyperledger.rest;

import com.mantech.hyperledger.client.AppUser;
import com.mantech.hyperledger.client.ChannelUtil;
import com.mantech.hyperledger.client.JavaSDKFabCarExample;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.annotation.ExceptionProxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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


    @RequestMapping("/getMedications")
    public String getMedications() {
        String response ="";
        try {
            // create fabric-ca client

            HFClient client = getHFClient();
            Channel channel = getChannel(client);

            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, "mychannel", "medications", "queryAllMedications", null);
            if (proposalResponses == null) {
                log.info("No responses");
            } else {
                for (ProposalResponse proposalResponse : proposalResponses) {
                   response = new String(proposalResponse.getChaincodeActionResponsePayload());
                }
            }
        } catch (Exception ex) {
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return response;

        }
    }
    @RequestMapping("/getMedication")
    public String getMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum) {
        String response ="";
        try {
        HFClient client = getHFClient();
        Channel channel = getChannel(client);
        ArrayList<String> args = new ArrayList<>() ;
        args.add(medNum);
        // call query blockchain example
        Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, "mychannel", "medications", "queryMedication", args);
        if (proposalResponses == null) {
            log.info("No responses");
        } else {
            for (ProposalResponse proposalResponse : proposalResponses) {
                response = new String(proposalResponse.getChaincodeActionResponsePayload());
            }
        }
    } catch (Exception ex) {
        log.error("Problem communicating with blockchain.", ex);
    } finally {
        return response;

    }
    }


    @RequestMapping(method = RequestMethod.POST, value="/transferMedicationLot")
    public String distributeMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
                                        @RequestParam(value="owner") String owner)
    {
        String result ="";
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
            BlockEvent.TransactionEvent event = channel.sendTransaction(txResponses).get(20, TimeUnit.SECONDS);;

            if (event.isValid()) {
                result = "Transaction tx: " + event.getTransactionID() + " is completed.";
                log.info(result);
            } else {
                result = "Transaction tx: " + event.getTransactionID() + " is failed.";
                log.error(result);
            }
        } catch (TimeoutException te) {
            result = "Transaction is pending.";
        }
        catch (Exception ex) {
            result = "Problem communicating with blockchain: " + ex.getMessage();
            log.error("Problem communicating with blockchain.", ex);
        } finally {
            return result;

        }

    }

    @RequestMapping(method = RequestMethod.POST, value="/dispenseMedication")
    public String dispenseMedication(@RequestParam(value = "medNum", defaultValue = "MED-9") String medNum,
                                     @RequestParam(value="patient") String patient,
                                     @RequestParam(value="quantity") String quantity)
    {
        String result="Unknown";
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
            BlockEvent.TransactionEvent event = channel.sendTransaction(txResponses).get(20, TimeUnit.SECONDS);;

            if (event.isValid()) {
                result = "Transaction tx: " + event.getTransactionID() + " is completed.";
                log.info(result);
            } else {
                result = "Transaction tx: " + event.getTransactionID() + " is failed.";
                log.error(result);
            }
        } catch (TimeoutException te) {
            result = "Transaction is pending.";
        }
        catch (Exception ex) {
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
                GRCP_ROOT+":7053");
        log.info("Channel: " + channel.getName());
        return channel;
    }
}