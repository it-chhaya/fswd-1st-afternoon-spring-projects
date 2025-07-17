package kh.edu.cstad.mbapi.service.impl;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.domain.CustomerSegment;
import kh.edu.cstad.mbapi.domain.KYC;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import kh.edu.cstad.mbapi.mapper.CustomerMapper;
import kh.edu.cstad.mbapi.repository.CustomerRepository;
import kh.edu.cstad.mbapi.repository.CustomerSegmentRepository;
import kh.edu.cstad.mbapi.repository.KYCRepository;
import kh.edu.cstad.mbapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final KYCRepository kycRepository;
    private final CustomerSegmentRepository customerSegmentRepository;

    @Override
    public CustomerResponse createNew(CreateCustomerRequest createCustomerRequest) {

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Phone number already exists"
            );
        }

        // Validation national card ID for creating KYC
        if (kycRepository.existsByNationalCardId(createCustomerRequest.nationalCardId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "National card ID already exists"
            );
        }

        // Validation customer segment
        CustomerSegment customerSegment = customerSegmentRepository
                .findBySegment(createCustomerRequest.customerSegment())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Customer segment not found"));

        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setCustomerSegment(customerSegment);
        customer.setIsDeleted(false);

        // Prepare KYC of customer
        KYC kyc = new KYC();
        kyc.setCustomer(customer);
        kyc.setNationalCardId(createCustomerRequest.nationalCardId());
        kyc.setIsVerified(false);
        kyc.setIsDeleted(false);
        customer.setKyc(kyc);

        log.info("Customer ID before save: {}", customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer ID after save: {}", customer.getId());

        return customerMapper.toCustomerResponse(customer);
    }


    @Transactional
    @Override
    public void disableByPhoneNumber(String phoneNumber) {

        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number not found");
        }

        customerRepository.disableByPhoneNumber(phoneNumber);
    }

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber,
                                                UpdateCustomerRequest updateCustomerRequest) {

        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Customer phone number not found."
                        ));

        customerMapper.toCustomerPartially(
                updateCustomerRequest, customer
        );
        customer = customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findByPhoneNumberAndIsDeletedFalse(phoneNumber)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number doesn't exist")
                );
    }


    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAllByIsDeletedFalse();
        return customers
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

}
