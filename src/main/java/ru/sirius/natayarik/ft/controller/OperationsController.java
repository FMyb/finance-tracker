package ru.sirius.natayarik.ft.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sirius.natayarik.ft.data.FullOperationDTO;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.services.OperationService;

import java.util.List;

/**
 * @author Yaroslav Ilin
 */

@RestController
@RequestMapping("/api/operations")
public class OperationsController {
    private final OperationService operationService;

    @Autowired
    public OperationsController(OperationService operationService) {
        this.operationService = operationService;
    }

    @Operation(summary = "Метод для cоздания операции")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationCreateDTO createOperation(@RequestBody OperationCreateDTO operation) {
        return operationService.create(operation);
    }

    @Operation(summary = "Метод для получения всех операций по кошельку")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FullOperationDTO> getAllOperations(@RequestParam long accountId) {
        return operationService.getAll(accountId);
    }

    @Operation(summary = "Получение операции по ее id")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public FullOperationDTO getOperationFromId(@PathVariable("id") long operationId) {
        return operationService.getFromId(operationId);
    }

    @Operation(summary = "Удалить операцию")
    @DeleteMapping(value = "/{id}")
    public void deleteOperation(@PathVariable("id") int operationId) {
        operationService.delete(operationId);
    }

    @Operation(summary = "Изменить операцию")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationCreateDTO changeOperation(@RequestBody OperationCreateDTO operation) {
        return operationService.change(operation);
    }
}
