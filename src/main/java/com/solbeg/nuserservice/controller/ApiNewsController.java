package com.solbeg.nuserservice.controller;

import com.solbeg.nuserservice.model.AuthParamsModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class ApiNewsController {

    @GetMapping
    @PreAuthorize("hasAuthority('news:read')")
    public List<?> getAll() {
        return null;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('news:read')")
    public Object getById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('news:write')")
    public Object addModel(@RequestBody Object o) {
        return null;
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('news:write')")
    public boolean delete(@PathVariable Long id) {
        return true;
    }
}
