package br.com.casamagalhaes.workshop.desafio.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.casamagalhaes.workshop.desafio.model.Item;
import br.com.casamagalhaes.workshop.desafio.model.Pedido;

import br.com.casamagalhaes.workshop.desafio.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    /*
    * Entrada: 
    * Saida: 
    * Descricao: Pega um pedido do pago pelo numero do pedido/Id
    */ 
    public Pedido getPedido(Long pedido){
        return repository.findById(pedido).orElseThrow(EntityNotFoundException::new);
    }

    /*
    * Entrada: 
    * Saida: 
    * Descricao:
    */ 
    public Pedido atualizarValores(Pedido pedido){
        Double valorTotalProdutos = 0.0;
        for (Item i : pedido.getItens()){
            valorTotalProdutos += (i.getPrecoUnitario() * i.getQuantidade());
        }
        pedido.setValorTotalProdutos(valorTotalProdutos);
        pedido.setValorTotal(valorTotalProdutos + pedido.getTaxa());

        return pedido;
    }

    /*
    * Entrada: 
    * Saida: 
    * Descricao: Adiciona um novo pedido ao banco;
    */ 
    public Pedido addPedido(Pedido pedido) {
        pedido = atualizarValores(pedido);
        return repository.saveAndFlush(pedido);
    }

    /*
    * Entrada: 
    * Saida: 
    * Descricao: Adiciona um item ao pedido no banco;
    */ 
    public Pedido addItem(Long numPedido, Item item){
        if (repository.existsById(numPedido)){
            Pedido p = getPedido(numPedido);
            p.getItens().add(item);
            p = atualizarValores(p);
            if (numPedido.equals(p.getPedido()))
                return repository.saveAndFlush(p);
            else
                throw new UnsupportedOperationException("Numero do pedido informado diferente do pedido.");
        }else
            throw new EntityNotFoundException("Numero do pedido: " + numPedido);
    }

    /*
    * Entrada: 
    * Saida: 
    * Descricao:
    */ 
    public void deletePedido(Long numPedido){
        repository.deleteById(numPedido);
    }


    /*
    * Entrada: 
    * Saida: 
    * Descricao:
    */ 
    public String statusChange(Long numPedido, String status){
        if (repository.existsById(numPedido)){
            Pedido p = repository.findById(numPedido).orElseThrow(EntityNotFoundException::new);
            String s = p.getStatus();

            if (numPedido.equals(p.getPedido())){
                if(status.equals("CANCELADO") && (!s.equals("PRONTO") || !s.equals("PENDENTE"))){
                   throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else if(status.equals("EM_ROTA") && !s.equals("PRONTO")){
                    throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else if(status.equals("ENTREGUE") && !s.equals("EM_ROTA")){
                    throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else{
                    p.setStatus(status);
                    repository.saveAndFlush(p);
                    return p.getStatus();
                }
            }else
                throw new UnsupportedOperationException("Numero do pedido informado diferente do pedido.");
        }else
            throw new EntityNotFoundException("Numero do pedido: " + numPedido);
    }
}


