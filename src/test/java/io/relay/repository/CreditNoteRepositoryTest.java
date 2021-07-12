package io.relay.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.data.domain.Sort.Direction.DESC;

import io.relay.model.entity.CreditNote;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CreditNoteRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CreditNoteRepository creditNoteRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(creditNoteRepository).isNotNull();
    }

    @Test
    public void findAllCreditNotes() {
        final List<CreditNote> creditNoteList = new ArrayList<>();

        CreditNote creditNote1 = new CreditNote();
        creditNote1.setId(UUID.randomUUID());
        creditNote1.setNumber("INVC-01");
        creditNote1.setValue(BigDecimal.valueOf(130.99));
        creditNote1.setCreatedAt(Instant.now());

        CreditNote creditNote2 = new CreditNote();
        creditNote2.setId(UUID.randomUUID());
        creditNote2.setNumber("INVC-02");
        creditNote2.setValue(BigDecimal.valueOf(100.99));
        creditNote2.setCreatedAt(Instant.now());

        creditNoteList.add(creditNote1);
        creditNoteList.add(creditNote2);

        final List<CreditNote> creditNotes = creditNoteRepository.saveAll(creditNoteList);
        assertNotNull(creditNoteRepository.findAll(Sort.by(DESC, "createdAt")));
        assertThat(creditNotes.size()).isEqualTo(2);
        assertThat(creditNoteList.get(0)).isEqualToComparingOnlyGivenFields(creditNote1, "number", "value");
    }

}
