package br.com.stefanini.santander.santander.service;

import br.com.stefanini.santander.santander.dto.CepDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidarCepSolicitado {

    public List<CepDTO> validarCepSolicitado(List<CepDTO> cepDTO, String cepDigitado) {

        List<CepDTO> encontrados = new ArrayList<>();
        cepDTO.stream()
                .filter(cep -> cep.getCep().equals(cepDigitado))
                .findFirst()
                .ifPresentOrElse(cp -> {
                    encontrados.add(CepDTO.builder()
                                    .cep(cp.getCep())
                                    .complemento(cp.getComplemento())
                                    .logradouro(cp.getLogradouro())
                                    .bairro(cp.getBairro())
                                    .localidade(cp.getLocalidade())
                                    .uf(cp.getUf())
                                    .ddd(cp.getDdd())
                            .build());
                }, () -> {
                    System.out.println("CEP não encontrado!!!!!!");
                });
        return encontrados;
    }
}
