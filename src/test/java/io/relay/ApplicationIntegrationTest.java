package io.relay;

import static io.relay.utils.TestUtils.createCreditNoteEntities;
import static io.relay.utils.TestUtils.createInvoiceEntities;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;
import io.relay.repository.CreditNoteRepository;
import io.relay.repository.InvoiceRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@Tag("integration")
@Tag("slow")
@ComponentScan(basePackages = "io.relay")
public class ApplicationIntegrationTest {

    @Value("${resource.path}")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Autowired
    private JacksonTester<List<InvoiceDto>> responseInvoice;

    @Autowired
    private JacksonTester<List<CreditNoteDto>> responseCreditNote;

    @Autowired
    private JacksonTester<AggregatedDto> aggregatedResponse;

    @BeforeEach
    void setUp() {
        final List<Invoice> invoiceEntities = createInvoiceEntities();
        final List<CreditNote> creditNoteEntities = createCreditNoteEntities();
        invoiceRepository.saveAll(invoiceEntities);
        creditNoteRepository.saveAll(creditNoteEntities);
    }

    @AfterEach
    void tearDown() {
        invoiceRepository.deleteAll();
        creditNoteRepository.deleteAll();
    }

    @Test
    void testCreateInvoices() throws Exception {
        InvoiceDto createInvoice1 = new InvoiceDto();
        createInvoice1.setNumber("INVC-01");
        createInvoice1.setValue(BigDecimal.valueOf(100.00));

        InvoiceDto createInvoice2 = new InvoiceDto();
        createInvoice2.setNumber("INVC-02");
        createInvoice2.setValue(BigDecimal.valueOf(156.00));

        List<InvoiceDto> invoices = new ArrayList<>() {
            {
                add(createInvoice1);
                add(createInvoice2);
            }
        };

        MvcResult result = mockMvc.perform(post(basePath + "/invoices")
            .contentType(APPLICATION_JSON)
            .content(responseInvoice.write(invoices).getJson()))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();
        final List<InvoiceDto> actual = responseInvoice.parse(result.getResponse().getContentAsString()).getObject();

        assertThat(actual).hasSize(2);
        assertAll("create invoices",
            () -> assertThat(actual.get(0).getId()).isNotNull(),
            () -> assertThat(actual.get(1).getId()).isNotNull(),
            () -> assertThat(actual.get(0).getCreatedAt()).isNotNull(),
            () -> assertThat(actual.get(1).getCreatedAt()).isNotNull());
    }

    @Test
    void testCreateCreditNotes() throws Exception {
        CreditNoteDto createCreditNote1 = new CreditNoteDto();
        createCreditNote1.setNumber("CRN-01");
        createCreditNote1.setValue(BigDecimal.valueOf(50.00));

        CreditNoteDto createCreditNote2 = new CreditNoteDto();
        createCreditNote2.setNumber("CRN-02");
        createCreditNote2.setValue(BigDecimal.valueOf(56.00));

        List<CreditNoteDto> creditNotes = new ArrayList<>() {
            {
                add(createCreditNote1);
                add(createCreditNote2);
            }
        };

        MvcResult result = mockMvc.perform(post(basePath + "/creditnotes")
            .contentType(APPLICATION_JSON)
            .content(responseCreditNote.write(creditNotes).getJson()))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();
        final List<CreditNoteDto> actual = responseCreditNote.parse(result.getResponse().getContentAsString()).getObject();

        assertThat(actual).hasSize(2);
        assertAll("create credit notes",
            () -> assertThat(actual.get(0).getId()).isNotNull(),
            () -> assertThat(actual.get(1).getId()).isNotNull(),
            () -> assertThat(actual.get(0).getCreatedAt()).isNotNull(),
            () -> assertThat(actual.get(1).getCreatedAt()).isNotNull());
    }

    @Test
    void testGetAggregatedView() throws Exception {
        MvcResult result = mockMvc.perform(get(basePath + "/getAggregatedView"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();
        AggregatedDto actual = aggregatedResponse.parse(result.getResponse().getContentAsString()).getObject();

        assertThat(actual).isNotNull();
        assertThat(actual.getInvoices()).hasSize(2);
        assertThat(actual.getCreditNotes()).hasSize(2);
    }


    @Test
    void testInvalidContentTypeError() throws Exception {
        List<InvoiceDto> invoices = new ArrayList<>();
        InvoiceDto invoiceDto = new InvoiceDto();
        invoiceDto.setValue(null);
        invoices.add(invoiceDto);

        mockMvc.perform(post(basePath + "/invoices")
            .contentType(APPLICATION_XML)
            .content(responseInvoice.write(invoices).getJson()))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(APPLICATION_JSON))
            .andReturn();
    }
}
