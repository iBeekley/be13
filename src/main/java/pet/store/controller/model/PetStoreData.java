package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;
import pet.store.entity.Customer; // Ensure you import Customer and Employee entities
import pet.store.entity.Employee;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class PetStoreData {

    private Long petStoreId;
    private String petStoreName;
    private String petStoreAddress;
    private String petStoreCity;
    private String petStoreState;
    private String petStoreZip;
    private String petStorePhone;
    private Set<PetStoreCustomer> customers = new HashSet<>();
    private Set<PetStoreEmployee> employees = new HashSet<>();

    // Constructor to map from PetStore entity to PetStoreData DTO
    public PetStoreData(PetStore petStore) {
        this.petStoreId = petStore.getPetStoreId();
        this.petStoreName = petStore.getPetStoreName();
        this.petStoreAddress = petStore.getPetStoreAddress();
        this.petStoreCity = petStore.getPetStoreCity();
        this.petStoreState = petStore.getPetStoreState();
        this.petStoreZip = petStore.getPetStoreZip();
        this.petStorePhone = petStore.getPetStorePhone();

        // Map customers to PetStoreCustomer DTOs
        for (Customer customer : petStore.getCustomers()) {
            this.customers.add(new PetStoreCustomer(customer));
        }

        // Map employees to PetStoreEmployee DTOs
        for (Employee employee : petStore.getEmployees()) {
            this.employees.add(new PetStoreEmployee(employee));
        }
    }
    
    @Data
    @NoArgsConstructor
    public static class PetStoreCustomer {
    	private Long customerId;
    	private String customerFirstName;
    	private String customerLastName;
    	private String customerEmail;
    	
    	public PetStoreCustomer(Customer customer) {
    		customerId = customer.getCustomerId();
    		customerFirstName = customer.getCustomerFirstName();
    		customerLastName = customer.getCustomerLastName();
    		customerEmail = customer.getCustomerEmail();
    		
    	}
    }
    
    @Data
    @NoArgsConstructor
    public static class PetStoreEmployee {
    	private Long employeeId;
    	private String employeeFirstName ;
    	private String employeeLastName ;
    	private String employeePhone ;
    	private String employeeJobTitle ;
    	
    	public PetStoreEmployee (Employee employee) {
    		employeeId = employee.getEmployeeId();
    		employeeFirstName = employee.getEmployeeFirstName();
    		employeeLastName = employee.getEmployeeLastName();
    		employeePhone = employee.getEmployeePhone();
    		employeeJobTitle = employee.getEmployeeJobTitle();
    	}


    }
    
}
