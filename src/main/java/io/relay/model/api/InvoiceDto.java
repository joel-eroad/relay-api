package io.relay.model.api;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto implements OnCreate, OnUpdate {

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    private UUID id;

    @JsonProperty(value = "invoiceNumber", required = true)
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private String number;

    @JsonProperty(required = true)
    @JsonFormat(shape = STRING)
    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    private double value;

    @NotNull(groups = OnUpdate.class)
    @Null(groups = OnCreate.class)
    @JsonFormat(shape = STRING)
    private long createdAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceDto)) {
            return false;
        }
        InvoiceDto that = (InvoiceDto) o;
        return Double.compare(that.getValue(), getValue()) == 0 &&
            getCreatedAt() == that.getCreatedAt() &&
            getId().equals(that.getId()) &&
            getNumber().equals(that.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getValue(), getCreatedAt());
    }
}
