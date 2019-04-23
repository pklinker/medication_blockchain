package com.mantech.hyperledger.rest;

import com.mantech.hyperledger.client.AppUser;
import com.mantech.hyperledger.client.ChannelUtil;
import com.mantech.hyperledger.client.JavaSDKFabCarExample;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController

public class MedicationController {
    private static final Logger log = Logger.getLogger(JavaSDKFabCarExample.class);
    private static final String HOSTNAME = "172.20.180.34";
    private static final String URL_ROOT = "http://" + HOSTNAME;
    private static final String GRCP_ROOT = "grpc://" + HOSTNAME;
    private static final String template = "Hello, %s!";

    @RequestMapping("/getMedications")
    public String getMedications() {
        String response ="";
        try {
            // create fabric-ca client
            HFCAClient caClient = ChannelUtil.getHfCaClient(URL_ROOT + ":7054", null);

            // enroll or load admin
            AppUser admin = ChannelUtil.getAdmin(caClient);
            log.info(admin);

            // register and enroll new user
            AppUser appUser = ChannelUtil.getUser(caClient, admin, "user1");
            log.info(appUser);

            // get HFC client instance
            HFClient client = ChannelUtil.getHfClient();
            // set user context
            client.setUserContext(admin);

            // get HFC channel using the client
            Channel channel = ChannelUtil.getChannel(client,
                    "peer0.org1.example.com",
                    GRCP_ROOT + ":7051",
                    "orderer.example.com",
                    GRCP_ROOT + ":7050",
                    "mychannel");
            log.info("Channel: " + channel.getName());

            // call query blockchain example
            Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client, "mychannel", "medications", "queryAllMedications");
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

    public String getMedication(@RequestParam(value = "applNo", defaultValue = "001") String applNo) {
return "";
    }
}