package kh.edu.cstad.mbapi.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
