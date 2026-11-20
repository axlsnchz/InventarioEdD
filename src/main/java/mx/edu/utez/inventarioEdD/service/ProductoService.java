package mx.edu.utez.inventarioEdD.service;

import mx.edu.utez.inventarioEdD.model.Producto;
import mx.edu.utez.inventarioEdD.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listarTodos() {
        return repository.findAll();
    }

    public double valorTotalInventario() {
        return repository.findAll().stream()
                .mapToDouble(Producto::getValorEnInventario)
                .sum();
    }

    public List<Producto> necesitanReposicion() {
        return repository.findAll().stream()
                .filter(p -> !"Disponible".equals(p.getEstadoStock()))
                .sorted(Comparator.comparing(Producto::getStockActual))
                .toList();
    }

    public List<Producto> agregadosRecientemente() {
        return repository.findAll().stream()
                .sorted(Comparator.comparing(Producto::getFechaRegistro)
                        .thenComparing(Producto::getId).reversed())
                .limit(3)
                .toList();
    }
}