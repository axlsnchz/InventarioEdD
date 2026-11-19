package mx.edu.utez.inventarioEdD.repository;

import mx.edu.utez.inventarioEdD.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
