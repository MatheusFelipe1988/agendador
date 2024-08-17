package com.api.agenda.api.controller;

import com.api.agenda.configuration.exception.BussinessException;
import com.api.agenda.domain.entity.Role;
import com.api.agenda.domain.entity.Usuario;
import com.api.agenda.domain.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService service;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(service.getRoles(), FOUND);
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try {
            service.createRole(theRole);
            return ResponseEntity.ok("New role success");
        }catch (BussinessException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId){
        service.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        return service.removeAllUsersFromRole(roleId);
    }

    @PostMapping("/remove-from-role")
    public Usuario removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        return service.removeUserFromRole(userId, roleId);
    }

    @PostMapping("/assign-role")
    public Usuario assignUserToRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        return service.assignToRoleUser(userId, roleId);
    }
}
