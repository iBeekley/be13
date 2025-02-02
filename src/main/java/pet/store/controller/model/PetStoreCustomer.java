package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer; // Ensure you import Customer entity
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreCustomer {
	
	@ManyToMany
	private Set<PetStore> petStores = new HashSet<>(); // initializes

    private Long customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;

    // Constructor to map from Customer entity to PetStoreCustomer DTO
    public PetStoreCustomer(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.customerFirstName = customer.getCustomerFirstName();
        this.customerLastName = customer.getCustomerLastName();
        this.customerEmail = customer.getCustomerEmail();
    }
}
