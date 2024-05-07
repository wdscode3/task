package org.wds.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    private String descricao;
    private Long originalEstimate;

    public Long getOriginalEstimate() {
        return originalEstimate * 3600000;
    }
    

}
