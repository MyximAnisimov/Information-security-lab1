package anisimov.iblab1.controller;

import jakarta.validation.Valid;
import anisimov.iblab1.dto.PersonDTO;
import anisimov.iblab1.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.text.MessageFormat;
import java.util.List;

@RestController @RequestMapping("/api/data") @RequiredArgsConstructor
public class PersonController {
    private final PersonService service;

    @GetMapping
    public List<PersonDTO> all() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO create(@RequestBody @Valid PersonDTO dto) {
        return service.create(dto);
    }
}
