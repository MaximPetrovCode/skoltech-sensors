package com.skoltech.task.Models.Dtos.Responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DataResponse {
    @NotNull(message = "ID обьекта")
    public Integer objectId;
    @NotNull(message = "ID датчика")
    public Integer sensorId;
    @NotNull(message = "Время (timestamp)")
    public Long time;
    @NotNull(message = "Значение")
    public Double value;
}
