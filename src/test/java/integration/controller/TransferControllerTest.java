package integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfer.application_service.TransferApplicationService;
import com.transfer.controller.TransferController;
import com.transfer.data_transfer.AccountDto;
import com.transfer.data_transfer.TransferDto;
import com.transfer.exception.InvalidDataException;
import com.transfer.exception_handler.TransactionServiceExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class TransferControllerTest {


    private MockMvc mockMvc;

    private TransferApplicationService transferApplicationService = mock(TransferApplicationService.class);

    private ObjectMapper mapper = new ObjectMapper();

    private TransferController transferController;

    @BeforeEach
    public void setUp(){
        this.transferController = new TransferController(transferApplicationService);
        mockMvc = MockMvcBuilders.standaloneSetup( new TransferController(transferApplicationService))
                .setControllerAdvice(new TransactionServiceExceptionHandler())
                .build();
    }

    @Test
    public void given_valid_input_return_status_200() throws Exception {

       AccountDto accountDto = new AccountDto.AccountDtoBuilder()
               .setAccountNumber(3123123)
               .setBalance(100.00)
               .build();
       TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
               .setAmount(20.00)
               .setDestinationAccountNumber(11111)
               .setSourceAccountNumber(22222)
               .build();

        when(transferApplicationService.transferAmount(any(TransferDto.class)))
                .thenReturn(accountDto);
       this.mockMvc.perform(post("/transfer")
               .contentType(MediaType.APPLICATION_JSON)
               .content(mapper.writeValueAsString(transferDTO))
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.account_number").value("3123123"));

   }

    @Test
    public void given_invalid_input_return_status_400_bad_request() throws Exception {

        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(11111)
                .setSourceAccountNumber(22222)
                .build();

        when(transferApplicationService.transferAmount(any(TransferDto.class)))
                .thenThrow(new InvalidDataException("Account number cannot be empty"));
        this.mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transferDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("400"));

    }

    @Test
    public void given_invalid_input_return_status_500_internal_server_error() throws Exception {

        TransferDto transferDTO = new TransferDto.TransferDtoBuilder()
                .setAmount(20.00)
                .setDestinationAccountNumber(11111)
                .setSourceAccountNumber(22222)
                .build();

        when(transferApplicationService.transferAmount(any(TransferDto.class)))
                .thenThrow(new RuntimeException("Internal error occurred"));
        this.mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transferDTO))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("500"));

    }
}
