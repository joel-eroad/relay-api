package io.relay.model.entity;

import java.util.List;

public class AggregatedModel {

    private List<Invoice> invoices;
    private List<CreditNote> creditNotes;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<CreditNote> getCreditNotes() {
        return creditNotes;
    }

    public void setCreditNotes(List<CreditNote> creditNotes) {
        this.creditNotes = creditNotes;
    }
}
