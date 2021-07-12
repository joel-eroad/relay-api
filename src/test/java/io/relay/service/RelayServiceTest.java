package io.relay.service;

import io.relay.configuration.ServiceConfig;
import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.AggregatedModel;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;
import io.relay.repository.CreditNoteRepository;
import io.relay.repository.InvoiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.relay.utils.TestUtils.createCreditNoteEntities;
import static io.relay.utils.TestUtils.createInvoiceEntities;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {ServiceConfig.class, RelayService.class}, webEnvironment = NONE)
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

        assertThat(actual).hasSameSizeAs(invoiceEntities).containsExactlyElementsOf(invoiceToCreateDtos);
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

        assertThat(actual).hasSameSizeAs(creditNoteEntities).containsExactlyElementsOf(creditNoteDtos);
        verify(creditNoteRepository).saveAll(creditNoteEntities);
    }

    @Test
    void testGetAggregatedView() {
        final List<CreditNote> creditNoteEntities = createCreditNoteEntities();
        final List<Invoice> invoiceEntities = createInvoiceEntities();

        when(invoiceRepository.findAll(any(Sort.class))).thenReturn(invoiceEntities);
        when(creditNoteRepository.findAll(any(Sort.class))).thenReturn(creditNoteEntities);

        final AggregatedDto actual = relayService.getAggregatedView();

        assertThat(actual.getCreditNotes()).hasSameSizeAs(creditNoteEntities);
        assertThat(actual.getInvoices()).hasSameSizeAs(invoiceEntities);
        verify(creditNoteRepository).findAll(any(Sort.class));
        verify(invoiceRepository).findAll(any(Sort.class));
    }
}
