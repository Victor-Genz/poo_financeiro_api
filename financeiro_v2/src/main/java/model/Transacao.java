package model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transacao")
public class Transacao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "descricao")
    public String descricao;

    @Column(name = "valor")
    public BigDecimal valor;

    @Column(name = "tipo")
    public String tipo;

    @Column(name = "datatransacao")
    public LocalDate dataTransacao;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    public Conta conta;

    @ManyToOne
    @JoinColumn(name = "categoriaid")
    public Categoria categoria;
}