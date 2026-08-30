package com.company;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final List<ItemPedido> listaPedidos = new CopyOnWriteArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public PedidoController() {
        listaPedidos.add(new ItemPedido(contadorId.getAndIncrement(), "Caderno","Caderno de 100 folhas", 2, 15.50));
        listaPedidos.add(new ItemPedido(contadorId.getAndIncrement(), "Caneta","Caneta esferográfica", 5, 2.50));
    }

    @GetMapping
    public List<ItemPedido> listar() {
        return listaPedidos;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedido> buscarPorId(@PathVariable Long id) {
        return listaPedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemPedido> adicionar(@RequestBody ItemPedido novoItem) {
        novoItem.setId(contadorId.getAndIncrement());
        listaPedidos.add(novoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemPedido> atualizar(@PathVariable Long id, @RequestBody ItemPedido dadosAtualizados) {
        for (int i = 0; i < listaPedidos.size(); i++) {
            ItemPedido p = listaPedidos.get(i);
            if (p.getId().equals(id)) {
                dadosAtualizados.setId(id);
                listaPedidos.set(i, dadosAtualizados);
                return ResponseEntity.ok(dadosAtualizados);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        boolean removido = listaPedidos.removeIf(p -> p.getId().equals(id));
        return removido ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}