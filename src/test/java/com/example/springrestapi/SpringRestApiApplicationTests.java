package com.example.springrestapi;

import com.example.springrestapi.products.Product;
import com.example.springrestapi.products.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

//REST API testen
//webEnvironment Definded_Port das heißt, dass uns SpringBootTest unseren ApllicationContext startet
//und dabei unser WebEnvironment also unseren Controller, unseren DispatcherServlet etc. bereit stellen wird
//auf den von uns definierten Port. In dem Fall den Tomcat Port 8080
//Das @RunWith ist für JUnit (Testframework)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SpringRestApiApplicationTests {

	@Autowired
	private ProductRepository productRepository;

	private static final String productName = "Shoe";

	@BeforeEach
	public void addProductToDb() {
		Product product = new Product();
		product.setName(productName);
		product.setCostInEuro(23);

		productRepository.save(product);
	}

	@AfterEach
	public void clearProductDb() {
		productRepository.deleteAll();
	}

	@Test
	void testGetRequest() {
		TestRestTemplate restTemplate = new TestRestTemplate();

		ResponseEntity<Product> productEntity
				= restTemplate.getForEntity("http://localhost:8080/products/1", Product.class);
		Assert.assertEquals(productEntity.getStatusCode(), HttpStatus.OK);
		Assert.assertNotNull(productEntity.getBody());
		Assert.assertEquals(productEntity.getBody().getName(), productName);
	}

}
