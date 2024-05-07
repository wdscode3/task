package org.wds.taskmanager.domain;

import lombok.*;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private UUID idExterno;

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @PrePersist
    public void prePersist() {
        this.idExterno = UUID.randomUUID();
        this.inicio = now();
    }

    public Atividade(Task task) {
        this.task = task;
    }

    public boolean isPendente() {
        return fim == null;
    }

    public void finalizar() {
        if(fim == null)
            this.fim = now();
    }

    public Long getWorkToMillis() {
        var f = ofNullable(fim).orElse(now());

        Duration duration = Duration.between(inicio, f);

        return duration.toMillis();
    }



}
