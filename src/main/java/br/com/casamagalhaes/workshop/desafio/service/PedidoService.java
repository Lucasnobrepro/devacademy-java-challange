package br.com.casamagalhaes.workshop.desafio.service;

import java.util.List;

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

    public List<Pedido> getPedidoAll(){
        return repository.findAll();
    }

    /*
    * Entrada: Recebe um pedido;
    * Saida: Devolve o pedido com os valores atualizados;
    * Descricao: Atualizar os valores
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
    * Entrada: Recebe um pedido;
    * Saida: Devolve o pedido que foi adicionado;
    * Descricao: Adiciona um novo item a um pedido;
    */ 
    public Pedido addPedido(Pedido pedido) {
        pedido.setStatus("PEDENTE");
        pedido = atualizarValores(pedido);
        return repository.saveAndFlush(pedido);
    }

    /*
    * Entrada: O ID do pedido e um ITEM que sera adicionado em um pedido;
    * Saida: O pedido com o ITEM adicionado;
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
            throw new UnsupportedOperationException("Numero do pedido informado Nao existe.");
    }

    /*
    * Entrada: O ID do pedido a ser deletado;
    * Saida: ;
    * Descricao: Deleta um pedido pelo ID;
    */ 
    public void deletePedido(Long numPedido){
        repository.deleteById(numPedido);
    }


    /*
    * Entrada: Recebe o ID do pedido para mudar o status, e recebe pelo body da requisicao o novo STATUS;
    * Saida: O STATUS atualizado
    * Descricao: Atualiza o status de um pedido pelo ID.
    */ 
    public String statusChange(Long numPedido, String status){
        if (repository.existsById(numPedido)){
            Pedido pedidoExistente = repository.findById(numPedido).orElseThrow(EntityNotFoundException::new);
            String statusPedido = pedidoExistente.getStatus();

            if (numPedido.equals(pedidoExistente.getPedido())){
                if(status.equals("CANCELADO") && (!statusPedido.equals("PRONTO") || !statusPedido.equals("PENDENTE"))){
                   throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else if(status.equals("EM_ROTA") && !statusPedido.equals("PRONTO")){
                    throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else if(status.equals("ENTREGUE") && !statusPedido.equals("EM_ROTA")){
                    throw new UnsupportedOperationException("status não pode ser alterado....");
    
                }else{
                    pedidoExistente.setStatus(status);
                    repository.saveAndFlush(pedidoExistente);
                    return pedidoExistente.getStatus();
                }
            }else
                throw new UnsupportedOperationException("Numero do pedido informado diferente do pedido.");
        }else
            throw new EntityNotFoundException("Numero do pedido: " + numPedido);
    }
}


