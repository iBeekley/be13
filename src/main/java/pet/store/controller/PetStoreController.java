package pet.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RestController
@RequestMapping("/pet_store")
public class PetStoreController {
	@Autowired
    private PetStoreService petStoreService;
	
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    
    public PetStoreData insertPetStore(@RequestBody PetStoreData petStoreData) {
    	log.info("Creating pet store {}", petStoreData);
    	return petStoreService.savePetStore(petStoreData);
    }
    
    @PutMapping("/{petStoreId}")
    public PetStoreData updatePetStore(@PathVariable Long petStoreId, 
                                       @RequestBody PetStoreData petStoreData) {
        petStoreData.setPetStoreId(petStoreId);
        log.info("Updating pet store {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }
    
    //==== Added for week 15 ====
    @PostMapping("/{petStoreId}/employee")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreEmployee addEmployeeToPetStore(@PathVariable Long petStoreId,
    		@RequestBody PetStoreEmployee petStoreEmployee) {
    	log.info("Adding employee {} to pet store with ID = {}", petStoreEmployee, petStoreId);
    	
    	return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
    }
    
    @PostMapping("/{petStoreId}/customer")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PetStoreCustomer addCustomerToPetStore(@PathVariable Long petStoreId,
    		@RequestBody PetStoreCustomer petStoreCustomer) {
    	log.info("Adding customer {} to pet store with ID = {}", petStoreCustomer, petStoreId);
    	
    	return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
    }
    
    @GetMapping
    public List<PetStoreData> retrieveAllPetStores() {
    	log.info("Retrieving all pet stores...");
    	return petStoreService.retrieveAllPetStores();
    }
    
    @GetMapping("/{petStoreId}")
    public PetStoreData retrievePetStoreById(@PathVariable Long petStoreId) {
    	return petStoreService.retrievePetStoreById(petStoreId);
    }
    
    @DeleteMapping("/{petStoreId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
        log.info("Deleting pet store {}", petStoreId);
        
        petStoreService.deletePetStoreById(petStoreId);
        
        return Map.of("message", "Pet Store with Id = " + petStoreId + " deleted."); 
    }

    
}
 