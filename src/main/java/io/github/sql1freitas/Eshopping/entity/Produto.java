package io.github.sql1freitas.Eshopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    @JsonIgnore
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @JsonIgnore
    private Categoria categoria;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column
    @CreatedDate
    private LocalDateTime dataEntradaEstoque;

    @Column
    private Boolean habilitar = true;



}
