package com.consulta.credito.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.consulta.credito.api.dto.CreditoDTO;
import com.consulta.credito.api.model.Credito;

@Component
public class CreditoMapper {

    private final ModelMapper modelMapper;

    /**
     * Inicializa o ModelMapper e configura os mapeamentos.
     */
    public CreditoMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Credito.class, CreditoDTO.class);
    }

    /**
     * Converte um objeto Credito para CreditoDTO.
     *
     * @param credito o objeto Credito a ser convertido
     * @return o objeto CreditoDTO resultante da conversão
     */
    public CreditoDTO toDTO(Credito credito) {
        return modelMapper.map(credito, CreditoDTO.class);
    }
}