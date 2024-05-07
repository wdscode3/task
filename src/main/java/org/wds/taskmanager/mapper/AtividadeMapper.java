package org.wds.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.wds.taskmanager.domain.Atividade;
import org.wds.taskmanager.dto.AtividadeResponseDTO;

@Mapper(componentModel = "spring")
public abstract class AtividadeMapper {

    public abstract AtividadeResponseDTO toDto(Atividade atividade);
    
}
