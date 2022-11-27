package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.GetPlaylistDto;
import com.salesianostriana.dam.trianafy.dto.PlaylistConverterDto;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {

    private PlaylistService repository;
    private PlaylistConverterDto playlistConverterDto;

    private SongService songService;


    @PostMapping("/list")
    public ResponseEntity<Playlist> create(@RequestBody CreatePlaylistDto dto){

        if(dto.getName() == null){
           return ResponseEntity.badRequest().build();
        }
        if(dto.getDescription() == null){
            return ResponseEntity.badRequest().build();
        }

        Playlist list = playlistConverterDto.convertToPlaylist(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.add(list));
    }

    @GetMapping("list")
    public ResponseEntity<List<GetPlaylistDto>> findAll(){

        if(repository.findAll() == null){
            return ResponseEntity.notFound().build();
        }

        List<GetPlaylistDto> lista = playlistConverterDto.getAllPlaylist(repository.findAll());

        return ResponseEntity
                .ok()
                .body(lista);
    }


    @GetMapping("/list/{id}")
    public ResponseEntity<Optional<Playlist>> findById(@PathVariable Long id){
        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity
                    .ok()
                    .body(repository.findById(id));
        }
    }


    @PutMapping("/list/{id}")
    public ResponseEntity<Playlist> edit(@RequestBody Playlist list, @PathVariable Long id){
        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        repository.findById(id).map(l -> {
            l.setName(null);
            l.setSongs(null);
            l.setDescription(null);
            repository.edit(l);
            return l;
        });

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Optional<Playlist>> addSongToPlaylist(@PathVariable Long id1, @PathVariable Long id2){
        if(songService.findById(id2) == null){
            return ResponseEntity.notFound().build();
        }

        Optional<Playlist> playlist = repository.findById(id1);

        return ResponseEntity
                .ok()
                .body(playlist.map(l -> {
                    l.addSong(songService.findById(id2).get());
                    repository.edit(l);
                    return l;
                })
                );
    }

    @GetMapping("/list/{id}/song")
    public ResponseEntity<Playlist> getPlaylistSongs(@PathVariable Long id){
        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .body(repository.findById(id).get());
    }

    @GetMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Song> getSongFromPlaylist(@PathVariable Long id1, @PathVariable Long id2){

        if(repository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .body(repository.findById(id1).get().getSongs().get(id2.intValue()));
    }

    @DeleteMapping("/list/{id1}/song/{id2}")
     public ResponseEntity<?> deleteSongFromPlaylist(@PathVariable Long id1, @PathVariable Long id2){
        if(repository.findById(id1) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(repository.findById(id1).get().getSongs().remove(id2));
    }



}
