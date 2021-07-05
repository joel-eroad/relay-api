package io.relay.model.entity;

import java.util.List;

public class AggregatedModel {

    private List<Invoice> invoice;
    private List<CreditNote> creditNote;

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public void setCreditNote(List<CreditNote> creditNote) {
        this.creditNote = creditNote;
    }
}
