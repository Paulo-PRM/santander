package br.com.stefanini.santander.santander.model;

import br.com.stefanini.santander.santander.dto.CepDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "consulta_cep_result")
public class ConsultaCepModel {

    @Id
    private String id;
    private List<CepDTO> ceps;
    private Date dataConsulta;
    private String usuario;

}