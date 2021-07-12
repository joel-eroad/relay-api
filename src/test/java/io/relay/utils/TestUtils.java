package io.relay.utils;

import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestUtils {

    public static List<Invoice> createInvoiceEntities() {
        final List<Invoice> invoiceEntities = new ArrayList<>();

        final Invoice invoiceEntity1 = new Invoice();
        final Invoice invoiceEntity2 = new Invoice();

        invoiceEntity1.setId(UUID.randomUUID());
        invoiceEntity1.setNumber("INVC-01");
        invoiceEntity1.setValue(BigDecimal.valueOf(100.00));
        invoiceEntity1.setCreatedAt(new Date());

        invoiceEntity2.setId(UUID.randomUUID());
        invoiceEntity2.setNumber("INVC-02");
        invoiceEntity2.setValue(BigDecimal.valueOf(156.00));
        invoiceEntity2.setCreatedAt(new Date());

        invoiceEntities.add(invoiceEntity1);
        invoiceEntities.add(invoiceEntity2);

        return invoiceEntities.stream().sorted(Comparator.comparing(Invoice::getCreatedAt).reversed())
            .collect(Collectors.toList());
    }

    public static List<CreditNote> createCreditNoteEntities() {
        final List<CreditNote> creditNoteEntities = new ArrayList<>();

        final CreditNote creditNoteEntity1 = new CreditNote();
        final CreditNote creditNoteEntity2 = new CreditNote();

        creditNoteEntity1.setId(UUID.randomUUID());
        creditNoteEntity1.setNumber("CRN-01");
        creditNoteEntity1.setValue(BigDecimal.valueOf(50.00));
        creditNoteEntity1.setCreatedAt(new Date());

        creditNoteEntity2.setId(UUID.randomUUID());
        creditNoteEntity2.setNumber("CRN-02");
        creditNoteEntity2.setValue(BigDecimal.valueOf(56.00));
        creditNoteEntity2.setCreatedAt(new Date());

        creditNoteEntities.add(creditNoteEntity1);
        creditNoteEntities.add(creditNoteEntity2);

        return creditNoteEntities.stream().sorted(Comparator.comparing(CreditNote::getCreatedAt).reversed())
            .collect(Collectors.toList());
    }

    public static List<InvoiceDto> createInvoiceDtos() {
        final List<InvoiceDto> invoiceDtos = new ArrayList<>();

        final InvoiceDto invoiceDto1 = new InvoiceDto();
        final InvoiceDto invoiceDto2 = new InvoiceDto();

        invoiceDto1.setId(UUID.randomUUID());
        invoiceDto1.setNumber("INVC-01");
        invoiceDto1.setValue(BigDecimal.valueOf(100.00));
        invoiceDto1.setCreatedAt(new Date());

        invoiceDto2.setId(UUID.randomUUID());
        invoiceDto2.setNumber("INVC-02");
        invoiceDto2.setValue(BigDecimal.valueOf(156.00));
        invoiceDto2.setCreatedAt(new Date());

        invoiceDtos.add(invoiceDto1);
        invoiceDtos.add(invoiceDto2);

        return invoiceDtos.stream().sorted(Comparator.comparing(InvoiceDto::getCreatedAt).reversed())
            .collect(Collectors.toList());
    }

    public static List<CreditNoteDto> createCreditNoteDtos() {
        final List<CreditNoteDto> creditNoteDtos = new ArrayList<>();

        final CreditNoteDto creditNoteDto1 = new CreditNoteDto();
        final CreditNoteDto creditNoteDto2 = new CreditNoteDto();

        creditNoteDto1.setId(UUID.randomUUID());
        creditNoteDto1.setNumber("CRN-01");
        creditNoteDto1.setValue(BigDecimal.valueOf(50.00));
        creditNoteDto1.setCreatedAt(new Date());

        creditNoteDto2.setId(UUID.randomUUID());
        creditNoteDto2.setNumber("CRN-02");
        creditNoteDto2.setValue(BigDecimal.valueOf(56.00));
        creditNoteDto2.setCreatedAt(new Date());

        creditNoteDtos.add(creditNoteDto1);
        creditNoteDtos.add(creditNoteDto2);

        return creditNoteDtos.stream().sorted(Comparator.comparing(CreditNoteDto::getCreatedAt).reversed())
            .collect(Collectors.toList());
    }
}
