/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.music_library.repositories;

import com.example.music_library.models.Album;
import com.example.music_library.models.Artist;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author IvanAnemi4niq
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>{
    List<Artist> findByName(String name);
    List<Artist> findByNameStartingWith(String name);
    
}
