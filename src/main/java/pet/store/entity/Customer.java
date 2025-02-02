package pet.store.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    @ManyToMany(mappedBy = "customers")
    private Set<PetStore> petStores;
}
