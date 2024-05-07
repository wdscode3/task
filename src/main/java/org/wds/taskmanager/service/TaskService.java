package org.wds.taskmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.wds.taskmanager.domain.Task;
import org.wds.taskmanager.domain.enums.StatusEnum;
import org.wds.taskmanager.exception.TaskException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskService {

    Task buscarPorStatusOrNull(StatusEnum running);

    void salvar(Task task) throws TaskException;

    Task buscarPorId(UUID id) throws TaskException;

    List<Task> buscarPorId(UUID... id) throws TaskException;

    void pausar(Task taskAtual) throws TaskException;

    void updateWork(Task task);

    Page<Task> buscarPorFiltro(String descricao, StatusEnum[] status, LocalDate inicio, LocalDate fim, Pageable pageable);

    void deletar(UUID id) throws TaskException;

    void finalizar(Task task) throws TaskException;

    List<Task> buscarPorStatus(StatusEnum... statusEnum);
}
