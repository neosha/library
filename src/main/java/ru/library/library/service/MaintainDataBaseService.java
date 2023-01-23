package ru.library.library.service;

import org.springframework.stereotype.Service;
import ru.library.library.repository.MaintainDataBaseRepository;

@Service
public class MaintainDataBaseService {
    private MaintainDataBaseRepository maintainDataBaseRepository;

    public MaintainDataBaseService(MaintainDataBaseRepository maintainDataBaseRepository) {
        this.maintainDataBaseRepository = maintainDataBaseRepository;
    }

}
