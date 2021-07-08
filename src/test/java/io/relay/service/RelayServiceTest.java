package io.relay.service;

import io.relay.configuration.ServiceConfig;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;
import io.relay.repository.CreditNoteRepository;
import io.relay.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ServiceConfig.class, RelayService.class}, webEnvironment = WebEnvironment.NONE)
class RelayServiceTest {

    @MockBean
    private CreditNoteRepository creditNoteRepository;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ModelMapper mapper;

    private RelayService relayService;

    @BeforeEach
    void setUp() {
        relayService = new RelayService(invoiceRepository, creditNoteRepository, mapper);
    }

    @Test
    void testSaveInvoices() {
        final List<Invoice> invoiceEntities = createInvoiceEntities();

        when(invoiceRepository.saveAll(invoiceEntities)).thenReturn(invoiceEntities);

        final List<InvoiceDto> invoiceToCreateDtos = invoiceEntities.stream()
            .map(invoice -> mapper.map(invoice, InvoiceDto.class))
            .collect(Collectors.toList());

        final List<InvoiceDto> actual = relayService.saveInvoices(invoiceToCreateDtos);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).hasSize(invoiceEntities.size()).hasSameElementsAs(invoiceToCreateDtos);

        verify(invoiceRepository).saveAll(invoiceEntities);

    }

    @Test
    void testSaveCreditNotes() {
        final List<CreditNote> creditNoteEntities = createCreditNoteEntities();

        when(creditNoteRepository.saveAll(creditNoteEntities)).thenReturn(creditNoteEntities);

        final List<CreditNoteDto> creditNoteDtos = creditNoteEntities.stream()
            .map(creditNote -> mapper.map(creditNote, CreditNoteDto.class))
            .collect(Collectors.toList());

        final List<CreditNoteDto> actual = relayService.saveCreditNotes(creditNoteDtos);
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).hasSize(creditNoteEntities.size()).hasSameElementsAs(creditNoteDtos);

        verify(creditNoteRepository).saveAll(creditNoteEntities);
    }

    @Test
    void testGetAggregatedView() {
    }

    private List<Invoice> createInvoiceEntities() {
        Invoice invoiceEntity1 = new Invoice();
        Invoice invoiceEntity2 = new Invoice();

        invoiceEntity1.setId(UUID.randomUUID());
        invoiceEntity1.setNumber("INVC-01");
        invoiceEntity1.setValue(BigDecimal.valueOf(100.00));
        invoiceEntity1.setCreatedAt(new Date());

        invoiceEntity2.setId(UUID.randomUUID());
        invoiceEntity2.setNumber("INVC-02");
        invoiceEntity2.setValue(BigDecimal.valueOf(156.00));
        invoiceEntity2.setCreatedAt(new Date());

        return List.of(invoiceEntity1, invoiceEntity2);
    }


    private List<CreditNote> createCreditNoteEntities() {
        CreditNote creditNoteEntity1 = new CreditNote();
        CreditNote creditNoteEntity2 = new CreditNote();

        creditNoteEntity1.setId(UUID.randomUUID());
        creditNoteEntity1.setNumber("CRN-01");
        creditNoteEntity1.setValue(BigDecimal.valueOf(50.00));
        creditNoteEntity1.setCreatedAt(new Date());

        creditNoteEntity2.setId(UUID.randomUUID());
        creditNoteEntity2.setNumber("CRN-02");
        creditNoteEntity2.setValue(BigDecimal.valueOf(56.00));
        creditNoteEntity2.setCreatedAt(new Date());

        return List.of(creditNoteEntity1, creditNoteEntity2);
    }
}
