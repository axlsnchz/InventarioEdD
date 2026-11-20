package mx.edu.utez.inventarioEdD.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código SKU es obligatorio")
    @Column(name = "codigo_sku", unique = true, nullable = false, length = 30)
    private String codigoSku;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 60)
    private String categoria;

    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private Double precio;

    @Column(name = "imagen_url", length = 300)
    private String imagenUrl;

    @NotNull(message = "El stock actual es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Column(name = "stock_minimo", nullable = false)
    private Integer stockMinimo;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
    }



    public String getEstadoStock() {
        if (stockActual == null || stockActual == 0) {
            return "Agotado";
        } else if (stockActual <= stockMinimo) {
            return "Stock bajo";
        }
        return "Disponible";
    }
    public String getClaseEstado() {
        if (stockActual == null || stockActual == 0) return "out";
        if (stockActual <= stockMinimo) return "low";
        return "ok";
    }

    public Double getValorEnInventario() {
        if (precio == null || stockActual == null) return 0.0;
        return precio * stockActual;
    }

    public String getInicial() {
        if (nombre == null || nombre.isBlank()) return "?";
        return nombre.substring(0, 1).toUpperCase();
    }
}