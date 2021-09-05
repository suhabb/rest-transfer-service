package integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.application_service.AccountApplicationService;
import com.transfer.controller.AccountController;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import com.transfer.exception.AccountNotFoundException;
import com.transfer.exception_handler.TransactionServiceExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    private MockMvc mockMvc;

    private AccountApplicationService accountApplicationService = mock(AccountApplicationService.class);

    private ObjectMapper mapper = new ObjectMapper();

    private AccountController accountController;

    @BeforeEach
    public void setUp(){
        this.accountController = new AccountController(accountApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup( new AccountController(accountApplicationService))
                .setControllerAdvice(new TransactionServiceExceptionHandler())
                .build();
    }

    @Test
    public void given_valid_account_number_return_status_200() throws Exception {

        AccountDto accountDto = new AccountDto.AccountDtoBuilder()
                .setAccountNumber(3123123)
                .setBalance(100.00)
                .build();
        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(11111)
                .setSourceAccountNumber(22222)
                .build();

        when(accountApplicationService.findByAccountNumber(anyInt()))
                .thenReturn(accountDto);
        this.mockMvc.perform(get("/account/{accountnumber}",3123123)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_number").value("3123123"));

    }

    @Test
    public void given_invalid_account_number_return_status_404() throws Exception {

        when(accountApplicationService.findByAccountNumber(anyInt()))
                .thenThrow(new AccountNotFoundException("Account not found"));
        this.mockMvc.perform(get("/account/{accountnumber}",3123123)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
