package br.com.casamagalhaes.workshop.desafio.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.casamagalhaes.workshop.desafio.model.Item;
import br.com.casamagalhaes.workshop.desafio.model.Pedido;
import br.com.casamagalhaes.workshop.desafio.model.Status;
import br.com.casamagalhaes.workshop.desafio.service.PedidoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pedido")
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService servicePedido;

      /*
    * Entrada: Recebe um ID de pedido e o novo Status para o pedido;
    * Saida: O retorno é uma string com o novo STATUS; 
    * Descricao: Muda o STATUS do pedido;
    */ 
    @PostMapping("/{id}/status")
    @ResponseStatus(code = HttpStatus.OK)
    public String changeStatus(@PathVariable Long id, @Valid @RequestBody Status status){
        return servicePedido.statusChange(id,status.getStatus());
    }

      /*
    * Entrada: Recebe um ID de um pedido;
    * Saida: Devolve um Pedido;
    * Descricao: Busca um pedido no banco e o retorna;
    */ 
    @GetMapping(path = {"/{id}"})
    public Pedido getPedido(@PathVariable Long id){
        return servicePedido.getPedido(id);
    }
    
    @GetMapping(path = {"/",""})
    public List<Pedido> getPedidoAll(){
        return servicePedido.getPedidoAll();
    }
    
      /*
    * Entrada: Recebe do Body da requisicao um pedido;
    * Saida: Devolve o pedido que foi inserido no banco;
    * Descricao: Adiciona um pedido ao banco;
    */ 
    @PostMapping({"/",""})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido addPedido(@Valid @RequestBody Pedido pedido){
        return servicePedido.addPedido(pedido);
    }

    /*
    * Entrada: Recebe um ID do pedido e um ITEM do body da requisicao;
    * Saida: Devolve o pedido;
    * Descricao: Adiciona um item a um pedido e retona-lo;
    */ 
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Pedido addItem(@Valid @RequestBody Item pedido, @PathVariable Long id){
        return servicePedido.addItem(id, pedido);
    }

    /*
    * Entrada: Recebe o ID de um pedido;
    * Saida: ;
    * Descricao: Deleta um pedido;
    */ 
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePedido(@PathVariable Long id){
        servicePedido.deletePedido(id);
    }

}
