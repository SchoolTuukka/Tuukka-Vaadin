package com.tuk.prj.services;

import com.tuk.prj.data.Game;
import com.tuk.prj.data.GameRepository;
import com.tuk.prj.data.SpeedRuns;
import com.tuk.prj.data.SpeedRunsRepository;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SpeedRunsService {

    private final SpeedRunsRepository repository;

    public SpeedRunsService(SpeedRunsRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private GameRepository gameRepository;

    public Game findGameByTitle(String title) {
        return gameRepository.findByTitle(title).orElse(null);
    }
    public Optional<SpeedRuns> get(Long id) {
        return repository.findById(id);
    }

    public SpeedRuns save(SpeedRuns entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<SpeedRuns> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<SpeedRuns> list(Pageable pageable, Specification<SpeedRuns> filter) {
        return repository.findAll(filter, pageable);
    }


    public int count() {
        return (int) repository.count();
    }


}
