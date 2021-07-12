package io.relay.repository;

import io.relay.model.entity.CreditNote;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditNoteRepository extends JpaRepository<CreditNote, UUID> {

    @Override
    List<CreditNote> findAll(Sort sort);

    boolean existsByNumber(String number);
}
