package org.wds.taskmanager.domain;

import lombok.*;
import org.wds.taskmanager.domain.enums.StatusEnum;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static org.wds.taskmanager.domain.enums.StatusEnum.FINISHED;
import static org.wds.taskmanager.domain.enums.StatusEnum.RUNNING;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private LocalDateTime inicio;

    private LocalDateTime fim;

    private Long originalEstimate;
    
    private Long remainingWork;
    
    private Long completedWork;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private UUID idExterno;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Atividade> atividades;

    @PrePersist
    public void prePersist() {
        this.idExterno = UUID.randomUUID();
        this.status = RUNNING;
        this.inicio = now();
        this.atividades = List.of(new Atividade(this));
    }

    public boolean isRunning() {
        return RUNNING.equals(status);
    }

    public boolean isFinished() {
        return FINISHED.equals(status);
    }

}
