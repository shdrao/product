package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest {

	@InjectMocks
	private ProductController productController;

	MockMvc mockMvc;
	
	@Mock
	private ProductService productService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	@Test
	public void testAddProductWhichReturnsProduct() throws Exception {
		Product product = new Product(12346, "Lenovo 6", "computer", 10000);
	
		when(productService.addProduct(Mockito.isA(Product.class)))
		.thenReturn(product);
		
		
		mockMvc.perform(post("/product").contentType(MediaType.APPLICATION_JSON)
        
		.content("{\r\n" + 
				"    \"productId\": 12346,\r\n" + 
				"    \"productName\": \"Lenovo 6\",\r\n" + 
				"    \"productCategory\": \"computer\",\r\n" + 
				"    \"productPrice\": 10000\r\n" + 
				"}")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
	  .andExpect(jsonPath("$.productId").exists())
      .andExpect(jsonPath("$.productName").exists())
      .andExpect(jsonPath("$.productCategory").exists())
      .andExpect(jsonPath("$.productPrice").exists())
      .andExpect(jsonPath("$.productId").value(12346))
      .andExpect(jsonPath("$.productName").value("Lenovo 6"))
      .andExpect(jsonPath("$.productCategory").value("computer"))
      .andExpect(jsonPath("$.productPrice").value(10000)).andDo(print());
      	
				
	}
}
