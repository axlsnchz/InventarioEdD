package mx.edu.utez.inventarioEdD.controller;

import mx.edu.utez.inventarioEdD.model.Producto;
import mx.edu.utez.inventarioEdD.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        List<Producto> reposicion = service.necesitanReposicion();

        model.addAttribute("valorTotal", service.valorTotalInventario());
        model.addAttribute("totalProductos", service.listarTodos().size());
        model.addAttribute("totalBajos", reposicion.size());
        model.addAttribute("reposicion", reposicion);
        model.addAttribute("recientes", service.agregadosRecientemente());
        return "index";
    }
    @GetMapping("/productos")
    public String productos(Model model) {
        List<Producto> productos = service.listarTodos();
        model.addAttribute("productos", productos);
        model.addAttribute("total", productos.size());
        return "productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo() {
        return "formulario";
    }
}