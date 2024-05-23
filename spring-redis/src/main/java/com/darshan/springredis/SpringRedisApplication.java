package com.darshan.springredis;

import com.darshan.springredis.entity.Product;
import com.darshan.springredis.repository.ProductDoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringRedisApplication {

	@Autowired
	private ProductDoa dao;

	@PostMapping
	public Product save (@RequestBody Product product){
		return dao.save(product);
	}

	@GetMapping
	public List<Product> getAllProducts() {
		return dao.findAll();
	}

	@Cacheable(key = "#id", value = "Product", unless = "#result.price >= 30000")
	@GetMapping("/{id}")
	public Product findProduct(@PathVariable int id){
		return dao.findProductById(id);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "Product")
	public String remove(@PathVariable int id){
		return dao.deleteProduct(id);
	}




	public static void main(String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

}
