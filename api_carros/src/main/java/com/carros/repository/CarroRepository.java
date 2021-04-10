package com.carros.repository;

import org.springframework.data.repository.CrudRepository;

import com.carros.domain.Carro;

public interface CarroRepository extends CrudRepository<Carro, Long> {

	Iterable<Carro> findByTipo(String tipo);

}
