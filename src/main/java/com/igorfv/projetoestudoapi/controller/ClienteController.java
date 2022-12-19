package com.igorfv.projetoestudoapi.controller;

import com.igorfv.projetoestudoapi.model.Cliente;
import com.igorfv.projetoestudoapi.repository.ClienteRepository;
import com.igorfv.projetoestudoapi.service.CatalogoClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> Listar() {
        return clienteRepository.findAll();
    }

    @GetMapping("/{clienteid}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clienteid) {
        return clienteRepository.findById(clienteid)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/{clienteid}")
    public ResponseEntity<Cliente> buscar(@Valid @PathVariable Long clienteid, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(clienteid)) {
            return ResponseEntity.notFound().build();
        }

        cliente.setId(clienteid);
        cliente = catalogoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clienteid}")
    public ResponseEntity<Void> exluir(@PathVariable Long clienteid) {
        if (!clienteRepository.existsById(clienteid)) {
            return ResponseEntity.notFound().build();
        }

        catalogoClienteService.deletar(clienteid);

        return ResponseEntity.noContent().build();
    }
}
