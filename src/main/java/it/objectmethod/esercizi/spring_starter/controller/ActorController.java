package it.objectmethod.esercizi.spring_starter.controller;

import com.fasterxml.jackson.annotation.JsonView;
import it.objectmethod.esercizi.spring_starter.dto.*;
import it.objectmethod.esercizi.spring_starter.service.ActorService;
import it.objectmethod.esercizi.spring_starter.util.PaginationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actor")
public class ActorController {
    private final ActorService actorService;

    public ActorController(final ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/{id}")
    public ActorDTO findById(@PathVariable("id") final Integer id) {
        return actorService.findById(id);
    }

    @GetMapping("/all")
    public List<ActorDTO> getAll() {
        return actorService.getAll();
    }

    @GetMapping("/allIdName")
    public ResponseEntity<List<IdNameDTO>> getAllActorsIdName() {
        return ResponseEntity.ok(actorService.getAllActorsIdAndName());
    }

    @GetMapping("/allPage")
    public PaginationResponse<ActorDTO> getPage(@RequestParam(defaultValue = "0") final Integer page,
                                                @RequestParam(defaultValue = "2") final Integer size,
                                                @RequestParam(required = false) final String size_
    ) {
        return actorService.getPage(page, size, size_);
    }

    @GetMapping("/search_by")
    public ActorDTO findByName(@RequestParam final String name) {
        return actorService.findByName(name);
    }

    @GetMapping("criteria/by")
    public ResponseEntity<List<ActorDTO>> findItemUsingCriteria(@RequestParam(required = false) final String name,
                                                                @RequestParam(required = false) final String surname,
                                                                @RequestParam(required = false) final String city) {
        return ResponseEntity.ok(actorService.findAllByCriteria(name, surname, city));
    }

    @GetMapping("/by")
    public ResponseEntity<List<ActorDTO>> findByItemBy(@RequestParam(required = false) final String name,
                                                       @RequestParam(required = false) final String surname,
                                                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") final LocalDate dob,
                                                       @RequestParam(required = false) final String city) {
        return ResponseEntity.ok(actorService.findAllBySpecs(name, surname, dob, city));
    }

    @GetMapping("/dto/by")
    public ResponseEntity<List<ActorDTO>> findByItemBy(ActorDTO dto) {
        return ResponseEntity.ok(actorService.findAllBySpecs(dto));
    }

    @PostMapping("/save")
    public ResponseEntity<ActorDTO> addActor(@RequestBody @Validated({ActorDTO.ForPost.class}) ActorDTO actorDTO) {
        if (actorDTO == null) return ResponseEntity.badRequest().build();
        ActorDTO newDto = actorService.save(actorDTO);
        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteActor(@PathVariable final Integer id) {
        actorService.deleteActorById(id);
        return ResponseEntity.ok(Map.of("message", "Actor deleted successfully"));
    }

    @GetMapping("/jpql/query")
    public ResponseEntity<List<ActorDTO>> getJPQL(@RequestParam final String city) {
        return ResponseEntity.ok().body(actorService.getActorsFilteredByCityUsingJPQL(city));
    }

    @GetMapping("/native/query")
    public ResponseEntity<List<ActorDTO>> getNative(@RequestParam final String city) {
        return ResponseEntity.ok().body(actorService.getActorsFilteredByCityUsingNative(city));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateActor(@RequestBody ActorDTO actorDTO) {
        if (actorDTO == null) return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        ActorDTO newDto = actorService.update(actorDTO);

        if (newDto == null) return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(newDto);
    }

//    @GetMapping("/params/specs")
//    public ResponseEntity<?> get(@RequestParam final Map<String, String> map) {
//        if (map.isEmpty()) return new ResponseEntity<>(this.getAll(), HttpStatus.OK);
//        List<ActorDTO> queryResult = actorService.findByAllParams(map);
//        if (queryResult == null || queryResult.isEmpty())
//            return new ResponseEntity<>("No director found", HttpStatus.NOT_FOUND);
//
//        return new ResponseEntity<List<ActorDTO>>(queryResult, HttpStatus.FOUND);
//    }

    @GetMapping("/params/specs")
    public List<ActorDTO> get(@RequestParam final Map<String, String> map) {
        return actorService.findByAllParams(map);
    }

    @GetMapping("/names/in")
    public List<ActorDTO> getInNames(@RequestParam final String... names) {
        return actorService.findByNameIn(names);
    }

    @JsonView({FilmDTO.BasicView.class})
    @GetMapping("/allInfo")
    public List<ActorCompleteDTO> getAllInfo() {
        return actorService.getEverything();
    }

    @JsonView({FilmDTO.BasicView.class})
    @GetMapping("/allInfoById/{id}")
    public ActorCompleteDTO getAllInfoById(@PathVariable Integer id) {
        return actorService.getEverythingById(id);
    }

    @JsonView({FilmDTO.DetailedView.class})
    @GetMapping("/allInfoWithDirector")
    public List<ActorCompleteDTO> getAllInfoWithDirector() {
        return actorService.getEverything();
    }

    @GetMapping("/actorsByFilmId/{id}")
    public ResponseEntity<?> movies(@PathVariable Integer id) {
        if (id == null) return new ResponseEntity<>("Data not found", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(actorService.getFilmsByActorId(id), HttpStatus.OK);
    }

    @GetMapping("/record")
    public ResponseEntity<ActorRecord> getAR(@RequestParam String city) {
        return ResponseEntity.ok(actorService.getActorRecord(city));
    }
}
