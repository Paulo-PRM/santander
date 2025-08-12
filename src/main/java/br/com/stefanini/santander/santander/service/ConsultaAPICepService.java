package br.com.stefanini.santander.santander.service;

import br.com.stefanini.santander.santander.dto.CepDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ConsultaAPICepService {

    private static final String ERRO_CONSULTA_API_CEPS = "Erro ao consultar a API de CEP";
    private static final String CEP = "/cep";

    @Value("${api.cep.base-url}")
    private String baseUrl;

    @Value("${api.cep.context-path}")
    private String contextPath;

    private static final RestTemplate restTemplate = new RestTemplate();

    public List<CepDTO> consultarCep() throws Exception {
        String url = baseUrl.concat(contextPath).concat(CEP);
        CepDTO[] cepDTO;
        try {
            cepDTO = restTemplate.getForObject(url, CepDTO[].class);
        } catch (Exception e) {
            throw new Exception(ERRO_CONSULTA_API_CEPS, e);
        }
        return Arrays.asList(cepDTO);
    }

}