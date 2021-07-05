package io.relay.service;

import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.AggregatedModel;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;
import io.relay.repository.CreditNoteRepository;
import io.relay.repository.InvoiceRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelayService {

    private final InvoiceRepository invoiceRepository;
    private final CreditNoteRepository creditNoteRepository;
    private final ModelMapper mapper;

    @Autowired
    public RelayService(InvoiceRepository invoiceRepository, CreditNoteRepository creditNoteRepository, ModelMapper mapper) {
        this.invoiceRepository = invoiceRepository;
        this.creditNoteRepository = creditNoteRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<InvoiceDto> saveInvoices(List<InvoiceDto> invoiceDtos) {

        List<Invoice> invoices = mapper.map(invoiceDtos, new TypeToken<List<Invoice>>() {
        }.getType());

//        final List<Invoice> invoices = invoiceDtos.stream()
//            .map(invoiceDto -> mapper.map(invoiceDto, Invoice.class))
//            .collect(Collectors.toList());
        final List<Invoice> createdInvoices = invoiceRepository.saveAll(invoices);
        return mapper.map(createdInvoices, new TypeToken<List<InvoiceDto>>() {
        }.getType());
    }

    public List<CreditNoteDto> saveCreditNotes(List<CreditNoteDto> creditNoteDtos) {
        List<CreditNote> creditNotes = mapper.map(creditNoteDtos, new TypeToken<List<CreditNote>>() {
        }.getType());

        final List<CreditNote> createdCreditNotes = creditNoteRepository.saveAll(creditNotes);
        return mapper.map(createdCreditNotes, new TypeToken<List<CreditNoteDto>>() {
        }.getType());
    }

    public List<AggregatedDto> getAggregatedView() {
        final List<Invoice> invoiceList = invoiceRepository.findByOrderByCreatedAtDesc();
        final List<CreditNote> creditNoteList = creditNoteRepository.findByOrderByCreatedAtDesc();

        AggregatedModel aggregatedModel = new AggregatedModel();
        aggregatedModel.setInvoice(invoiceList);
        aggregatedModel.setCreditNote(creditNoteList);

        return mapper.map(aggregatedModel, new TypeToken<AggregatedDto>() {
        }.getType());
    }
}
