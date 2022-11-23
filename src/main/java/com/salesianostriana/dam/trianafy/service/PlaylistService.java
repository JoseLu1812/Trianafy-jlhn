package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository repository;

    @PostMapping("/list")
    public Playlist add(Playlist playlist) {

        return repository.save(playlist);


    }

    @GetMapping("/list/{id}")
    public Optional<Playlist> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping("/list")
    public List<Playlist> findAll() {
        return repository.findAll();
    }

    @PutMapping("/list{")
    public Playlist edit(Playlist playlist) {
        return repository.save(playlist);
    }

    public void delete(Playlist playlist) {
        repository.delete(playlist);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
