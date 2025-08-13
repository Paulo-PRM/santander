package br.com.stefanini.santander.santander.service;

import br.com.stefanini.santander.santander.dto.CepDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValidarCepSolicitado {

    public List<CepDTO> validarCepSolicitado(List<CepDTO> cepDTO, String cepDigitado) {
        return cepDTO.stream()
                .filter(cep -> List.of(cepDigitado.split(",")).contains(cep.getCep()))
                .toList();
    }

}
