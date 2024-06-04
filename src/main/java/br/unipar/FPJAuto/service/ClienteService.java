package br.unipar.FPJAuto.service;

import br.unipar.FPJAuto.model.Cliente;
import br.unipar.FPJAuto.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> getAll(){
        return this.clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente){
        return this.clienteRepository.save(cliente);
    }

    public Optional<Cliente> getById(int id){
        return this.clienteRepository.findById(id);
    }

    public void deleteById(int id){
        this.clienteRepository.deleteById(id);
    }

}
