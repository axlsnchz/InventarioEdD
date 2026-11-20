package mx.edu.utez.inventarioEdD.controller;

import jakarta.validation.Valid;
import mx.edu.utez.inventarioEdD.model.Producto;
import mx.edu.utez.inventarioEdD.service.MyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }


    @GetMapping("/")
    public String inicio(Model m) {
        List<Producto> todos = service.listar();
        List<Producto> bajos = todos.stream().filter(p -> !p.getClaseEstado().equals("ok")).toList();
        double valor = todos.stream().mapToDouble(Producto::getValorEnInventario).sum();

        m.addAttribute("valorTotal", valor);
        m.addAttribute("totalProductos", todos.size());
        m.addAttribute("totalBajos", bajos.size());
        m.addAttribute("reposicion", bajos);
        m.addAttribute("recientes", todos.stream().skip(Math.max(0, todos.size() - 5)).toList());
        return "index";
    }

    @GetMapping("/productos")
    public String listar(Model m) {
        List<Producto> productos = service.listar();
        m.addAttribute("productos", productos);
        m.addAttribute("total", productos.size());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo(Model m) {
        m.addAttribute("producto", new Producto());
        return "formulario";
    }

    @PostMapping("/productos")
    public String guardar(@Valid @ModelAttribute("producto") Producto producto, BindingResult br) {
        if (service.skuRepetido(producto.getCodigoSku())) {
            br.rejectValue("codigoSku", "dup", "Escribe un código que no esté repetido.");
        }
        if (br.hasErrors()) return "formulario";

        service.registrar(producto);   // entra a la cola → BD
        return "redirect:/productos";
    }

    @GetMapping("/productos/{id}")
    public String detalle(@PathVariable Long id, Model m) {
        Producto p = service.buscarPorId(id);
        if (p == null) return "redirect:/productos";
        m.addAttribute("p", p);
        return "detalle";
    }
}


