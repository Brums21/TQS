package homework.HW1.UnitTest;

import groovy.util.logging.Slf4j;
import homework.HW1.Cache.Cache;
import homework.HW1.Model.AirCondition;
import org.junit.jupiter.api.*;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
public class CacheUnitTest {

    public static Cache cache = new Cache();
    public static HashMap<AirCondition, LocalDateTime> currentMap = new HashMap<>();

    public static HashMap<List<AirCondition>, LocalDateTime> daysMap = new HashMap<>();

    public static LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

    Double latitude = 50.0;
    Double longitude = -8.1;

    LocalDateTime dateStart = LocalDateTime.ofInstant(Instant.ofEpochSecond(1677715200), ZoneOffset.UTC);
    LocalDateTime dateEnd = LocalDateTime.ofInstant(Instant.ofEpochSecond(1677718800), ZoneOffset.UTC);

    AirCondition current = new AirCondition(50.0, -8.1, now, 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64);

    ArrayList<AirCondition> list = new ArrayList<>();

    @BeforeEach
    public void init(){

        list.add(new AirCondition(50.0, -8.1, dateStart, 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        list.add(new AirCondition(50.0, -8.1, dateEnd, 226.97, 0.0, 2.33, 79.39, 1.25, 1.67, 2.41, 0.57));

        cache.addToCacheCurrent(current);
        cache.addToCacheDays(list);

    }

    @AfterEach
    public void reset(){
        cache = new Cache();
    }

    @Test
    public void testValidCheckIfInCacheCurrent(){

        AirCondition actual = cache.checkIfInCacheCurrent(latitude, longitude);

        AirCondition expected = new AirCondition(50.0, -8.1, now, 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64);

        Assertions.assertEquals(expected.toString(), actual.toString());
        Assertions.assertEquals(cache.getHits(), 1);
        Assertions.assertEquals(cache.getMisses(), 0);
    }

    @Test
    public void testInvalidCheckIfInCacheCurrent(){

        AirCondition actual = cache.checkIfInCacheCurrent(latitude + 1, longitude);

        assertNull(actual);
        Assertions.assertEquals(cache.getHits(), 0);
        Assertions.assertEquals(cache.getMisses(), 1);
    }

    @Test
    public void testCacheExpired(){
        LocalDateTime timeAfterTTL = LocalDateTime.now(ZoneOffset.UTC).plusSeconds(241);
        Assertions.assertTrue(cache.isExpired(timeAfterTTL));
    }

    @Test
    public void testValidCheckIfInCacheDays(){

        ArrayList<AirCondition> conditions = cache.checkIfInCacheDays(latitude, longitude, dateStart, dateEnd);

        ArrayList<AirCondition> list = new ArrayList<>();
        list.add(new AirCondition(50.0, -8.1, dateStart, 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        list.add(new AirCondition(50.0, -8.1, dateEnd, 226.97, 0.0, 2.33, 79.39, 1.25, 1.67, 2.41, 0.57));

        Assertions.assertEquals(conditions.toString(), list.toString());
        Assertions.assertEquals(cache.getHits(), 1);
        Assertions.assertEquals(cache.getMisses(), 0);

    }

    @Test
    public void testInvalidCheckIfInCacheDays(){

        ArrayList<AirCondition> actual = cache.checkIfInCacheDays(latitude, longitude, dateStart, dateEnd.plusDays(6));

        assertNull(actual);
        Assertions.assertEquals(cache.getHits(), 0);
        Assertions.assertEquals(cache.getMisses(), 1);

    }

}
