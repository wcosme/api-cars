package com.carros.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.carros.ApiCarrosApplication;
import com.carros.domain.Carro;
import com.carros.domain.dto.CarroDTO;
import com.carros.service.CarroService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiCarrosApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrosAPITest {
	
	@Autowired
    protected TestRestTemplate rest;

    @Autowired
    private CarroService service;

    private ResponseEntity<CarroDTO> getCarro(String url) {
        return
                rest.withBasicAuth("user","123").getForEntity(url, CarroDTO.class);
    }
	
	@Test
    public void testSave() {

        Carro carro = new Carro();
        carro.setNome("Porshe");
        carro.setTipo("esportivos");

        // Insert
        ResponseEntity response = rest.withBasicAuth("admin","123").postForEntity("/api/v1/carros", carro, null);
        System.out.println(response);

        // Verifica se criou
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Buscar o objeto
        String location = response.getHeaders().get("location").get(0);
        CarroDTO c = getCarro(location).getBody();

        assertNotNull(c);
        assertEquals("Porshe", c.getNome());
        assertEquals("esportivos", c.getTipo());

        // Deletar o objeto
        rest.withBasicAuth("user","123").delete(location);

        // Verificar se deletou
        assertEquals(HttpStatus.NOT_FOUND, getCarro(location).getStatusCode());
    }

}
