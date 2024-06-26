package br.FPJAuto.service;

import br.FPJAuto.repository.ItemVendaRepository;
import br.FPJAuto.model.ItemVenda;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;

    public ItemVendaService(ItemVendaRepository itemVendaRepository){
        this.itemVendaRepository = itemVendaRepository;
    }

    public List<ItemVenda> getAll(){
        return this.itemVendaRepository.findAll();
    }

    public ItemVenda save(ItemVenda itemVenda){
        return this.itemVendaRepository.save(itemVenda);
    }

    public Optional<ItemVenda> getById(int id){
        return this.itemVendaRepository.findById(id);
    }

    public void deleteById(int id){
        this.itemVendaRepository.deleteById(id);
    }

}
