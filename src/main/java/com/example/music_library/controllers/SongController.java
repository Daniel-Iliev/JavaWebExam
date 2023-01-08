/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_library.controllers;

import com.example.music_library.repositories.AlbumRepository;
import com.example.music_library.models.Song;
import com.example.music_library.repositories.SongRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author IvanAnemi4niq
 */
@Controller
public class SongController {

    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    SongRepository songRepository;

    @GetMapping("/songs")
    public String index(Model model) {
        model.addAttribute("songs", songRepository.findAll());
        return "songs-index";
    }
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("songs", songRepository.findAll());
        return "songs-index";
    }
    
    @GetMapping("/addsongform")
    public String showSignUpForm(Song song, Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "add-song";
    }
    
    @PostMapping("/addsong")
    public String addSong(@Valid Song song, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-song";
        }

        songRepository.save(song);
        model.addAttribute("songs", songRepository.findAll());
        return "redirect:/songs";
    }
    @GetMapping("/editsong/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song Id:" + id));

        model.addAttribute("albums", albumRepository.findAll());
        model.addAttribute("song", song);
        return "update-song";
    }

    @PostMapping("/updatesong/{id}")
    public String updateSong(@PathVariable("id") Long id, @Valid Song song,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            song.setId(id);
            return "update-song";
        }

        songRepository.save(song);
        model.addAttribute("song", songRepository.findAll());
        return "redirect:/songs";
    }

    @GetMapping("/deletesong/{id}")
    public String deleteSong(@PathVariable("id") Long id, Model model) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid song Id:" + id));
        songRepository.delete(song);
        model.addAttribute("songs", songRepository.findAll());
        return "redirect:/songs";
    }
    
    @GetMapping("/searchsong")
    public String showSongsByName(@RequestParam (name = "search", required = false) String name, Model model) {
        model.addAttribute("songs", songRepository.findByNameStartingWith(name));
        return "songs-index";
    }
    
  
}
