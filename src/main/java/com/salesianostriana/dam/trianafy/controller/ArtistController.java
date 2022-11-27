package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository repository;
    private final SongRepository songRepository;


    @PostMapping("/artist")
    public ResponseEntity<Artist> create(@RequestBody Artist art){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(art));
    }

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id) {

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .of(repository.findById(id));
    }

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> edit(@RequestBody Artist a, @PathVariable Long id){

        return ResponseEntity
                .of(repository.findById(id).map(c -> {
                    c.setName(a.getName());
                    repository.save(c);
                    return c;
                }));
    }

    /*@DeleteMapping("/artist/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

    }*/




}
