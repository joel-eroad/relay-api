package io.relay;

import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationTest {

    @Test
    void testTimeZoneIsUtc() {
        String timezone = "Pacific/Auckland";
        TimeZone.setDefault(TimeZone.getTimeZone(timezone));
        assertThat(TimeZone.getDefault().getID()).isEqualTo(timezone);

        Application app = new Application();
        app.init();
        assertThat(TimeZone.getDefault().getID()).isEqualTo("UTC");
    }
}
