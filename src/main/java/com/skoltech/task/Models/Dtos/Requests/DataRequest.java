package com.skoltech.task.Models.Dtos.Requests;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class DataRequest {
    @NotNull(message = "ID обьекта")
    public Integer objectId;
    @NotNull(message = "ID датчика")
    public Integer sensorId;
    @NotNull(message = "Время (timestamp)")
    public Long time;
    @NotNull(message = "Значение")
    public Double value;
}
