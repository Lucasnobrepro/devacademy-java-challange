package br.com.casamagalhaes.workshop.desafio.api;

import org.springframework.boot.test.context.SpringBootTest;

import br.com.casamagalhaes.workshop.desafio.model.Item;
import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.Status;

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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;



@SpringBootTest 
public class PedidoControlerTest {
    @Value("${server.port}")
    private int porta;

    private RequestSpecification requisicao;

    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    private void preaparandoRequisicao(){
       requisicao = new RequestSpecBuilder()
          .setBasePath("/pedidos")
          .setPort(porta)
          .setAccept(ContentType.JSON)
          .setContentType(ContentType.JSON)
          .log(LogDetail.ALL)
          .build();
    }

    @Test
    public void deveriaRecuperarTodosOSPedidos(){
        given()
         .spec(requisicao)
       .expect()   
         .statusCode(HttpStatus.SC_OK)  
       .when()  
         .get();
    }

    @Test
    public void deveriaCriarUmPedido() throws JsonProcessingException {
        Pedido pedido = given()
                .spec(requisicao)
                .body(objectMapper.writeValueAsString(dadoUmPedido()))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(Pedido.class);

        assertNotNull(pedido, "pedido não foi cadastrado");
        assertNotNull(pedido, "pedido não foi cadastrado");
    }

    @Test
    public void deveriaDeletarPedido() throws JsonProcessingException{
      Pedido pedidoCadastrado = given()
                .spec(requisicao)
                .body(objectMapper.writeValueAsString(dadoUmPedido()))
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(Pedido.class);

        assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");
        assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");

          given()
          .spec(requisicao) 
          .when()  
          .delete("/{id}",pedidoCadastrado.getPedido())
          .then()
          .statusCode(HttpStatus.SC_NO_CONTENT);


          given()
          .spec(requisicao)
          .body(objectMapper.writeValueAsString(pedidoCadastrado))
          .when()
          .get("/{id}", pedidoCadastrado.getPedido())
          .then() 
          .statusCode(HttpStatus.SC_NOT_FOUND);
    }
 
    @Test
    public void deveriaPegarPedido() throws JsonProcessingException{
          Pedido pedidoCadastrado = given()
          .spec(requisicao)
          .body(objectMapper.writeValueAsString(dadoUmPedido()))
          .when()
          .post()
          .then()
          .statusCode(HttpStatus.SC_CREATED)
          .extract()
          .as(Pedido.class);

          assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");
          assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");

          given()
          .spec(requisicao)
          .body(objectMapper.writeValueAsString(pedidoCadastrado))
          .when()
          .get("/{id}", pedidoCadastrado.getPedido())
          .then() 
          .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deveriaMudarStatus () throws JsonProcessingException{
      Pedido pedidoCadastrado = given()
          .spec(requisicao)
          .body(objectMapper.writeValueAsString(dadoUmPedido()))
          .when()
          .post()
          .then()
          .statusCode(HttpStatus.SC_CREATED)
          .extract()
          .as(Pedido.class);

          assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");
          assertNotNull(pedidoCadastrado, "pedido não foi cadastrado");
    }

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
      pedido.setTelefone((long) 8884351);
      System.out.println(pedido);
  
      return pedido;
   }

   private Status dadoUmStatus(){
     Status status = new Status();
     status.setStatus("PRONTO");
     return status;
   }
    
}
