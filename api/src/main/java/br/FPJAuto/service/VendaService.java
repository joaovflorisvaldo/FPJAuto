package br.FPJAuto.service;

import br.FPJAuto.model.Venda;
import br.FPJAuto.repository.VendaRepository;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository){
        this.vendaRepository = vendaRepository;
    }

    public List<Venda> getAll(){
        return this.vendaRepository.findAll();
    }

    public Venda save(Venda venda){
        return this.vendaRepository.save(venda);
    }

    public Optional<Venda> getById(int id){
        return this.vendaRepository.findById(id);
    }

    public void deleteById(int id){
        this.vendaRepository.deleteById(id);
    }

    public List<Venda> vendaFilter(Date data) {
        return vendaRepository.findByData(data);
    }


}
