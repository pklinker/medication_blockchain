package com.mantech.hyperledger.client;

import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.QueryByChaincodeRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 *
 */
public class ChannelUtil {

    private static final Logger log = Logger.getLogger(ChannelUtil.class);

    /**
     * Invoke blockchain query
     *
     * @param hfClient The HF Client
     * @throws ProposalException
     * @throws InvalidArgumentException
     */
    public static Collection<ProposalResponse> queryBlockChain(HFClient hfClient, String channelName, String chaincodeName, String function, ArrayList<String> args) throws ProposalException, InvalidArgumentException {
        Channel channel = hfClient.getChannel(channelName);
        // create chaincode request
        QueryByChaincodeRequest qpr = hfClient.newQueryProposalRequest();
        // build cc id providing the chaincode name. Version is omitted here.
        qpr.setChaincodeID(ChaincodeID.newBuilder().setName(chaincodeName).build());
        // CC function to be called
        qpr.setFcn(function);
        if (args != null) {
            qpr.setArgs(args);
        }
        Collection<ProposalResponse> res = channel.queryByChaincode(qpr);
        return res;
    }

    /**
     * Initialize and get HF channel
     *
     * @param client The HFC client
     * @param peerName Peer name
     * @param peerUrl Peer GRPC URL endpoint
     * @param ordererName Orderer hub
     * @param ordererUrl Orderer GRPC URL endpoint
     * @param channelName
     * @return
     * @throws InvalidArgumentException
     * @throws TransactionException
     */
    public static Channel getChannel(HFClient client,
                                     String peerName,
                                     String peerUrl,
                                     String ordererName,
                                     String ordererUrl,
                                     String channelName,
                                     String eventHubName,
                                     String eventHubUrl) throws InvalidArgumentException, TransactionException {
        // initialize channel
        Channel channel = client.newChannel(channelName);
        channel.addPeer(client.newPeer(peerName, peerUrl));
//        channel.addEventHub(client.newEventHub(eventHubName, eventHubUrl));
        channel.addOrderer(client.newOrderer(ordererName, ordererUrl));
        channel.initialize();
        return channel;
    }

    /**
     * Create new HLF client
     *
     * @return new HLF client instance. Never null.
     * @throws CryptoException
     * @throws InvalidArgumentException
     */
    public static HFClient getHfClient() throws Exception {
        // initialize default cryptosuite
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        // setup the client
        HFClient client = HFClient.createNewInstance();
        client.setCryptoSuite(cryptoSuite);
        return client;
    }


    /**
     * Register and enroll user with userId.
     * If AppUser object with the name already exist on fs it will be loaded and
     * registration and enrollment will be skipped.
     *
     * @param caClient  The fabric-ca client.
     * @param registrar The registrar to be used.
     * @param userId    The user id.
     * @return AppUser instance with userId, affiliation,mspId and enrollment set.
     * @throws Exception
     */
    public static AppUser getUser(HFCAClient caClient, AppUser registrar, String userId) throws Exception {
        AppUser appUser = tryDeserialize(userId);
        if (appUser == null) {
            RegistrationRequest rr = new RegistrationRequest(userId, "");
            String enrollmentSecret = caClient.register(rr, registrar);
            Enrollment enrollment = caClient.enroll(userId, enrollmentSecret);
            appUser = new AppUser(userId, "", "ManufacturingMSP", enrollment);
            serialize(appUser);
        }
        return appUser;
    }

    /**
     * Enroll admin into fabric-ca using {@code admin/adminpw} credentials.
     * If AppUser object already exist serialized on fs it will be loaded and
     * new enrollment will not be executed.
     *
     * @param caClient The fabric-ca client
     * @return AppUser instance with userid, affiliation, mspId and enrollment set
     * @throws Exception
     */
    public  static AppUser getAdmin(HFCAClient caClient) throws Exception {
        AppUser admin = tryDeserialize("admin");
        if (admin == null) {
            Enrollment adminEnrollment = caClient.enroll("admin", "adminpw");
            admin = new AppUser("admin", "", "ManufacturingMSP", adminEnrollment);
            serialize(admin);
        }
        return admin;
    }

    /**
     * Get new fabic-ca client
     *
     * @param caUrl              The fabric-ca-server endpoint url
     * @param caClientProperties The fabri-ca client properties. Can be null.
     * @return new client instance. never null.
     * @throws Exception
     */
    public static HFCAClient getHfCaClient(String caUrl, Properties caClientProperties) throws Exception {
        CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
        HFCAClient caClient = HFCAClient.createNewInstance(caUrl, caClientProperties);
        caClient.setCryptoSuite(cryptoSuite);
        return caClient;
    }


    // user serialization and deserialization utility functions
    // files are stored in the base directory

    /**
     * Serialize AppUser object to file
     *
     * @param appUser The object to be serialized
     * @throws IOException
     */
    public static void serialize(AppUser appUser) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(
                Paths.get(appUser.getName() + ".jso")))) {
            oos.writeObject(appUser);
        }
    }

    /**
     * Deserialize AppUser object from file
     *
     * @param name The name of the user. Used to build file name ${name}.jso
     * @return
     * @throws Exception
     */
    public static AppUser tryDeserialize(String name) throws Exception {
        if (Files.exists(Paths.get(name + ".jso"))) {
            return deserialize(name);
        }
        return null;
    }

    public static AppUser deserialize(String name) throws Exception {
        try (ObjectInputStream decoder = new ObjectInputStream(
                Files.newInputStream(Paths.get(name + ".jso")))) {
            return (AppUser) decoder.readObject();
        }
    }
}
