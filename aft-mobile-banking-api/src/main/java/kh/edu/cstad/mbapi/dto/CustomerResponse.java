package kh.edu.cstad.mbapi.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
        String email
) {
}
