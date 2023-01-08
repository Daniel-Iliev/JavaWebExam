/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_library.controllers;

import com.example.music_library.models.Artist;
import com.example.music_library.repositories.ArtistRepository;
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
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @GetMapping("/artists")
    public String index(Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "artist-index";
    }
    
    @GetMapping("/addartistform")
    public String showSignUpForm(Artist artist) {
        return "add-artist";
    }
    
    @PostMapping("/addartist")
    public String addArtist(@Valid Artist artist, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-artist";
        }

        artistRepository.save(artist);
        model.addAttribute("artists", artistRepository.findAll());
        return "redirect:/artists";
    }
    @GetMapping("/editartist/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));

        model.addAttribute("artist", artist);
        return "update-artist";
    }

    @PostMapping("/updateartist/{id}")
    public String updateArtist(@PathVariable("id") Long id, @Valid Artist artist,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            artist.setId(id);
            return "update-artist";
        }

        artistRepository.save(artist);
        model.addAttribute("artist", artistRepository.findAll());
        return "redirect:/artists";
    }

    @GetMapping("/deleteartist/{id}")
    public String deleteArtist(@PathVariable("id") Long id, Model model) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));

        artistRepository.delete(artist);
        model.addAttribute("artists", artistRepository.findAll());
        return "redirect:/artists";
    }
    
    @GetMapping("/searchartist")
    public String showArtistsByName(@RequestParam (name = "search", required = false) String name, Model model) {
        model.addAttribute("artists", artistRepository.findByNameStartingWith(name));
        return "artist-index";
    }
    
  
}
