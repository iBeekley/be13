package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee; // Ensure you import Employee entity

@Data
@NoArgsConstructor
public class PetStoreEmployee {

    private Long employeeId;
    private String employeeFirstName;
    private String employeeLastName;
    private String employeePhone;
    private String employeeJobTitle;

    // Constructor to map from Employee entity to PetStoreEmployee DTO
    public PetStoreEmployee(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.employeeFirstName = employee.getEmployeeFirstName();
        this.employeeLastName = employee.getEmployeeLastName();
        this.employeePhone = employee.getEmployeePhone();
        this.employeeJobTitle = employee.getEmployeeJobTitle();
    }
}
