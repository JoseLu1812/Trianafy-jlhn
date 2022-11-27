package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.GetSongDto;
import com.salesianostriana.dam.trianafy.dto.SongConverterDto;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
public class SongController {

    private final SongService repository;
    private final SongConverterDto songConverter;
    private final ArtistService artistService;


    @PostMapping("/song/")
    public ResponseEntity<Song> create(@RequestBody CreateSongDto dto){

        if(dto.getTitle() == null){
            return ResponseEntity.badRequest().build();
        }
        if(dto.getAlbum() == null){
            return ResponseEntity.badRequest().build();
        }
        if(dto.getYear() == null){
            return ResponseEntity.badRequest().build();
        }
        if(dto.getArtisId() == null){
            return ResponseEntity.badRequest().build();
        }

        Song song = songConverter.convertToSong(dto);
        Artist artist = artistService.findById(dto.getArtisId()).orElse(null);
        song.setArtist(artist);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.add(song));
    }


    @GetMapping("/song")
    public ResponseEntity<List<Song>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(repository.findAll());
    }


    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findById(@PathVariable Long id){

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .of(repository.findById(id));
    }


    @PutMapping("/song/{id}")
    public ResponseEntity<Optional<Song>> update(@RequestBody GetSongDto dto, @PathVariable Long id){

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        Song song = songConverter.songToSongDto(dto);
        if( dto.getTitle() == null ){
            return ResponseEntity.badRequest().build();
        }
        if( dto.getAlbum() == null ){
            return ResponseEntity.badRequest().build();
        }
        if( dto.getYear() == null ){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity
                .ok()
                .body(
                    repository.findById(id).map(s -> {
                        s.setTitle(song.getTitle());
                        s.setAlbum(song.getAlbum());
                        s.setYear(song.getYear());
                        s.setArtist(song.getArtist());
                        repository.edit(s);
                        return s;
                    })
        );
    }


    public ResponseEntity<?> delete(@PathVariable Long id){

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
