package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TrainConsistManagementAppTest {

    @Test
    public void testSortBogiesByCapacityAscending() throws Exception {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        List<Bogie> sorted = TrainConsistManagementApp.sortBogiesByCapacity(bogies, true);

        assertEquals(48, sorted.get(0).getCapacity());
        assertEquals(72, sorted.get(1).getCapacity());
        assertEquals(96, sorted.get(2).getCapacity());
    }

    @Test
    public void testSortBogiesByCapacityDescending() throws Exception {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        List<Bogie> sorted = TrainConsistManagementApp.sortBogiesByCapacity(bogies, false);

        assertEquals(96, sorted.get(0).getCapacity());
        assertEquals(72, sorted.get(1).getCapacity());
        assertEquals(48, sorted.get(2).getCapacity());
    }

    @Test
    public void testFilterBogiesByMinCapacity() throws Exception {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        List<Bogie> filtered = TrainConsistManagementApp.filterBogiesByMinCapacity(bogies, 60);

        assertEquals(2, filtered.size());
        assertTrue(filtered.stream().allMatch(b -> b.getCapacity() > 60));
    }

    @Test
    public void testGroupBogiesByName() throws Exception {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("Sleeper", 72));

        Map<String, List<Bogie>> grouped = TrainConsistManagementApp.groupBogiesByName(bogies);

        assertEquals(2, grouped.get("Sleeper").size());
        assertEquals(1, grouped.get("AC Chair").size());
    }

    @Test
    public void testCountTotalSeats() throws Exception {
        List<Bogie> bogies = new ArrayList<>();
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 96));
        bogies.add(new Bogie("First Class", 48));

        int total = TrainConsistManagementApp.countTotalSeats(bogies);

        assertEquals(216, total);
    }

    @Test
    public void testValidateTrainIdAndCargoCode() {
        assertTrue(TrainConsistManagementApp.isValidTrainId("TRN-1234"));
        assertFalse(TrainConsistManagementApp.isValidTrainId("TRN1234"));
        assertTrue(TrainConsistManagementApp.isValidCargoCode("PET-AB"));
        assertFalse(TrainConsistManagementApp.isValidCargoCode("PET-ab"));
    }

    @Test
    public void testGoodsBogieSafetyCompliance() {
        List<GoodsBogie> goodsBogies = new ArrayList<>();
        goodsBogies.add(new GoodsBogie("Cylindrical", "Petroleum"));
        goodsBogies.add(new GoodsBogie("Rectangular", "Coal"));

        assertTrue(TrainConsistManagementApp.isGoodsBogieListSafe(goodsBogies));

        goodsBogies.add(new GoodsBogie("Cylindrical", "Coal"));
        assertFalse(TrainConsistManagementApp.isGoodsBogieListSafe(goodsBogies));
    }

    @Test
    public void testBogieInvalidCapacityThrowsException() {
        assertThrows(Exception.class, () -> new Bogie("Invalid", -10));
        assertThrows(Exception.class, () -> new Bogie("Zero", 0));
    }
}