package org.wds.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeResponseDTO {

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private UUID idExterno;
}
