package kh.edu.cstad.mbapi.dto;

import lombok.Builder;

@Builder
public record RegisterResponse(
        String userId,
        String username,
        String email,
        String firstName,
        String lastName
) {
}
