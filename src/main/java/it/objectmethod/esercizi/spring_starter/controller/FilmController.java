package it.objectmethod.esercizi.spring_starter.controller;

import it.objectmethod.esercizi.spring_starter.dto.FilmDTO;
import it.objectmethod.esercizi.spring_starter.dto.FilmRecord;
import it.objectmethod.esercizi.spring_starter.service.FilmService;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/film")
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FilmDTO>> get() {
        return ResponseEntity.ok(filmService.getFilms());
    }

    @GetMapping("/params/specs")
    public ResponseEntity<?> get(@RequestParam final Map<String, String> map) {
        if (map.isEmpty()) return this.get();
        List<FilmDTO> queryResult = filmService.findByAllParams(map);
        if (queryResult == null || queryResult.isEmpty()) {
            return new ResponseEntity<>("No director found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(queryResult, HttpStatus.FOUND);
    }

    @GetMapping("/all/page")
    public ResponseEntity<Page<FilmDTO>> getPages(
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "2") final Integer size
    ) {
        return ResponseEntity.ok(filmService.getFilmPages(page, size));
    }

    @GetMapping("/spec/filmByActorId/{id}")
    public ResponseEntity<List<FilmDTO>> getFilmsByActorId(@PathVariable final Integer id) {
        return ResponseEntity.ok(filmService.getFilmByActorId(id));
    }

    @GetMapping("/by")
    public ResponseEntity<List<FilmDTO>> getRecordsBySpecification(@RequestParam(required = false) final String title,
                                                                   @RequestParam(required = false) final Date date,
                                                                   @RequestParam(required = false) final Integer directorId,
                                                                   @RequestParam(required = false) final String category) {
        return ResponseEntity.ok(filmService.getFilmsUsingSpecification(title, date, category, directorId));
    }

    @GetMapping("dto/by")
    public ResponseEntity<List<FilmDTO>> getRecordsBySpecification(FilmDTO filmDTO) {
        return ResponseEntity.ok(filmService.getFilmsUsingSpecificationDtoParams(filmDTO));
    }

    @GetMapping("page/by")
    public ResponseEntity<Page<FilmDTO>> getRecordPagesBySpecification(
            FilmDTO filmDTO,
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "1") final Integer size) {
        return ResponseEntity.ok(filmService.getFilmPagesUsingSpecification(filmDTO, page, size));
    }

    @GetMapping("/custompage")
    public ResponseEntity<PaginationResponse<FilmDTO>> getCustomPage(
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "2") final Integer size) {
        return ResponseEntity.ok(filmService.getCustomFilmPages(page, size));
    }

    @GetMapping("page/{id}")
    public ResponseEntity<Page<FilmDTO>> getFilmsByDirectorId(
            @PathVariable final Integer id,
            @RequestParam(defaultValue = "0") final Integer page,
            @RequestParam(defaultValue = "20") final Integer size) {

        return ResponseEntity.ok(filmService.getFilmsByDirectorId(id, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDTO> getFilmById(@PathVariable final Integer id) {
        if (id == null) ResponseEntity.badRequest().body("Invalid data");
        FilmDTO filmById = filmService.getFilmById(id);
        if (filmById == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(filmById);
    }

    @PostMapping("/save")
    public ResponseEntity<FilmDTO> save(@RequestBody FilmDTO dto) {
        if (dto == null) return ResponseEntity.badRequest().build();
        FilmDTO newDto = filmService.save(dto);
        return ResponseEntity.ok(newDto);
    }

    @PutMapping("/update")
    public ResponseEntity<FilmDTO> update(@RequestBody FilmDTO dto) {
        if (dto == null) return ResponseEntity.badRequest().build();
        FilmDTO newDto = filmService.update(dto);
        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable final Integer id) {
//        if (filmService.getFilmById(id) == null) ResponseEntity.ok("Film doesn't exist");
        filmService.delete(id);
        return ResponseEntity.ok("Film deleted successfully");
    }

    @GetMapping("record/category")
    public ResponseEntity<List<FilmRecord>> get(@RequestParam String genre) {
        return ResponseEntity.ok(filmService.getFilmsByGenre(genre));
    }
}
