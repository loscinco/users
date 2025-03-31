package org.agendifive.users.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.agendifive.users.model.Request;
import org.agendifive.users.model.Response;
import org.agendifive.users.service.AuthInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;



@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthInterface authService;

    @PostMapping("/login")
    @Operation(
            summary = "verifica usuario y contraseña",
            description = "Genera JWT cuando usuario y contraseña son correctos"
    )
    public Response login(@RequestBody Request request) {

        return authService.authenticate(request.getUserName(), request.getPassword());
    }
}