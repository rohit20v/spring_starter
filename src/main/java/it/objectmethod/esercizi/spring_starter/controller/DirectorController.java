package it.objectmethod.esercizi.spring_starter.controller;

import it.objectmethod.esercizi.spring_starter.dto.DirectorDTO;
import it.objectmethod.esercizi.spring_starter.service.DirectorService;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/director")
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors() {
        if (directorService.getAllDirectors().isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(directorService.getAllDirectors());
    }

    @GetMapping("/by")
    public ResponseEntity<List<DirectorDTO>> getAllDirectorsBySpecification(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            @RequestParam(required = false) String city) {
        if (directorService.getAllDirectors().isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(directorService.getAllDirectorsBySpecification(name, surname, city));
    }

    @GetMapping("dto/by")
    public ResponseEntity<List<DirectorDTO>> getAllDirectorsBySpecificationWithDTO(@Validated(PUT.class) final DirectorDTO dto) {
        if (directorService.getAllDirectors().isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(directorService.getAllDirectorsBySpecification(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable final Integer id) {
        if (directorService.getDirectorById(id) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(directorService.getDirectorById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable final Integer id) {
//        if (id == null) return ResponseEntity.badRequest().build();
        directorService.deleteDirectorById(id);
        return ResponseEntity.ok("Director deleted successfully");
    }

    @PostMapping("/save")
    public ResponseEntity<DirectorDTO> save(@RequestBody @Validated DirectorDTO dto) {
        if (dto == null) return ResponseEntity.badRequest().build();
        DirectorDTO newDto = directorService.save(dto);
        return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    }

    @GetMapping("/vlad/specs")
    public ResponseEntity<?> get(final DirectorDTO dto) {
        List<DirectorDTO> queryResult = directorService.findAllDirectorsFilteredUsingSpecs(dto);
        if (queryResult == null || queryResult.isEmpty())
            return new ResponseEntity<>("No director found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(queryResult, HttpStatus.FOUND);
    }

    @GetMapping("/params/specs")
    public ResponseEntity<?> get(@RequestParam final Map<String, String> map) {
        if (map.isEmpty()) return this.getAllDirectors();
        List<DirectorDTO> queryResult = directorService.findByAllParams(map);
        if (queryResult == null || queryResult.isEmpty())
            return new ResponseEntity<>("No director found", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(queryResult, HttpStatus.FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<DirectorDTO> update(@RequestBody DirectorDTO dto) {
        if (dto == null) return ResponseEntity.badRequest().build();
        DirectorDTO newDto = directorService.update(dto);
        return ResponseEntity.ok(newDto);
    }

    @GetMapping("/page")
    public PaginationResponse<DirectorDTO> getInPage(@PageableDefault(size = 2) Pageable pageable) {
        return directorService.getDirectorPages(pageable);
    }

    public interface PUT {

    }
}
