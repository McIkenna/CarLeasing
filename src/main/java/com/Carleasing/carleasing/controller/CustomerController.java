package com.Carleasing.carleasing.controller;

import com.Carleasing.carleasing.model.Customer;
import com.Carleasing.carleasing.service.CustomerService;
import com.Carleasing.carleasing.service.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("/saveCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer, BindingResult result){
        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;
       Customer cus = customerService.saveCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public Customer findCustomer(@PathVariable String customerId){
        return customerService.findCustomerById(customerId);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable String customerId){
        return customerService.deleteCustomer(customerId);
    }

    @PutMapping("/updateCustomer")
    public String updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }
}
