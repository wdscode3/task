package org.wds.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.wds.taskmanager.domain.enums.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private String descricao;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private String originalEstimate;
    
    private String remainingWork;
    
    private String completedWork;

    private StatusEnum status;

    private UUID idExterno;

    private List<AtividadeResponseDTO> atividades;

    public Integer getTotalInterrupcao() {
        return status.isRunning() ? atividades.size() - 1 : atividades.size();
    }

}
