package use_case.view_monthly_schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.YearMonth;

import org.junit.Test;

import data_access.EventDataAccessObject;
import entity.event.Event;

public class ViewMonthlyScheduleInteractorTest {

    @Test
    public void returnsEventsOverlappingSelectedMonth() {
        final EventDataAccessObject dao = new EventDataAccessObject();
        dao.addEvent(new Event("August event", "Inside month",
                LocalDate.of(2026, 8, 10), LocalDate.of(2026, 8, 11)));
        dao.addEvent(new Event("Cross-month event", "Starts in July",
                LocalDate.of(2026, 7, 31), LocalDate.of(2026, 8, 2)));
        dao.addEvent(new Event("September event", "Outside month",
                LocalDate.of(2026, 9, 1), LocalDate.of(2026, 9, 2)));

        final ViewMonthlyScheduleOutputBoundary presenter =
                new ViewMonthlyScheduleOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            ViewMonthlyScheduleOutputData outputData) {
                        assertEquals(YearMonth.of(2026, 8), outputData.getMonth());
                        assertEquals(2, outputData.getEvents().size());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        fail("Unexpected failure: " + errorMessage);
                    }
                };

        final ViewMonthlyScheduleInteractor interactor =
                new ViewMonthlyScheduleInteractor(dao, presenter);
        interactor.execute(new ViewMonthlyScheduleInputData(
                YearMonth.of(2026, 8)));
    }

    @Test
    public void rejectsMissingMonth() {
        final ViewMonthlyScheduleDataAccessInterface dao = month -> {
            fail("The DAO should not be called.");
            return null;
        };
        final ViewMonthlyScheduleOutputBoundary presenter =
                new ViewMonthlyScheduleOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            ViewMonthlyScheduleOutputData outputData) {
                        fail("Success was not expected.");
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        assertEquals("A month must be selected.", errorMessage);
                    }
                };

        final ViewMonthlyScheduleInteractor interactor =
                new ViewMonthlyScheduleInteractor(dao, presenter);
        interactor.execute(new ViewMonthlyScheduleInputData(null));
    }
}
