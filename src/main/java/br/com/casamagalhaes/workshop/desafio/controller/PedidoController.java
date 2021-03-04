package br.com.casamagalhaes.workshop.desafio.controller;

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


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired
    private PedidoService servicePedido;


    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id, @Valid @RequestBody Status status){
        return servicePedido.statusChange(id,status.getStatus());
    }

    // Funciona
    @GetMapping(path = {"/{id}"})
    public Pedido getPedido(@PathVariable Long id){
        return servicePedido.getPedido(id);
    }
    
    //Funciona
    @PostMapping({"/",""})
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pedido addPedido(@Valid @RequestBody Pedido pedido){
        pedido.setStatus("PEDENTE");
        return servicePedido.addPedido(pedido);
    }

    // Funcionando
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Pedido addItem(@Valid @RequestBody Item pedido, @PathVariable Long id){
        return servicePedido.addItem(id, pedido);
    }

    // Funcionando
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletePedido(@PathVariable Long id){
        servicePedido.deletePedido(id);
    }

}
