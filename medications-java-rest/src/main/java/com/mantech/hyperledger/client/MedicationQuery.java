package com.mantech.hyperledger.client;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric_ca.sdk.HFCAClient;

import java.util.Collection;

public class MedicationQuery {
    private static final Logger log = Logger.getLogger(JavaSDKFabCarExample.class);
    private static final String HOSTNAME = "172.20.180.34";
    private static final String URL_ROOT = "http://"+HOSTNAME;
    private static final String GRCP_ROOT = "grpc://"+HOSTNAME;

    public static void main(String[] args) throws Exception {
        // create fabric-ca client
        HFCAClient caClient = ChannelUtil.getHfCaClient(URL_ROOT+":7054", null);

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

        // get HFC channel using the client
        Channel channel = ChannelUtil.getChannel(client,
                "peer0.manufacturing.bigpharma.com",
                GRCP_ROOT+":7051",
                "orderer.example.com",
                GRCP_ROOT+":7050",
                "mychannel",
                "eventhub01",
                GRCP_ROOT+":7053");

        log.info("Channel: " + channel.getName());

        // call query blockchain example
        Collection<ProposalResponse> proposalResponses = ChannelUtil.queryBlockChain(client,"mychannel","medications","queryAllMedications", null);
        if (proposalResponses == null)
        {
            log.info("No responses");
        } else {
            for (ProposalResponse proposalResponse : proposalResponses) {
                String stringResponse = new String(proposalResponse.getChaincodeActionResponsePayload());
                log.info(stringResponse);
            }
        }
    }

}
