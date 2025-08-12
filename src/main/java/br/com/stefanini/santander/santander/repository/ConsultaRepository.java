package br.com.stefanini.santander.santander.repository;

import br.com.stefanini.santander.santander.model.ConsultaCepModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends MongoRepository<ConsultaCepModel, String> {
}
