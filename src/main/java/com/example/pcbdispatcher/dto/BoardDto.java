package com.example.pcbdispatcher.dto;

import com.example.pcbdispatcher.enums.BoardStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;

    @NotBlank
    private String name;

    private BoardStatus status;

    private LocalDateTime createdAt;
}
