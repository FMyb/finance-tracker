package ru.sirius.natayarik.ft.cache;

import org.springframework.stereotype.Component;
import ru.sirius.natayarik.ft.data.OperationCreateDTO;
import ru.sirius.natayarik.ft.services.CurrentUserService;
import ru.sirius.natayarik.ft.services.OperationService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Natalia Nikonova
 */
@Component
public class OperationCache {
    private final Map<String, OperationCreateDTO> operationCash = new HashMap<>();
    private final OperationService operationService;
    private final CurrentUserService currentUserService;

    public OperationCache(OperationService operationService, CurrentUserService currentUserService) {
        this.operationService = operationService;
        this.currentUserService = currentUserService;
    }

    public void createOperation() {
        operationCash.put(currentUserService.getUser().getName(), new OperationCreateDTO());
    }
    public void addAmount(BigDecimal amount) {
        operationCash.get(currentUserService.getUser().getName()).setAmount(amount);
    }

    public void addCategory(long categoryId) {
        operationCash.get(currentUserService.getUser().getName()).setCategoryId(categoryId);
    }

    public void addAccount(long accountId) {
        operationCash.get(currentUserService.getUser().getName()).setAccountId(accountId);
    }

    public OperationCreateDTO saveOperation() {
        return operationService.create(operationCash.get(currentUserService.getUser().getName()));
    }
}
