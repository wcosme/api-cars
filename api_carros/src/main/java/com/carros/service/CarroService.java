package com.carros.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carros.domain.Carro;
import com.carros.repository.CarroRepository;

@Service
public class CarroService {

	@Autowired
	private CarroRepository rep;

	public Iterable<Carro> getCarros() {
		return rep.findAll();
	}

	public Optional<Carro> getCarrosById(Long id) {
		return rep.findById(id);
	}

	public Iterable<Carro> getCarrosByTipo(String tipo) {

		return rep.findByTipo(tipo);
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
		Optional<Carro> carro = rep.findById(id);
		if (carro.isPresent()) {			
			rep.deleteById(id);
		} else {
			throw new RuntimeException("Não foi possível excluir o registro");
		}
	}
}
