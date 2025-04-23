package com.tuk.prj.services;

import com.tuk.prj.data.Game;
import com.tuk.prj.data.GameRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public Optional<Game> get(Long id) {
        return repository.findById(id);
    }

    public Game save(Game entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Game> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Game> list(Pageable pageable, Specification<Game> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
