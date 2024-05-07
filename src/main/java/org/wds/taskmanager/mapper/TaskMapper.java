package org.wds.taskmanager.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.wds.taskmanager.domain.Task;
import org.wds.taskmanager.dto.TaskResponseDTO;

@Mapper(componentModel = "spring", uses = {AtividadeMapper.class})
public abstract class TaskMapper {
    
    @Mapping(source = "originalEstimate", target = "originalEstimate", qualifiedByName = "toDateTime")
    @Mapping(source = "remainingWork", target = "remainingWork", qualifiedByName = "toDateTime")
    @Mapping(source = "completedWork", target = "completedWork", qualifiedByName = "toDateTime")
    public abstract TaskResponseDTO toDto(Task task);

    @Named("toDateTime")
    public String toDateTime(Long time) {
        if (time == null) {
            return "00:00";
        }

        long segundos = time / 1000; // Convertendo milissegundos para segundos
        int horas = (int) (segundos / 3600); // Calculando horas
        int minutos = (int) ((segundos % 3600) + 30) / 60; // Calculando minutos com arredondamento

        // Ajuste para garantir que os minutos não ultrapassem 59
        if (minutos >= 60) {
            horas++;
            minutos -= 60;
        }

        return String.format("%02d:%02d", horas, minutos);
    }
}
