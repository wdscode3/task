package org.wds.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.wds.taskmanager.domain.Atividade;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    
}
