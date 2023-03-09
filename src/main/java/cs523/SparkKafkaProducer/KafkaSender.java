package cs523.SparkKafkaProducer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;

public class KafkaSender {

	private static String BOOTSTRAP_SERVERS ;
    private static String TOPIC ;
    public static KafkaProducer<String, String> kProducer;

    public static Producer<String, String> createProducer() {
    	if(kProducer != null){
    		return kProducer;
    	}
    	
    	MyResources resources;
		try {
			resources = new MyResources();
			TOPIC = resources.TOPIC;
			BOOTSTRAP_SERVERS = "localhost:"+resources.PORT;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        kProducer = new KafkaProducer<>(props);
        return kProducer;
    }

    
    public static void sendWeatherData(String id, String text) throws Exception {
    	final Producer<String, String> producer = createProducer();
        final ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, id, text);
        producer.send(record);
    }
}