package kh.edu.cstad.mbapi.dto;

public record CreateCustomerRequest(
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        String remark
) {
}
