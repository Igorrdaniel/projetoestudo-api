package com.igorfv.projetoestudoapi.service;

import com.igorfv.projetoestudoapi.exception.NegocioException;
import com.igorfv.projetoestudoapi.model.Cliente;
import com.igorfv.projetoestudoapi.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class CatalogoClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Long clienteid){
       return  clienteRepository.findById(clienteid)
                .orElseThrow(() -> new NegocioException("Cliente não encontrado"));
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        boolean emailEmUso = clienteRepository.findByemail(cliente.getEmail())
                .stream()
                .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));

        if (emailEmUso) {
            throw new NegocioException("Já existe um cliente cadastrado com esse e-mail");
        }

        return clienteRepository.save(cliente);

    }

    @Transactional
    public void deletar(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }
}
