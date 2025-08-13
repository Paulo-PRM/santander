package br.com.stefanini.santander.santander.principal;

import br.com.stefanini.santander.santander.dto.CepDTO;
import br.com.stefanini.santander.santander.model.ConsultaCepModel;
import br.com.stefanini.santander.santander.repository.ConsultaRepository;
import br.com.stefanini.santander.santander.service.ConsultaAPICepService;
import br.com.stefanini.santander.santander.service.ValidarCepSolicitado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PrincipalTest {

    private ConsultaAPICepService apiCepService;
    private ConsultaRepository consultaRepository;
    private ValidarCepSolicitado validarCepSolicitado;
    private Principal principal;

    @BeforeEach
    void setUp() {
        apiCepService = mock(ConsultaAPICepService.class);
        consultaRepository = mock(ConsultaRepository.class);
        validarCepSolicitado = mock(ValidarCepSolicitado.class);
        principal = new Principal(apiCepService, consultaRepository, validarCepSolicitado);
    }

    @Test
    void deveSalvarConsultaComListaDeCeps() throws Exception {
        List<CepDTO> ceps = new ArrayList<>();
        ceps.add(CepDTO.builder()
                        .cep("12345678")
                        .logradouro("Rua A")
                        .bairro("Bairro A")
                        .localidade("Cidade A")
                        .uf("UF A")
                        .complemento("Complemento A")
                .build());
        ceps.add(CepDTO.builder()
                .cep("03020300")
                .logradouro("Rua B")
                .bairro("Bairro B")
                .localidade("Cidade B")
                .uf("UF B")
                .complemento("Complemento B")
                .build());
        when(apiCepService.consultarCep()).thenReturn(ceps);
        when(validarCepSolicitado.validarCepSolicitado(anyList(), anyString())).thenReturn(ceps);
        System.setIn(new java.io.ByteArrayInputStream("03020300\nUsuário\n".getBytes()));

        principal.getCep();

        ArgumentCaptor<ConsultaCepModel> captor = ArgumentCaptor.forClass(ConsultaCepModel.class);
        verify(consultaRepository).save(captor.capture());
        ConsultaCepModel model = captor.getValue();
        assert model.getCeps().size() == 2;
        assert "Usuário".equals(model.getUsuario());
        assert model.getDataConsulta() != null;
    }

    @Test
    void deveRetornarConsultaComListaVazia() throws Exception {
        List<CepDTO> ceps = new ArrayList<>();
        ceps.add(CepDTO.builder()
                .cep("12345678")
                .logradouro("Rua A")
                .bairro("Bairro A")
                .localidade("Cidade A")
                .uf("UF A")
                .complemento("Complemento A")
                .build());
        ceps.add(CepDTO.builder()
                .cep("03020300")
                .logradouro("Rua B")
                .bairro("Bairro B")
                .localidade("Cidade B")
                .uf("UF B")
                .complemento("Complemento B")
                .build());
        when(apiCepService.consultarCep()).thenReturn(ceps);
        when(validarCepSolicitado.validarCepSolicitado(anyList(), anyString())).thenReturn(List.of());
        System.setIn(new java.io.ByteArrayInputStream("03020300\nUsuário\n".getBytes()));

        principal.getCep();

        verify(consultaRepository, never()).save(any());

    }

    @Test
    void deveLancarExceptionAoFalharConsultaCep() throws Exception {
        when(apiCepService.consultarCep()).thenThrow(new RuntimeException("Falha na API"));
        System.setIn(new java.io.ByteArrayInputStream("03020300\nUsuário\n".getBytes()));

        Exception exception = assertThrows(Exception.class, () -> principal.getCep());
    }

}