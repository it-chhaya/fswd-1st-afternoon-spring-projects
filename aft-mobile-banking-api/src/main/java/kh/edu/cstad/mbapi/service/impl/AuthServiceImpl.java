package kh.edu.cstad.mbapi.service.impl;

import jakarta.ws.rs.core.Response;
import kh.edu.cstad.mbapi.dto.RegisterRequest;
import kh.edu.cstad.mbapi.dto.RegisterResponse;
import kh.edu.cstad.mbapi.service.AuthService;
import kh.edu.cstad.mbapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final RoleService roleService;

    @Override
    public void verifyEmail(String userId) {
        UserResource userResource = keycloak.realm("mbapi")
                .users().get(userId);
        userResource.sendVerifyEmail();
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // Validate password
        if (!registerRequest.password().equals(registerRequest.confirmedPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Passwords don't match");
        }

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(registerRequest.username());
        userRepresentation.setEmail(registerRequest.email());
        userRepresentation.setFirstName(registerRequest.firstName());
        userRepresentation.setLastName(registerRequest.lastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("gender", List.of(registerRequest.gender().name()));
        attributes.put("biography", List.of(registerRequest.biography()));
        userRepresentation.setAttributes(attributes);

        // Prepare credential
        CredentialRepresentation cr = new  CredentialRepresentation();
        cr.setType(CredentialRepresentation.PASSWORD);
        cr.setValue(registerRequest.confirmedPassword());
        userRepresentation.setCredentials(List.of(cr));

        try (Response response = keycloak.realm("mbapi")
                     .users()
                     .create(userRepresentation)) {
            if (response.getStatus() == HttpStatus.CREATED.value()) {

                UserRepresentation ur = keycloak.realm("mbapi")
                        .users()
                        .search(userRepresentation.getUsername(), true)
                        .stream()
                        .findFirst()
                        .orElse(null);

                assert ur != null;

                roleService.assignRole(ur.getId(), "USER");

                this.verifyEmail(ur.getId());

                return RegisterResponse.builder()
                        .userId(ur.getId())
                        .username(ur.getUsername())
                        .email(ur.getEmail())
                        .firstName(ur.getFirstName())
                        .lastName(ur.getLastName())
                        .build();
            }
        }

        return null;
    }

}
