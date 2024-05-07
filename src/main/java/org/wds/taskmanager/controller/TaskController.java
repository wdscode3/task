package org.wds.taskmanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.wds.taskmanager.domain.enums.StatusEnum;
import org.wds.taskmanager.dto.TaskRequestDTO;
import org.wds.taskmanager.dto.TaskResponseDTO;
import org.wds.taskmanager.exception.TaskException;
import org.wds.taskmanager.facade.Facade;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TaskController {

    private final Facade facade;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void novaTask(@RequestBody TaskRequestDTO request) throws TaskException {
        facade.novaTask(request);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/retomar/{id}")
    public void retomarTask(@PathVariable UUID id) throws TaskException {
        facade.retomarTask(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/finalizar/{id}")
    public void finalizarTask(@PathVariable UUID... id) throws TaskException {
        facade.finalizarTask(id);
    }

    @GetMapping("/{id}")
    public TaskResponseDTO buscarPorId(@PathVariable UUID id) throws TaskException {
        return facade.buscarTaskPorId(id);
    }

    @GetMapping("/filtro")
    public Page<TaskResponseDTO> buscarPorFiltro(@RequestParam(required = false) String descricao,
                                                    @RequestParam(required = false) StatusEnum[] status,
                                                    @RequestParam(required = false) LocalDate inicio,
                                                    @RequestParam(required = false) LocalDate fim,
                                                    @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        return facade.buscarPorFiltro(descricao, status, inicio, fim, pageable);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarTask(@PathVariable UUID id) throws TaskException {
        facade.deletarTask(id);
    }

    @PutMapping("/pausar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pausarTask(@PathVariable UUID id) throws TaskException {
        facade.pausarTask(id);
    }

    @PutMapping("/pausar-todos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pausarTodos() throws TaskException {
        facade.pausarTodos();
    }

    @PutMapping("/finalizar-todos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finalizarTodos() throws TaskException {
        facade.finalizarTodos();
    }

}
