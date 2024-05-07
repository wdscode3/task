package org.wds.taskmanager.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.wds.taskmanager.domain.Atividade;
import org.wds.taskmanager.domain.Task;
import org.wds.taskmanager.domain.enums.StatusEnum;
import org.wds.taskmanager.dto.TaskRequestDTO;
import org.wds.taskmanager.dto.TaskResponseDTO;
import org.wds.taskmanager.exception.TaskException;
import org.wds.taskmanager.mapper.TaskMapper;
import org.wds.taskmanager.service.CustomMessageSource;
import org.wds.taskmanager.service.TaskService;

import java.time.LocalDate;
import java.util.UUID;

import static org.wds.taskmanager.domain.enums.StatusEnum.PAUSED;
import static org.wds.taskmanager.domain.enums.StatusEnum.RUNNING;
import static org.wds.taskmanager.utils.AssertsUtil.assertTrue;
import static org.wds.taskmanager.utils.AssertsUtil.isNotNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class Facade {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final CustomMessageSource messageSource;

    @Transactional
    public void novaTask(TaskRequestDTO request) throws TaskException {

        var taskAtual = taskService.buscarPorStatusOrNull(RUNNING);
        if (isNotNull(taskAtual))
            taskService.pausar(taskAtual);

        var novaTask = Task.builder()
                            .descricao(request.getDescricao())
                            .originalEstimate(request.getOriginalEstimate())
                            .build();
        taskService.salvar(novaTask);
        
    }

    public TaskResponseDTO buscarTaskPorId(UUID id) throws TaskException {
        var task = taskService.buscarPorId(id);
        return taskMapper.toDto(task);
    }

    public Page<TaskResponseDTO> buscarPorFiltro(String descricao, StatusEnum[] status, LocalDate inicio, LocalDate fim, Pageable pageable) {
        var tasks = taskService.buscarPorFiltro(descricao, status, inicio, fim, pageable);
        tasks.stream().filter(Task::isRunning).forEach(taskService::updateWork);
        return tasks.map(taskMapper::toDto);
    }

    public void retomarTask(UUID id) throws TaskException {
        var task = taskService.buscarPorId(id);
        assertTrue(task.isFinished(), messageSource.getMessage("erro.task.finished"));

        var taskAtual = taskService.buscarPorStatusOrNull(RUNNING);
        if (isNotNull(taskAtual))
            taskService.pausar(taskAtual);

        task.getAtividades().add(new Atividade(task));
        task.setStatus(RUNNING);
        taskService.salvar(task);
    }

    public void finalizarTask(UUID... id) throws TaskException {
        var tasks = taskService.buscarPorId(id);
        for(var task : tasks) {
            taskService.finalizar(task);
        }
    }

    public void deletarTask(UUID id) throws TaskException {
        taskService.deletar(id);
    }

    public void pausarTask(UUID id) throws TaskException {
        var task = taskService.buscarPorId(id);
        taskService.pausar(task);
    }

    public void pausarTodos() throws TaskException {
        var tasks = taskService.buscarPorStatus(RUNNING, PAUSED);
        for(var task : tasks) {
            taskService.pausar(task);
        }
    }

    public void finalizarTodos() throws TaskException {
        var tasks = taskService.buscarPorStatus(RUNNING, PAUSED);
        for(var task : tasks) {
            taskService.finalizar(task);
        }
    }

    public void reabrirTask(UUID id) throws TaskException {
        var task = taskService.buscarPorId(id);
        assertTrue(!task.isFinished(), messageSource.getMessage("erro.task.not-finished"));
        task.setStatus(PAUSED);
        taskService.salvar(task);
    }
}
