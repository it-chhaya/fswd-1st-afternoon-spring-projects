package kh.edu.cstad.mbapi.controller;

import kh.edu.cstad.mbapi.dto.AssignRoleRequest;
import kh.edu.cstad.mbapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/assign-role")
    public void assignRole(@RequestBody AssignRoleRequest assignRoleRequest) {
        roleService.assignRole(assignRoleRequest.userId(),
                assignRoleRequest.roleName());
    }

}
