package anisimov.iblab1.service;

import jakarta.persistence.EntityNotFoundException;
import anisimov.iblab1.dto.PersonDTO;
import anisimov.iblab1.entity.Person;
import anisimov.iblab1.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream()
                .map(t -> new PersonDTO(t.getId(), t.getName(), t.getSurname(), t.getAge()))
                .toList();
    }

    @Transactional
    public PersonDTO create(PersonDTO dto) {
        Person saved = personRepository.save(Person.builder().name(dto.getName()).surname(dto.getSurname()).age(dto.getAge()).build());
        return new PersonDTO(saved.getId(), saved.getName(), saved.getSurname(), saved.getAge());
    }
}
