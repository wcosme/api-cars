package com.carros.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.service.CarroService;

@RestController
@RequestMapping("/api/v1/carros")
public class CarroController {
	
	@Autowired
	private CarroService service;
	
	@GetMapping
	public ResponseEntity<List<CarroDTO>>  getAll() {
		return ResponseEntity.ok(service.getCarros());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CarroDTO> getById(@PathVariable("id") Long id) {
		Optional<CarroDTO> carro = service.getCarrosById(id);
		
		//Usando Lambda
		return carro.map(c-> ResponseEntity.ok(c)).orElse(ResponseEntity.notFound().build());
		
		// Usando for ternário
		//return carro.isPresent() ? ResponseEntity.ok(carro) : ResponseEntity.notFound().build();
		
		/*
		 * if(carro.isPresent()) { return ResponseEntity.ok(carro); }else { return
		 * ResponseEntity.notFound().build(); }
		 */
		
	}
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<CarroDTO>> getByTipo(@PathVariable("tipo") String tipo) {
		List<CarroDTO> listaDecarros = service.getCarrosByTipo(tipo);
		
		return listaDecarros.isEmpty() ?
				ResponseEntity.noContent().build() :
				ResponseEntity.ok(listaDecarros);
	}
	
	@PostMapping
	public String save(@RequestBody Carro carro) {
		Carro car = service.save(carro);
		
		return "Carro salvo com sucesso " + car.toString();
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable("id") Long id, @RequestBody Carro carro) {
		Carro c = service.update(carro, id);
		return "Carro atualizado com sucesso. " + c.getId();
		
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable("id") Long id) {
		service.delete( id);
		return "Carro deletado com sucesso. " + id;
		
	}

}
