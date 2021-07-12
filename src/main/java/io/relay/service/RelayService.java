package io.relay.service;

import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.entity.AggregatedModel;
import io.relay.model.entity.CreditNote;
import io.relay.model.entity.Invoice;
import io.relay.repository.CreditNoteRepository;
import io.relay.repository.InvoiceRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.DESC;

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
        final List<InvoiceDto> exists = invoiceDtos
            .stream()
            .filter(invoiceDto -> invoiceRepository.existsByNumber(invoiceDto.getNumber()))
            .collect(Collectors.toList());

        if (!exists.isEmpty()) {
            throw new ConstraintViolationException("These invoice numbers already exists", new HashSet<>());
        }

        List<Invoice> invoices = mapper.map(invoiceDtos, new TypeToken<List<Invoice>>() {
        }.getType());

        final List<Invoice> createdInvoices = invoiceRepository.saveAll(invoices);
        return mapper.map(createdInvoices, new TypeToken<List<InvoiceDto>>() {
        }.getType());
    }

    @Transactional
    public List<CreditNoteDto> saveCreditNotes(List<CreditNoteDto> creditNoteDtos) {
        final List<CreditNoteDto> exists = creditNoteDtos
            .stream()
            .filter(creditNoteDto -> creditNoteRepository.existsByNumber(creditNoteDto.getNumber()))
            .collect(Collectors.toList());

        if (!exists.isEmpty()) {
            throw new ConstraintViolationException("These credit numbers already exists", new HashSet<>());
        }

        List<CreditNote> creditNotes = mapper.map(creditNoteDtos, new TypeToken<List<CreditNote>>() {
        }.getType());

        final List<CreditNote> createdCreditNotes = creditNoteRepository.saveAll(creditNotes);
        return mapper.map(createdCreditNotes, new TypeToken<List<CreditNoteDto>>() {
        }.getType());
    }

    @Transactional(readOnly = true)
    public AggregatedDto getAggregatedView() {
        final List<Invoice> invoiceList = invoiceRepository.findAll(Sort.by(DESC, "createdAt"));
        final List<CreditNote> creditNoteList = creditNoteRepository.findAll(Sort.by(DESC, "createdAt"));

        AggregatedModel aggregatedModel = new AggregatedModel();
        aggregatedModel.setInvoices(invoiceList);
        aggregatedModel.setCreditNotes(creditNoteList);

        final TypeMap<AggregatedModel, AggregatedDto> typeMap1 = mapper.createTypeMap(AggregatedModel.class,
            AggregatedDto.class);

        typeMap1
            .addMappings(mapping -> mapping
                .map(AggregatedModel::getInvoices, AggregatedDto::setInvoices))
            .addMappings(mapping -> mapping.map(AggregatedModel::getCreditNotes, AggregatedDto::setCreditNotes));

        return typeMap1.map(aggregatedModel);
    }
}
