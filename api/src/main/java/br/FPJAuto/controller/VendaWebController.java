package br.FPJAuto.controller;

import br.FPJAuto.model.Venda;
import br.FPJAuto.service.VendaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class VendaWebController {

    private final VendaService vendaService;

    public VendaWebController(VendaService vendaService){this.vendaService = vendaService;}

    @GetMapping(path = "/vendas")
    public String getAllVendas(Model model){
        List<Venda> vendas = vendaService.getAll();
        model.addAttribute("vendas", vendas);
        return "vendas";
    }

    @PostMapping("/filter")
    public String filterVendasByDate(@RequestParam("data") String dataStr, Model model) throws ParseException {

        // Converter a string para Date
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formatter.parse(dataStr);

        // Obter as vendas filtradas
        List<Venda> vendasFiltradas = vendaService.vendaFilter(data);
        model.addAttribute("vendas", vendasFiltradas);


        return "vendas";
    }

    @PostMapping("/retornarVendas")
    public String retornar(){ return "redirect:/vendas";}

}
