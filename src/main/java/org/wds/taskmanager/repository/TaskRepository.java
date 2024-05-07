package org.wds.taskmanager.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.wds.taskmanager.domain.Task;
import org.wds.taskmanager.domain.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findFirstByStatus(StatusEnum running);

    Optional<Task> findByIdExterno(UUID id);

    @Query(value = "FROM Task t WHERE 1 = 1 " +
        "AND (:descricao IS NULL OR UPPER(t.descricao) LIKE CONCAT('%', UPPER(:descricao), '%')) " +
        "AND (:status IS NULL OR t.status IN :status) " +
        "AND (:inicio IS NULL OR :fim IS NULL OR t.inicio BETWEEN :inicio AND :fim)")
    Page<Task> findByFilter(@Param(value = "descricao") String descricao, @Param(value = "status") StatusEnum[] status, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    List<Task> findByIdExternoIn(UUID... id);

    List<Task> findAllByStatusIn(StatusEnum[] statusEnum);
}
