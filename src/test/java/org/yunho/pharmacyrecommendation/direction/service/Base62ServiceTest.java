package org.yunho.pharmacyrecommendation.direction.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Base62ServiceTest {

    private Base62Service base62Service;

    @BeforeEach
    void setUp() {
        base62Service = new Base62Service();
    }

    @Test
    void testBase62EncoderDecoder() {
        // Given
        long num = 5;

        // When
        String encodedId = base62Service.encodeDirectionId(num);
        long decodedId = base62Service.decodeDirectionId(encodedId);

        // Then
        assertEquals(num, decodedId);
    }
}
