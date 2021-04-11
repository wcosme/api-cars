package com.carros.controllers;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<CarroDTO> post(@RequestBody Carro carro) {
		
		try {
			CarroDTO dto = service.insert(carro);
			
			URI location = getURI(dto.getId());
			return ResponseEntity.created(location).build();
		}catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CarroDTO> put(@PathVariable("id") Long id, @RequestBody Carro carro) {
		CarroDTO c = service.update(carro, id);
		return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CarroDTO> delete(@PathVariable("id") Long id) {
		boolean ok = service.delete( id);
		
		return ok ?
				ResponseEntity.ok().build() : 
				ResponseEntity.notFound().build();
		
	}
	
	private URI getURI(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
