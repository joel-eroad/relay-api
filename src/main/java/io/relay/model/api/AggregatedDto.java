package io.relay.model.api;

import java.util.List;

public class AggregatedDto {

    private List<InvoiceDto> invoices;
    private List<CreditNoteDto> creditNotes;

    public AggregatedDto(List<InvoiceDto> invoices, List<CreditNoteDto> creditNotes) {
        this.invoices = invoices;
        this.creditNotes = creditNotes;
    }

    public AggregatedDto() {

    }

    public List<InvoiceDto> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceDto> invoices) {
        this.invoices = invoices;
    }

    public List<CreditNoteDto> getCreditNotes() {
        return creditNotes;
    }

    public void setCreditNotes(List<CreditNoteDto> creditNotes) {
        this.creditNotes = creditNotes;
    }
}
