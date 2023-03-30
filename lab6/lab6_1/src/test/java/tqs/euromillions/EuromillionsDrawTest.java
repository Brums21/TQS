package tqs.euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

public class EuromillionsDrawTest {

    private CuponEuromillions sampleCoupon;
    private CuponEuromillions sampleCoupon1;
    private Dip randomDip;

    @BeforeEach
    public void setUp() {
        sampleCoupon = new CuponEuromillions();
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(new Dip(new int[]{1, 2, 3, 48, 49}, new int[]{1, 9}));
        sampleCoupon1 = new CuponEuromillions();
        randomDip = new Dip(new int[]{1, 2, 3, 4, 49}, new int[]{2, 7});
        sampleCoupon1.appendDip(randomDip);
    }

    @DisplayName("test the formatting")
    @Test
    public void testFormatting(){
        String expected = "Dip #1:" + randomDip.format() + "\n";
        String normal = sampleCoupon1.format();
        assertEquals(expected, normal);
    }

    @DisplayName("test the counting dips in cupons")
    @Test
    public void testCoutingDips(){
        int size = 3;
        assertEquals(sampleCoupon.countDips(), size);
    }


    @DisplayName("reports correct matches in a coupon")
    @Test
    public void testCompareBetWithDrawToGetResults() {
        Dip winningDip, matchesFound;

        // test for full match, using the 3rd dip in the coupon as the Draw results
        winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);

        assertEquals(winningDip, matchesFound, "expected the bet and the matches found to be equal");

        // test for no matches at all
        testDraw = new EuromillionsDraw(new Dip(new int[]{9, 10, 11, 12, 13}, new int[]{2, 3}));
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);
        // compare empty with the matches found
        assertEquals( new Dip(), matchesFound);
    }

}
