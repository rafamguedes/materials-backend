package com.materials.api.service;

import com.materials.api.controller.dto.CustomerRequestDTO;
import com.materials.api.entity.Customer;
import com.materials.api.repository.CustomerRepository;
import com.materials.api.service.dto.CustomerDTO;
import com.materials.api.service.exceptions.BadRequestException;
import com.materials.api.service.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final CustomerRepository customerRepository;
  private final ModelMapper modelMapper;

  private static final String CUSTOMER_NOT_FOUND = "Customer not found";
  private static final String EMAIL_ALREADY_EXISTS = "Email already exists";

  public CustomerDTO create(CustomerRequestDTO requestDTO) {
    if (customerRepository.existsByEmail(requestDTO.getEmail())) {
      throw new BadRequestException(EMAIL_ALREADY_EXISTS);
    }

    var customer = modelMapper.map(requestDTO, Customer.class);
    var savedCustomer = customerRepository.save(customer);
    return modelMapper.map(savedCustomer, CustomerDTO.class);
  }

  public CustomerDTO getById(Long id) {
    var customer = findEntityById(id);
    return modelMapper.map(customer, CustomerDTO.class);
  }

  public Customer findEntityById(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND));
  }

  public CustomerDTO update(Long id, CustomerRequestDTO requestDTO) {
    var customer = findEntityById(id);
    customer.setName(requestDTO.getName());
    customer.setEmail(requestDTO.getEmail());
    customer.setPhone(requestDTO.getPhone());

    var updatedCustomer = customerRepository.save(customer);
    return modelMapper.map(updatedCustomer, CustomerDTO.class);
  }

  public void delete(Long id) {
    if (!customerRepository.existsById(id)) {
      throw new NotFoundException(CUSTOMER_NOT_FOUND);
    }
    customerRepository.deleteById(id);
  }
}
