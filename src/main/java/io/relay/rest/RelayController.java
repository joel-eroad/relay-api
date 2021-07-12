package io.relay.rest;

import io.relay.model.api.AggregatedDto;
import io.relay.model.api.CreditNoteDto;
import io.relay.model.api.InvoiceDto;
import io.relay.model.api.OnCreate;
import io.relay.service.RelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${resource.path}")
public class RelayController {

    private final RelayService relayService;

    @Autowired
    public RelayController(RelayService relayService) {
        this.relayService = relayService;
    }

    @PostMapping(value = "/invoices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<InvoiceDto> createInvoices(@Validated(OnCreate.class) @Valid @RequestBody List<InvoiceDto> invoices) {
        return relayService.saveInvoices(invoices);
    }

    @PostMapping(value = "/creditnotes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<CreditNoteDto> createCreditNotes(@Validated(OnCreate.class) @Valid @RequestBody List<CreditNoteDto> creditNotes) {
        return relayService.saveCreditNotes(creditNotes);
    }

    @GetMapping(value = "/getAggregatedView", produces = MediaType.APPLICATION_JSON_VALUE)
    public AggregatedDto getAggregatedInformation() {
        return relayService.getAggregatedView();
    }
}
