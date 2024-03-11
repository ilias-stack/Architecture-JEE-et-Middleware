package com.example.tp3.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty @Size(min = 4)
    private String nom;
    @DateTimeFormat(pattern = "yyyy-MM-dd") @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private boolean malade;
    @DecimalMax("100")
    private int socre;

}
