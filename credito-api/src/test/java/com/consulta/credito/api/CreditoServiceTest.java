package com.consulta.credito.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.consulta.credito.api.dto.CreditoDTO;
import com.consulta.credito.api.exception.ResourceNotFoundException;
import com.consulta.credito.api.mapper.CreditoMapper;
import com.consulta.credito.api.model.Credito;
import com.consulta.credito.api.repository.CreditoRepository;
import com.consulta.credito.api.service.CreditoService;
import com.consulta.credito.api.service.KafkaProducerService;

@ExtendWith(MockitoExtension.class)
public class CreditoServiceTest {

    @InjectMocks
    private CreditoService creditoService;

    @Mock
    private CreditoRepository creditoRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @Mock
    private CreditoMapper mapper;

    /*  
     * Método auxiliar para criar um objeto Credito.
     */
    private Credito criarCredito() {
        Credito credito = new Credito();
        credito.setId(1L);
        credito.setNumeroCredito("123456");
        credito.setNumeroNfse("7891011");
        credito.setDataConstituicao(LocalDate.of(2024, 2, 25));
        credito.setValorIssqn(new BigDecimal("1500.75"));
        credito.setTipoCredito("ISSQN");
        credito.setSimplesNacional(true);
        credito.setAliquota(new BigDecimal("5.00"));
        credito.setValorFaturado(new BigDecimal("30000.00"));
        credito.setValorDeducao(new BigDecimal("5000.00"));
        credito.setBaseCalculo(new BigDecimal("25000.00"));
        return credito;
    }

    /*  
     * Método auxiliar para criar um objeto CreditoDTO.
     */
    private CreditoDTO criarCreditoDTO() {
        return new CreditoDTO(
                "123456",
                "7891011",
                LocalDate.of(2024, 2, 25),
                new BigDecimal("1500.75"),
                "ISSQN",
                true,
                new BigDecimal("5.00"),
                new BigDecimal("30000.00"),
                new BigDecimal("5000.00"),
                new BigDecimal("25000.00")
        );
    }

    @Test
    @DisplayName("procurarPorNumeroNfse - Quando existir, deve retornar lista de créditos")
    void findByNumeroNfse_WhenExists_ShouldReturnListOfCreditos() {
        // Arrange
        String numeroNfse = "7891011";
        List<Credito> creditos = Arrays.asList(criarCredito());
        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(creditos);
        when(mapper.toDTO(any(Credito.class))).thenReturn(criarCreditoDTO());

        // Act
        List<CreditoDTO> resultado = creditoService.procurarPorNumeroNfse(numeroNfse);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("123456", resultado.get(0).getNumeroCredito());
        verify(kafkaProducerService, times(1)).enviarMensagem("numeroNfse", numeroNfse);
    }

    @Test
    @DisplayName("procurarPorNumeroNfse - Quando não encontrado, deve lançar ResourceNotFoundException")
    void findByNumeroNfse_WhenNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        String numeroCredito = "999999";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> creditoService.procurarPorNumeroCredito(numeroCredito));
        verify(kafkaProducerService, times(1)).enviarMensagem("numeroCredito", numeroCredito);
    }

    @Test
    @DisplayName("procurarPorNumeroCredito - Quando encontrado, deve retornar o crédito")
    void findByNumeroCredito_WhenExists_ShouldReturnCredito() {
        // Arrange
        String numeroCredito = "123456";
        Credito credito = criarCredito();
        CreditoDTO creditoDTO = criarCreditoDTO();
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.of(credito));
        when(mapper.toDTO(credito)).thenReturn(creditoDTO);

        // Act
        CreditoDTO resultado = creditoService.procurarPorNumeroCredito(numeroCredito);

        // Assert
        assertEquals(creditoDTO, resultado);
        verify(kafkaProducerService, times(1)).enviarMensagem("numeroCredito", numeroCredito);
    }

    @Test
    @DisplayName("procurarPorNumeroCredito - Quando não encontrado, deve lançar ResourceNotFoundException")
    void findByNumeroCredito_WhenNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange
        String numeroCredito = "999999";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> creditoService.procurarPorNumeroCredito(numeroCredito));
        verify(kafkaProducerService, times(1)).enviarMensagem("numeroCredito", numeroCredito);
    }
}