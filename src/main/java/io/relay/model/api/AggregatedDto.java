package io.relay.model.api;

import java.util.List;

public class AggregatedDto {

    private List<InvoiceDto> invoiceDto;
    private List<CreditNoteDto> creditNoteDto;

    public AggregatedDto(List<InvoiceDto> invoiceDto, List<CreditNoteDto> creditNoteDto) {
        this.invoiceDto = invoiceDto;
        this.creditNoteDto = creditNoteDto;
    }

    public AggregatedDto() {

    }

    public List<InvoiceDto> getInvoiceDto() {
        return invoiceDto;
    }

    public void setInvoiceDto(List<InvoiceDto> invoiceDto) {
        this.invoiceDto = invoiceDto;
    }

    public List<CreditNoteDto> getCreditNoteDto() {
        return creditNoteDto;
    }

    public void setCreditNoteDto(List<CreditNoteDto> creditNoteDto) {
        this.creditNoteDto = creditNoteDto;
    }
}
