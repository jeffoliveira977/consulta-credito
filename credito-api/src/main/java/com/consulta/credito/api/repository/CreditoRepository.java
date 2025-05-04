package com.consulta.credito.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consulta.credito.api.model.Credito;

public interface CreditoRepository extends JpaRepository<Credito, Long> {

    // Métodos de consulta personalizados
    List<Credito> findByNumeroNfse(String numeroNfse);
    Optional<Credito> findByNumeroCredito(String numeroCredito);
}