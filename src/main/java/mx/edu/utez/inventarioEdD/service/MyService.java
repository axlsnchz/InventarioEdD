package mx.edu.utez.inventarioEdD.service;

import mx.edu.utez.inventarioEdD.estructuras.Cola;
import mx.edu.utez.inventarioEdD.model.Producto;
import mx.edu.utez.inventarioEdD.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {


    private final ProductoRepository repo;
    private final Cola<Producto> colaRegistro = new Cola<>();

    public MyService(ProductoRepository repo) {
        this.repo = repo;
    }

    public void registrar(Producto p) {
        colaRegistro.encolar(p);
        System.out.println("Encolado: " + p.getNombre() + ", en cola: " + colaRegistro.getTamano());

        procesarCola();
    }

    private void procesarCola() {
        while (!colaRegistro.isEmpty()) {
            Producto p = colaRegistro.peek();
            repo.save(p);
            colaRegistro.desencolar();
            System.out.println("Guardado en BD y desencolado: " + p.getNombre());

        }
    }

    public boolean skuRepetido(String sku) {
        return repo.existsByCodigoSku(sku);
    }

    public List<Producto> listar() { return repo.findAll(); }

    public Producto buscarPorId(Long id) { return repo.findById(id).orElse(null); }
}


