package com.consulta.credito.api.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.consulta-credito:consulta-credito}")
    private String topicName;

    /**
     * Envia uma mensagem para o tópico Kafka com os detalhes da consulta de crédito.
     *
     * @param tipoConsulta      O tipo de consulta (ex: "numeroNfse", "numeroCredito").
     * @param valorConsultado   O valor consultado (ex: número da NFS-e ou número do crédito).
     */
    public void enviarMensagem(String tipoConsulta, String valorConsultado) {
        Map<String, Object> evento = new HashMap<>();
        evento.put("tipoConsulta", tipoConsulta);
        evento.put("valorConsultado", valorConsultado);
        evento.put("dataHora", LocalDateTime.now().toString());

       kafkaTemplate.send(topicName, evento);
    }
}
