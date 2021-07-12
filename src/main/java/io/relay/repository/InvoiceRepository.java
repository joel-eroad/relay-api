package io.relay.repository;

import io.relay.model.entity.Invoice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    @Override
    List<Invoice> findAll(Sort sort);

    boolean existsByNumber(String number);
}
