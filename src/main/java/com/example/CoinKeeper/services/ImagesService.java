package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.Images;
import com.example.CoinKeeper.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {
    private final ImagesRepository imagesRepository;

    @Autowired
    public ImagesService(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public Images findById(Long id){ return imagesRepository.getOne(id); }

    public List<Images> findAll(){ return imagesRepository.findAll(); }

    public void saveImages(Images images){ imagesRepository.save(images); }

    public void deleteById(Long id){ imagesRepository.deleteById(id); }
}
