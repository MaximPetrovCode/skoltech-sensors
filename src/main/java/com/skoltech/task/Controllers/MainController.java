package com.skoltech.task.Controllers;

import com.skoltech.task.Models.Dtos.Requests.DataRequest;
import com.skoltech.task.Models.Dtos.Responses.AvgResponse;
import com.skoltech.task.Models.Dtos.Responses.DataResponse;
import com.skoltech.task.Models.Entities.Helpers.AvgValue;
import com.skoltech.task.Models.Entities.ValueEntity;
import com.skoltech.task.Services.DataService;
import com.skoltech.task.Utils.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MainController {

    private final DataService dataService;

    public MainController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/save")
    public Integer saveData(@RequestBody List<DataRequest> objects) {
        return dataService.saveValues(objects);
    }

    @GetMapping("/latest")
    public List<DataResponse> getLatest(@RequestParam Integer id) {
        Optional<List<ValueEntity>> latestById = dataService.getLatestById(id);
        if (latestById.isPresent()) {
            return latestById.get().stream().map(Mapper::mapEntityToResponse).collect(Collectors.toList());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no result for this object");
    }

    @GetMapping("/history")
    public List<DataResponse> getHistory(@RequestParam Integer id,
                                         @RequestParam Long from,
                                         @RequestParam Long to) {
        Optional<List<ValueEntity>> entities = dataService.getByInterval(id, from, to);
        return entities.map(valueEntities -> valueEntities.stream()
                .map(Mapper::mapEntityToResponse).collect(Collectors.toCollection(ArrayList::new)))
                .orElseGet(ArrayList::new);
    }

    @GetMapping("/avg")
    public List<AvgResponse> getAvg() {
        Optional<List<AvgValue>> avg = dataService.getAvg();
        if (avg.isPresent()) {
            return avg.get().stream().map(entity -> AvgResponse.builder()
                    .object_id(entity.getObjectId())
                    .value(entity.getValue())
                    .build()).collect(Collectors.toList());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Average values can't be calculated");
    }
}
