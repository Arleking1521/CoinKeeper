package com.example.CoinKeeper.services;

import com.example.CoinKeeper.models.History;
import com.example.CoinKeeper.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryService(HistoryRepository historyRepository) { this.historyRepository = historyRepository;
    }

    public History findById(Long id){ return historyRepository.getOne(id); }

    public List<History> findAll(){ return historyRepository.findAll(); }

    public void saveHistory(History history){ historyRepository.save(history); }

    public void deleteById(Long id){ historyRepository.deleteById(id); }
}
