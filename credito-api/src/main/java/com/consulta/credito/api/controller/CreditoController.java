package com.consulta.credito.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consulta.credito.api.dto.CreditoDTO;
import com.consulta.credito.api.service.CreditoService;

@RestController
@RequestMapping("/api/creditos")
public class CreditoController {

    private final CreditoService creditoService;

    /**
     * Construtor da classe CreditoController.
     * @param creditoService O serviço de crédito a ser injetado.
     */
    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    /**
     * Método para buscar créditos pelo número da NFS-e.
     * @param numeroNfse O número da NFS-e a ser buscado.
     * @return Uma lista de objetos CreditoDTO representando os créditos encontrados.
     */
    @GetMapping("/{numeroNfse}")
    public ResponseEntity<List<CreditoDTO>> getByNumeroNfse(@PathVariable String numeroNfse) {
        return ResponseEntity.ok(creditoService.procurarPorNumeroNfse(numeroNfse));
    }

    /**
     * Método para buscar um crédito pelo número do crédito.
     * @param numeroCredito O número do crédito a ser buscado.
     * @return Um objeto CreditoDTO representando o crédito encontrado.
     */
    @GetMapping("/credito/{numeroCredito}")
    public ResponseEntity<CreditoDTO> getByNumeroCredito(@PathVariable String numeroCredito) { 
        return ResponseEntity.ok(creditoService.procurarPorNumeroCredito(numeroCredito));
    }
}
