package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Customer;
import kh.edu.cstad.mbapi.dto.CreateCustomerRequest;
import kh.edu.cstad.mbapi.dto.CustomerResponse;
import kh.edu.cstad.mbapi.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toCustomerPartially(
            UpdateCustomerRequest updateCustomerRequest,
            @MappingTarget Customer customer
    );

    // DTO -> Model
    // Model -> DTO
    // What is source data? (parameter)
    // What is target data? (return_type)
    CustomerResponse toCustomerResponse(Customer customer);

    @Mapping(target = "customerSegment", ignore = true)
    Customer fromCreateCustomerRequest(CreateCustomerRequest createCustomerRequest);

}
