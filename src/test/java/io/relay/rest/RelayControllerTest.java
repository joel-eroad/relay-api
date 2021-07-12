package io.relay.rest;

import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.service.RelayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.relay.utils.TestUtils.createCreditNoteDtos;
import static io.relay.utils.TestUtils.createInvoiceDtos;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {RelayController.class}, webEnvironment = NONE)
@DisplayName("Relay Rest Service Test")
public class RelayControllerTest {

    @MockBean
    private RelayService relayService;

    private RelayController relayController;

    @BeforeEach
    void setUp() {
        relayController = new RelayController(relayService);
    }

    @Test
    void testCreateInvoices() {
        final List<InvoiceDto> testInvoices = createInvoiceDtos();

        InvoiceDto createInvoice1 = new InvoiceDto();
        createInvoice1.setNumber("INVC-01");
        createInvoice1.setValue(BigDecimal.valueOf(100.00));

        InvoiceDto createInvoice2 = new InvoiceDto();
        createInvoice2.setNumber("INVC-02");
        createInvoice2.setValue(BigDecimal.valueOf(156.00));

        List<InvoiceDto> createInvoices = new ArrayList<>() {
            {
                add(createInvoice1);
                add(createInvoice2);
            }
        };

        when(relayService.saveInvoices(createInvoices)).thenReturn(testInvoices);

        final List<InvoiceDto> actual = relayController.createInvoices(createInvoices);

        assertThat(actual).hasSameSizeAs(testInvoices).containsExactlyElementsOf(testInvoices);
        verify(relayService).saveInvoices(createInvoices);
    }

    @Test
    void testCreateCreditNotes() {
        final List<CreditNoteDto> testCreditNotes = createCreditNoteDtos();

        CreditNoteDto createCreditNote1 = new CreditNoteDto();
        createCreditNote1.setNumber("CRN-01");
        createCreditNote1.setValue(BigDecimal.valueOf(50.00));

        CreditNoteDto createCreditNote2 = new CreditNoteDto();
        createCreditNote2.setNumber("CRN-02");
        createCreditNote2.setValue(BigDecimal.valueOf(56.00));

        List<CreditNoteDto> createCreditNotes = new ArrayList<>() {{
            add(createCreditNote1);
            add(createCreditNote2);
        }};

        when(relayService.saveCreditNotes(createCreditNotes)).thenReturn(testCreditNotes);

        final List<CreditNoteDto> actual = relayController.createCreditNotes(createCreditNotes);

        assertThat(actual).hasSameSizeAs(testCreditNotes).containsExactlyElementsOf(testCreditNotes);
        verify(relayService).saveCreditNotes(createCreditNotes);
    }

    @Test
    void getAggregatedInformation() {
        AggregatedDto testAggregatedDto = new AggregatedDto();

        testAggregatedDto.setInvoices(createInvoiceDtos());
        testAggregatedDto.setCreditNotes(createCreditNoteDtos());

        when(relayService.getAggregatedView()).thenReturn(testAggregatedDto);

        final AggregatedDto actual = relayController.getAggregatedInformation();
        assertThat(actual.getInvoices()).hasSameSizeAs(testAggregatedDto.getInvoices()).containsExactlyElementsOf(testAggregatedDto.getInvoices());
        assertThat(actual.getCreditNotes()).hasSameSizeAs(testAggregatedDto.getCreditNotes()).containsExactlyElementsOf(testAggregatedDto.getCreditNotes());
        verify(relayService).getAggregatedView();
    }
}
