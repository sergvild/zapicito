package com.ruso.zapicito.service;

import com.ruso.zapicito.dto.CustomerDto;
import com.ruso.zapicito.dto.RoleType;
import com.ruso.zapicito.entity.*;
import com.ruso.zapicito.exception.ZapicitoException;
import com.ruso.zapicito.mapper.CustomerMapper;
import com.ruso.zapicito.repository.CustomerRepository;
import com.ruso.zapicito.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final CustomerMapper customerMapper;
    private final CompanyService companyService;

    @Transactional
    public Customer saveCustomer(CustomerDto customerDto, Long companyId) throws ZapicitoException {
        if (isPhoneExists(customerDto.getPhone())) {
            throw new ZapicitoException("Phone number already exists");
        }

        Customer customer = customerMapper.fromDto(customerDto);
        customer.setUuid(UUID.randomUUID().toString());

        setCustomerRole(customer);
        setCustomerCompany(customer, companyId);

        return customerRepository.save(customer);
    }

    public List<Customer> findCustomersByCompanyId(Long companyId) {
        return customerRepository.findAllByCompanyId(companyId);
    }

    public Customer findCustomerById(Long id) throws ZapicitoException {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ZapicitoException("Couldn't find customer by ID=" + id));
    }

    public Optional<Customer> findCustomerByPhone(String phoneNumber) {
        return customerRepository.findByPhone(phoneNumber);
    }

    @Transactional
    public Customer updateCustomer(Long customerId, CustomerDto updatedCustomerDto) throws ZapicitoException {
        Customer savedCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ZapicitoException("Couldn't find customer by ID=" + customerId));

        customerMapper.updateFromDto(updatedCustomerDto, savedCustomer);

        return customerRepository.save(savedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private void setCustomerCompany(Customer customer, Long companyId) throws ZapicitoException {
        Company company = companyService.findCompanyById(companyId);
        customer.setCompany(company);
    }

    private void setCustomerRole(Customer customer) throws ZapicitoException {
        Role role = roleRepository.findByName(RoleType.CLIENT)
                .orElseThrow(() -> new ZapicitoException("There is no an role with that name: "
                        + RoleType.CLIENT));

        customer.setRole(role);
    }

    private boolean isPhoneExists(String phone) {
        return customerRepository
                .findByPhone(phone)
                .isPresent();
    }
}
