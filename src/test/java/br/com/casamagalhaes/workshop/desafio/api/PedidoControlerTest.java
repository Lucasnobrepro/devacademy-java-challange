package br.com.casamagalhaes.workshop.desafio.api;

import org.springframework.boot.test.context.SpringBootTest;

import br.com.casamagalhaes.workshop.desafio.model.Item;
import br.com.casamagalhaes.workshop.desafio.model.Pedido;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;



@SpringBootTest 
public class PedidoControlerTest {
    @Value("${server.port}")
    private int porta;

    private RequestSpecification requisicao;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    private void preperarRequisicao(){
       requisicao = new RequestSpecBuilder()
          .setBasePath("/pedidos")
          .setPort(porta)
          .setAccept(ContentType.JSON)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();
    }

    @Test
    public void deveriaReceberMensagemDeOk(){
        given()
         .spec(requisicao)
       .expect()   
         .statusCode(HttpStatus.SC_OK)  
       .when()  
         .get();
    }

    @Test
    public void deveriaDeletarPedido() throws JsonProcessingException{
      Pedido pedidoCadastrado = dadoUmPedido();
        given()
          .spec(requisicao) 
        .when()  
          .delete("/{pedido}",pedidoCadastrado.getPedido())
        .then()
          .statusCode(HttpStatus.SC_NO_CONTENT);
    }


  //   @Test
  //    public void deveriaCriarUmProduto() throws JsonProcessingException {
  //       Pedido pedidoCadastrado  =
  //       given()
  //         .spec(requisicao)
  //         .body(objectMapper.writeValueAsString(dadoUmPedido()))
  //       .when()  
  //         .post()
  //       .then()
  //         .statusCode(HttpStatus.SC_CREATED)
  //       .extract()
  //         .as(Pedido.class);    
        
  //       assertNotNull(pedidoCadastrado, "produto não foi cadastrado");    
  //       assertNotNull(pedidoCadastrado.getPedido(), "id do produto não gerado");       
  //    }


    private Pedido dadoUmPedido(){
      
      Pedido pedido = new Pedido();
      Item item = new Item();
      item.setId((long) 1);
      item.setDescricao("coca");
      item.setPrecoUnitario(5.0);
      item.setQuantidade(6);
      
      List<Item> i = new ArrayList<Item>();
      i.add(item);
      long id = 1;
      pedido.setPedido(id); 
      pedido.setItens(i);
      pedido.setEndereco("minhacasa");
      pedido.setStatus("PEDENTE");
      pedido.setTaxa(5.5);
      pedido.setNomeCliente("Lucas");

      return pedido;
   }
    
}
