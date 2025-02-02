package pet.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PetStoreService {
	@Autowired
	private PetStoreDao petStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private CustomerDao customerDao;
	
//	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
	}
	
	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		petStore.setPetStoreId(petStoreData.getPetStoreId());
		petStore.setPetStoreName(petStoreData.getPetStoreName());
		petStore.setPetStorePhone(petStoreData.getPetStorePhone());
		petStore.setPetStoreState(petStoreData.getPetStoreState());
		petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	}
	
	private PetStore findOrCreatePetStore(Long petStoreId) {
	    if (Objects.isNull(petStoreId)) {
	        return new PetStore();  
	    } else {
	    	return findPetStoreById(petStoreId);
	    }
	}

	
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException(
						"Pet Store with ID =" + petStoreId + " was not found."));
	}
	
	//===== added week 15 =====
	
	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
	    employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
	    employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
	    employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
	    employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
	    employee.setEmployeeId(petStoreEmployee.getEmployeeId());
	}
	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
	    customer.setCustomerId(petStoreCustomer.getCustomerId());
	    customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
	    customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	    customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
	}
	
	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
		if(Objects.isNull(employeeId)) {
			return new Employee();
		}
		
		return findEmployeeById(petStoreId, employeeId);
	}
	
	private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	    Customer customer;

	    if (Objects.isNull(customerId)) {
	        customer = new Customer();
	    } else {
	        customer = findCustomerById(petStoreId, customerId);
	    }
	    
	    if (customer.getPetStores() == null) {
	        customer.setPetStores(new HashSet<>());
	    }

	    return customer;
	}
	
	private Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException(
			    "Employee with ID =" + employeeId + " was not able to be found."));
		if(employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException("Employee with Id= " + employeeId + " is not an employee at the store with ID = " + petStoreId + ".");
		}
		return employee;
	}
	
	private Customer findCustomerById(Long petStoreId, Long customerId) {
		Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException(
				"Customer with ID = " + customerId + " was not able to be found."));
		
		boolean found = false;
		
		for(PetStore petStore : customer.getPetStores()) {
			if(petStore.getPetStoreId() == petStoreId) {
				found = true;
				break;
			}
		}
		
		if(!found) {
			throw new IllegalArgumentException("The Customer with ID = " + customerId + " is not a member of the store with ID = " + petStoreId); 
		}
		return customer;
	}

	//@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = petStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new PetStoreEmployee(dbEmployee);
		
	}
	
	@Transactional
	public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long customerId = petStoreCustomer.getCustomerId();
		Customer customer = findOrCreateCustomer(petStoreId, customerId);
		
		copyCustomerFields(customer, petStoreCustomer);
		
		customer.getPetStores().add(petStore);
		petStore.getCustomers().add(customer);
		
		Customer dbCustomer = customerDao.save(customer);
		
		return new PetStoreCustomer(dbCustomer);
	}
	
	@Transactional
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petStores) {
			PetStoreData psd = new PetStoreData(petStore);
			
			psd.getCustomers().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		return result;
	}
	
	@Transactional
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		return new PetStoreData(findPetStoreById(petStoreId));
	}
	
	@Transactional
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
}