package com.consulta.credito.api.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    /**
     * Método que escuta mensagens do tópico "consulta-credito" no Kafka.
     * 
     * @param record O registro de consumidor que contém a mensagem recebida.
     */
    @KafkaListener(topics = "consulta-credito", groupId = "grupo-consulta")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println(String.format(
            "Mensagem recebida do Kafka: \nTópico: %s \nChave: %s \nValor: %s \nOffset: %d",
            record.topic(),
            record.key(),
            record.value(),
            record.offset()
        ));
    }
}