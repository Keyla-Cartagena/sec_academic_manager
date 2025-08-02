package com.college.auth_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre del rol debe tener entre 2 y 50 caracteres")
    private String name_role;

    @Column(length = 200)
    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    private String description;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<User> users = new HashSet<>();

    // Método para agregar usuario al rol
    public void addUser(User user) {
        users.add(user);
        user.setRole(this);
    }

    // Método para remover usuario del rol
    public void removeUser(User user) {
        users.remove(user);
        user.setRole(null);
    }

    // Método auxiliar para verificar si el rol está activo
    public boolean isActive() {
        return this.active != null && this.active;
    }



}
