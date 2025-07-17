package kh.edu.cstad.mbapi.dto;

import java.time.LocalDate;

public record CreateCustomerRequest(
        String fullName,
        String gender,
        LocalDate dob,
        String email,
        String phoneNumber,
        String remark,
        String nationalCardId,
        String customerSegment
) {
}
