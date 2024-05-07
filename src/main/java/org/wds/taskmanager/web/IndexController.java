package org.wds.taskmanager.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.wds.taskmanager.domain.enums.RefreshEnum;
import org.wds.taskmanager.domain.enums.StatusEnum;
import org.wds.taskmanager.dto.TaskRequestDTO;
import org.wds.taskmanager.exception.TaskException;
import org.wds.taskmanager.facade.Facade;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final Facade facade;

    @GetMapping("/")
    public String index(Model model,
                         @RequestParam(required = false) String descricao,
                         @RequestParam(required = false) StatusEnum[] status,
                         @RequestParam(required = false) LocalDate inicio,
                         @RequestParam(required = false) LocalDate fim,
                         @RequestParam(required = false) Long refresh,
                         @PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        var tasks = facade.buscarPorFiltro(descricao, status, inicio, fim, pageable);

        model.addAttribute("descricao", descricao);
        model.addAttribute("inicio", inicio);
        model.addAttribute("fim", fim);
        model.addAttribute("status", status);
        model.addAttribute("statusList", StatusEnum.values());
        model.addAttribute("tasks", tasks);
        model.addAttribute("refreshList", RefreshEnum.values());
        model.addAttribute("refresh", refresh);
        return "index";
    }

    @PostMapping(value = "/nova")
    public String novaTask(TaskRequestDTO request) throws TaskException {
        facade.novaTask(request);
        return "redirect:/";
    }

    @RequestMapping("/retomar/{id}")
    public String retomarTask(@PathVariable UUID id) throws TaskException {
        facade.retomarTask(id);
        return "redirect:/";
    }

    @RequestMapping("/reabrir/{id}")
    public String reabrirTask(@PathVariable UUID id) throws TaskException {
        facade.reabrirTask(id);
        return "redirect:/";
    }

    @RequestMapping("/finalizar/{id}")
    public String finalizarTask(@PathVariable UUID id) throws TaskException {
        facade.finalizarTask(id);
        return "redirect:/";
    }

    @RequestMapping("/buscar/{id}")
    public String buscarPorId(@PathVariable UUID id) throws TaskException {
        var task = facade.buscarTaskPorId(id);
        return "redirect:/";
    }

    @RequestMapping("/deletar/{id}")
    public String deletarTask(@PathVariable UUID id) throws TaskException {
        facade.deletarTask(id);
        return "redirect:/";
    }

    @RequestMapping("/pausar/{id}")
    public String pausarTask(@PathVariable UUID id) throws TaskException {
        facade.pausarTask(id);
        return "redirect:/";
    }

    @RequestMapping("/pausar-todos")
    public void pausarTodos() throws TaskException {
        facade.pausarTodos();
    }

    @RequestMapping("/finalizar-todos")
    public void finalizarTodos() throws TaskException {
        facade.finalizarTodos();
    }

}
