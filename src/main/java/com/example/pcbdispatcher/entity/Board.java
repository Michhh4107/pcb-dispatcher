package com.example.pcbdispatcher.entity;

import com.example.pcbdispatcher.enums.BoardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "printed_circuit_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq")
    @SequenceGenerator(name = "board_seq", sequenceName = "board_id_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 64)
    private BoardStatus status;

    private LocalDateTime createdAt;

    @Builder
    public Board(String name, BoardStatus status, LocalDateTime createdAt) {
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
    }
}
