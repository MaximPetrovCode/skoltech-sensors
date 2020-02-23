package com.skoltech.task.Utils;

import com.skoltech.task.Models.Dtos.Responses.DataResponse;
import com.skoltech.task.Models.Entities.ValueEntity;

public class Mapper {
    public static DataResponse mapEntityToResponse(ValueEntity entity) {
        return DataResponse.builder()
                .objectId(entity.getObject().getId())
                .sensorId(entity.getSensor().getId())
                .time(entity.getTime())
                .value(entity.getValue())
                .build();

    }
}
