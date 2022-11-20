//package ru.sotn.imageservice.config;
//
//import com.thoughtworks.xstream.converters.basic.StringBufferConverter;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.LongDeserializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.converter.MessageConverter;
//import org.springframework.kafka.support.converter.MessagingMessageConverter;
//import org.springframework.kafka.support.converter.StringJsonMessageConverter;
//import org.springframework.messaging.converter.StringMessageConverter;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class ConsumerKafkaConfig {
//    @Value("${kafka.server}")
//    private String kafkaServer;
//
//    @Value("${kafka.group.id}")
//    private String kafkaGroupId;
//
//    @Bean
//    public KafkaListenerContainerFactory<?> singleFactory(){
//        ConcurrentKafkaListenerContainerFactory<Long, MultipartFile> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//       factory.setConsumerFactory(consumerFactory());
//       factory.setBatchListener(false);
//       factory.setMessageConverter(new MessagingMessageConverter());
//       return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<Long,MultipartFile> consumerFactory(){
//        return new DefaultKafkaConsumerFactory<>(consumerConfig());
//    }
//
//    @Bean
//    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(){
//        return new ConcurrentKafkaListenerContainerFactory<>();
//    }
//    @Bean
//    public Map<String, Object> consumerConfig(){
//        Map<String, Object> props  = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
//        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
//        return props;
//    }
//    @Bean
//    public MessageConverter converter(){
//        return new MessagingMessageConverter();
//    }
//}
