package org.wds.taskmanager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.wds.taskmanager.domain.Atividade;
import org.wds.taskmanager.domain.Task;
import org.wds.taskmanager.domain.enums.StatusEnum;
import org.wds.taskmanager.exception.NotFoundException;
import org.wds.taskmanager.exception.TaskException;
import org.wds.taskmanager.repository.TaskRepository;
import org.wds.taskmanager.service.CustomMessageSource;
import org.wds.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static java.lang.Long.compare;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.wds.taskmanager.domain.enums.StatusEnum.FINISHED;
import static org.wds.taskmanager.domain.enums.StatusEnum.PAUSED;
import static org.wds.taskmanager.utils.AssertsUtil.*;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CustomMessageSource messageSource;

    @Override
    public Task buscarPorStatusOrNull(StatusEnum status) {
        return taskRepository.findFirstByStatus(status).orElse(null);
    }

    @Override
    public void salvar(Task task) throws TaskException {
        try {
            taskRepository.save(task);
        } catch (Exception e) {
            throw new TaskException(messageSource.getMessage("erro.task.salvar"), e);
        }
    }

    @Override
    public Task buscarPorId(UUID id) throws TaskException {
        return taskRepository.findByIdExterno(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("erro.task.buscar")));
    }

    @Override
    public List<Task> buscarPorId(UUID... id) throws TaskException {
        return taskRepository.findByIdExternoIn(id);
    }

    @Override
    public void pausar(Task task) throws TaskException {
        
        assertNull(task, messageSource.getMessage("erro.task.null"));
        task.getAtividades().forEach(Atividade::finalizar);
        task.setStatus(PAUSED);
        this.updateWork(task);
        this.salvar(task);
    }

    @Override
    public void finalizar(Task task) throws TaskException {
        assertNull(task, messageSource.getMessage("erro.task.null"));
        task.getAtividades().forEach(Atividade::finalizar);
        task.setStatus(FINISHED);
        task.setFim(now());
        this.updateWork(task);
        this.salvar(task);
    }

    @Override
    public List<Task> buscarPorStatus(StatusEnum... statusEnum) {
        return taskRepository.findAllByStatusIn(statusEnum);
    }

    @Override
    public void updateWork(Task task) {
        var completeWork = task.getAtividades().stream().mapToLong(Atividade::getWorkToMillis).sum();
        var remainingWork = compare(completeWork, task.getOriginalEstimate()) > 0 ? 0 : (task.getOriginalEstimate() - completeWork);
        
        task.setCompletedWork(completeWork);
        task.setRemainingWork(remainingWork);
    }

    @Override
    public Page<Task> buscarPorFiltro(String descricao, StatusEnum[] status, LocalDate inicio, LocalDate fim, Pageable pageable) {
        var descTratada = getStringOrNull(descricao);
        var statusTratado = getArrayOrNUll(status);
        var dataInicio = ofNullable(inicio).map(LocalDate::atStartOfDay).orElse(null);
        var dataFim = ofNullable(fim).map(f -> f.atTime(LocalTime.MAX)).orElse(null);
        return taskRepository.findByFilter(descTratada, statusTratado, dataInicio, dataFim, pageable);
    }

    @Override
    public void deletar(UUID id) throws TaskException {
        var task = taskRepository.findByIdExterno(id).orElseThrow(() -> new NotFoundException(messageSource.getMessage("erro.task.buscar")));
        try {
            taskRepository.delete(task);
        } catch (Exception e) {
            throw new TaskException(messageSource.getMessage("erro.task.deletar"), e);
        }
    }

}
