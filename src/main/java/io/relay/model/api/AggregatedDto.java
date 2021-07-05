package io.relay.model.api;

public class AggregatedDto {

    private InvoiceDto invoiceDto;
    private CreditNoteDto creditNoteDto;

    private AggregatedDto() {

    }


    public AggregatedDto(InvoiceDto invoiceDto, CreditNoteDto creditNoteDto) {
        this.invoiceDto = invoiceDto;
        this.creditNoteDto = creditNoteDto;
    }

    public InvoiceDto getInvoiceDto() {
        return invoiceDto;
    }

    public void setInvoiceDto(InvoiceDto invoiceDto) {
        this.invoiceDto = invoiceDto;
    }

    public CreditNoteDto getCreditNoteDto() {
        return creditNoteDto;
    }

    public void setCreditNoteDto(CreditNoteDto creditNoteDto) {
        this.creditNoteDto = creditNoteDto;
    }
}
