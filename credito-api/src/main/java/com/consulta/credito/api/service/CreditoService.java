package com.consulta.credito.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.consulta.credito.api.dto.CreditoDTO;
import com.consulta.credito.api.exception.ResourceNotFoundException;
import com.consulta.credito.api.mapper.CreditoMapper;
import com.consulta.credito.api.model.Credito;
import com.consulta.credito.api.repository.CreditoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditoService {

    private final CreditoRepository creditoRepository;
    private final KafkaProducerService kafkaPublisherService;
    private final CreditoMapper mapper;
    
    /**
     * Método para procurar créditos pelo número da NFS-e.
     * 
     * @param numeroNfse O número da NFS-e a ser procurado.
     * @return Uma lista de objetos CreditoDTO representando os créditos encontrados.
     * @throws ResourceNotFoundException Se nenhum crédito for encontrado.
     */
    public List<CreditoDTO> procurarPorNumeroNfse(String numeroNfse) {

        kafkaPublisherService.enviarMensagem("numeroNfse", numeroNfse);

        List<Credito> creditos = creditoRepository.findByNumeroNfse(numeroNfse);
       
        if (creditos.isEmpty()) {
            throw new ResourceNotFoundException("NFS-e", numeroNfse);
        }

        return creditos.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Método para procurar um crédito pelo número do crédito.
     * 
     * @param numeroCredito O número do crédito a ser procurado.
     * @return Um objeto CreditoDTO representando o crédito encontrado.
     * @throws ResourceNotFoundException Se o crédito não for encontrado.
     */
    public CreditoDTO procurarPorNumeroCredito(String numeroCredito) {

        kafkaPublisherService.enviarMensagem("numeroCredito", numeroCredito);
             
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito)
            .orElseThrow(() -> new ResourceNotFoundException("Credito", numeroCredito));

        return mapper.toDTO(credito);
    }
}