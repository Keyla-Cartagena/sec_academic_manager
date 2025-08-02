package com.college.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "id_document")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "^[0-9]{7,10}$", message = "La cédula debe tener entre 7 y 10 dígitos")
    private String id_document;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String name;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String last_name;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @NotNull(message = "El rol es obligatorio")
    private Role role;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creation_date;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime update_date;

    // Método auxiliar para verificar si el usuario está activo
    public boolean isActive() {
        return this.active != null && this.active;
    }

    // Método para desactivar usuario (soft delete)
    public void desactivar() {
        this.active = false;
    }

    // Método para activar usuario
    public void activar() {
        this.active = true;
    }



}
