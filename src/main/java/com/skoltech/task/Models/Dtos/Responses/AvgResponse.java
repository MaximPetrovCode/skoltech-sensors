package com.skoltech.task.Models.Dtos.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AvgResponse {
    private Integer object_id;
    private Double value;
}
