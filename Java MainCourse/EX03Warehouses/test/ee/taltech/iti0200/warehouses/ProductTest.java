package ee.taltech.iti0200.warehouses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductTest {
    Product book;
    private static final int PRICESIXBOOKS = 6;
    private static final int PRICEELEVENBOOKS = 11;

    @BeforeEach
    void setUp() {
        book = new Product("book1", BigDecimal.valueOf(1), BigDecimal.valueOf(3),
                BigDecimal.valueOf(PRICESIXBOOKS));
    }

    @Test
    void getProductDescription() {
        assertEquals("book1 6 € 1 kg", book.getProductDescription());
    }

    @Test
    void getName() {
        assertEquals("book1", book.getName());
    }

    @Test
    void setName() {
        book.setName("new book");
        assertEquals("new book", book.getName());
    }

    @Test
    void getWeight() {
        assertEquals(BigDecimal.valueOf(1), book.getWeight());
    }

    @Test
    void setWeight() {
        book.setWeight(BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), book.getWeight());
    }

    @Test
    void getNetPrice() {
        assertEquals(BigDecimal.valueOf(3), book.getNetPrice());
    }

    @Test
    void setNetPrice() {
        book.setNetPrice(BigDecimal.valueOf(PRICESIXBOOKS));
        assertEquals(BigDecimal.valueOf(PRICESIXBOOKS), book.getNetPrice());
    }

    @Test
    void getGrossPrice() {
        assertEquals(BigDecimal.valueOf(PRICESIXBOOKS), book.getGrossPrice());
    }

    @Test
    void setGrossPrice() {
        book.setGrossPrice(BigDecimal.valueOf(PRICEELEVENBOOKS));
        assertEquals(BigDecimal.valueOf(PRICEELEVENBOOKS), book.getGrossPrice());
    }

    @Test
    void getProfitabilityPercentage() {
        BigDecimal result;
        result = (book.getGrossPrice().subtract(book.getNetPrice())).divide(book.getGrossPrice(), 4,
                RoundingMode.CEILING).multiply(BigDecimal.valueOf(100));
        assertEquals(result, book.getProfitabilityPercentage());
    }
}
