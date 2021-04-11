package com.carros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carros.domain.Carro;

public interface CarroRepository extends JpaRepository<Carro, Long> {

	List<Carro> findByTipo(String tipo);

}
