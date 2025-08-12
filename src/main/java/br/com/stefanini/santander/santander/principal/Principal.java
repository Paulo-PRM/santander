package br.com.stefanini.santander.santander.principal;

import br.com.stefanini.santander.santander.dto.CepDTO;
import br.com.stefanini.santander.santander.model.ConsultaCepModel;
import br.com.stefanini.santander.santander.repository.ConsultaRepository;
import br.com.stefanini.santander.santander.service.ConsultaAPICepService;
import br.com.stefanini.santander.santander.service.ValidarCepSolicitado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private static final String ERRO_CONSULTA_CEPS = "Erro ao consultar CEPs: ";
    private static final String TOTAL_CEPS_ENCONTRADOS = "Total de CEPs encontrados: ";
    private static final String DIGITE_CEP = "Digite o CEP desejado: ";
    private static final String DIGITE_NOME = "Favor digitar seu nome: ";
    private static final String CEP_NAO_ENCONTRADO =  "O CEP informado não foi encontrado ou não existe ";

    private final ConsultaAPICepService apiCepService;
    private final ConsultaRepository consultaRepository;
    private final ValidarCepSolicitado validarCepSolicitado;

    @Autowired
    public Principal(ConsultaAPICepService apiCepService, ConsultaRepository consultaRepository,ValidarCepSolicitado validarCepSolicitado) {
        this.apiCepService = apiCepService;
        this.consultaRepository = consultaRepository;
        this.validarCepSolicitado = validarCepSolicitado;
    }

    public void getCep() throws Exception {
        List<CepDTO> cepDTO = new ArrayList<>();
        try {
            cepDTO = apiCepService.consultarCep();
        } catch (Exception e) {
            System.err.println(ERRO_CONSULTA_CEPS + e.getMessage());
            throw new Exception();
        }
        ConsultaCepModel cepModel = new ConsultaCepModel();

        Scanner scanner = new Scanner(System.in);
        System.out.print(DIGITE_CEP);
        String cep = scanner.next();
        System.out.println(DIGITE_NOME);
        String usuario = scanner.next();
        scanner.close();

        cepModel.setCeps(validarCepSolicitado.validarCepSolicitado(cepDTO, cep));
        if(cepModel.getCeps().isEmpty()){
            System.out.println(CEP_NAO_ENCONTRADO + cep);
        } else {
            cepModel.setUsuario(usuario);
            cepModel.setDataConsulta(new Date());
            consultaRepository.save(cepModel);
            System.out.println(TOTAL_CEPS_ENCONTRADOS + cepModel.getCeps().size() + " - " + cepModel.getCeps().toString());
        }

    }

}