package kh.edu.cstad.mbapi.dto;

public record RegisterRequest(
        String username,
        String email,
        String password,
        String confirmedPassword,
        String firstName,
        String lastName,
        GenderOptions gender,
        String biography
) {
}
