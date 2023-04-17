package com.example.technomarketproject.model.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
    private Object message;
    private int status;
    private LocalDateTime time;
}

