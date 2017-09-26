/**
 * Created by Lahiru Samaranayaka on 9/26/2017.
 */

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttReceivedMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.sql.Timestamp;


public class MqttSubscriber implements MqttCallback {

    private MqttClient client;
    private String brokerUrl;
    private boolean quietMode;
    private MqttConnectOptions conOpt;
    private boolean clean;
    //String tmpDir = System.getProperty("java.io.tmpdir");
    //MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
    MemoryPersistence persistence = new MemoryPersistence();


    public MqttSubscriber(String brokerUrl, String clientId, boolean cleanSession, boolean quietMode) throws MqttException {
        this.brokerUrl = brokerUrl;
        this.quietMode = quietMode;
        this.clean = cleanSession;
        try {
            conOpt = new MqttConnectOptions();
            conOpt.setCleanSession(clean);

            // Construct an MQTT blocking mode client
            client = new MqttClient(this.brokerUrl, clientId, persistence);


        } catch (MqttException e) {
            e.printStackTrace();
            log("Unable to set up client: " + e.toString());
            System.exit(1);
        }

    }

    public static void main(String[] args) {

        String topic = "/minio";
        int qos = 2;
        String broker = "localhost";
        String port = "1883";
        String protocol = "tcp://";
        String clientid = MqttClient.generateClientId();
        String url = protocol + broker + ":" + port;
        Boolean cleanSession = true;
        Boolean quietMode = true;


        try {
            // Create an instance of this class
            MqttSubscriber Client = new MqttSubscriber(url, clientid, cleanSession, quietMode);

            // Perform the requested subscribe
            Client.subscribe(topic, qos);

        } catch (MqttException me) {
            // Display full details of any exception that occurs
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    public void subscribe(String topicName, int qos) throws MqttException {

        try {
            //set as call back handler
            client.setCallback(this);
            // Connect to the MQTT server
            client.connect(conOpt);
            log("Connected to " + brokerUrl + " with client ID " + client.getClientId());

            // Subscribe to the requested topic

            log("Subscribing to topic \"" + topicName + "\" qos " + qos);
            client.subscribe(topicName, qos);
            MqttReceivedMessage message = new MqttReceivedMessage();

            //Implement the Message setting s here
            System.out.println(message.getPayload().toString());
            // Disconnect the client from the server
            //client.disconnect();
            //log("Disconnected");
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }

    private void log(String message) {
        if (!quietMode) {
            System.out.println(message);
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log("Connection to " + brokerUrl + " lost!" + throwable);
        System.exit(1);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // Called when a message arrives from the server that matches any
        // subscription made by the client
        String time = new Timestamp(System.currentTimeMillis()).toString();
        System.out.println("Time:\t" + time +
                "  Topic:\t" + topic +
                "  Message:\t" + new String(message.getPayload()) +
                "  QoS:\t" + message.getQos());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
