package com.carros.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.repository.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository rep;

	public List<CarroDTO> getCarros() {		
		return rep.findAll().stream().map(CarroDTO::create).collect(Collectors.toList());		
	}

	public Optional<CarroDTO> getCarrosById(Long id) {
		return rep.findById(id).map(CarroDTO::create);
	}

	public List<CarroDTO> getCarrosByTipo(String tipo) {	
		return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
	}

	public Carro save(Carro carro) {
		return rep.save(carro);
	}

	public Carro update(Carro carro, Long id) {

		// Busca o objeto no banco de dados
		Optional<Carro> optional = rep.findById(id);
		if (optional.isPresent()) {
			Carro db = optional.get();

			// Copia as propiedades
			db.setNome(carro.getNome());
			db.setTipo(carro.getTipo());

			// Atualiza o objeto
			rep.save(db);

			return db;
		} else {
			throw new RuntimeException("Não foi possível atualizar o registro");
		}
	}

	public void delete(Long id) {
		// Busca o objeto no banco de dados
		if (getCarrosById(id).isPresent()) {			
			rep.deleteById(id);
		} else {
			throw new RuntimeException("Não foi possível excluir o registro");
		}		
	}
}
