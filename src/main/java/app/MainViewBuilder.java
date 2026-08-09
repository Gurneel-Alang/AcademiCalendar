package app;

import javax.swing.JFrame;

import data_access.CheckListDataAccessObject;
import data_access.EventDataAccessObject;
import data_access.ReminderScheduler;
import entity.event.EventFactory;
import entity.task.CommonTaskFactory;
import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.create_task.CreateTaskController;
import interface_adapter.checklist.create_task.CreateTaskPresenter;
import interface_adapter.checklist.load_checklist.LoadChecklistController;
import interface_adapter.checklist.load_checklist.LoadChecklistPresenter;
import interface_adapter.checklist.toggle_task.ToggleTaskController;
import interface_adapter.checklist.toggle_task.ToggleTaskPresenter;
import interface_adapter.event.add_event.AddEventController;
import interface_adapter.event.add_event.AddEventPresenter;
import interface_adapter.event.add_event.AddEventViewModel;
import interface_adapter.event.delete_event.DeleteEventController;
import interface_adapter.event.delete_event.DeleteEventPresenter;
import interface_adapter.event.delete_event.DeleteEventViewModel;
import interface_adapter.event.edit_event.EditEventController;
import interface_adapter.event.edit_event.EditEventPresenter;
import interface_adapter.event.edit_event.EditEventViewModel;
import interface_adapter.event.view_events.ViewEventsViewModel;
import interface_adapter.event.view_events.ViewEventsController;
import interface_adapter.view_monthly_schedule.ViewMonthlyScheduleController;
import interface_adapter.view_monthly_schedule.ViewMonthlySchedulePresenter;
import use_case.event.add_event.AddEventInteractor;
import use_case.event.delete_event.DeleteEventInteractor;
import use_case.event.edit_event.EditEventInteractor;
import use_case.task.create_task.CreateTaskInteractor;
import use_case.task.load_checklist.LoadChecklistInteractor;
import use_case.task.toggle_task.ToggleTaskInteractor;
import use_case.view_monthly_schedule.ViewMonthlyScheduleInteractor;
import view.CalendarView;
import view.ChecklistView;
import view.MainView;
import view.WeatherView;
import view.event_view.*;
import view.StudyTimerView;

/**
 * Builder class to attach use cases and views to - and return - a MainView.
 * Makes use of the Builder design pattern.
 */
public class MainViewBuilder {

    private final String apiKey = System.getenv("OPENWEATHER_API_KEY");

    private final JFrame frame;

    private final CheckListDataAccessObject checkListDataAccessObject = new CheckListDataAccessObject();
    private final EventDataAccessObject eventDataAccessObject = new EventDataAccessObject();

    private CreateTaskController createTaskController;
    private LoadChecklistController loadChecklistController;
    private ViewEventsController viewEventsController;
    private ViewMonthlyScheduleController viewMonthlyScheduleController;

    private CalendarView calendarView;
    private WeatherView weatherView;
    private ChecklistView checklistView;
    private EventListView eventListView;
    private AddEventView addEventView;
    private EditEventView editEventView;
    private DeleteEventView deleteEventView;
    private StudyTimerView studyTimerView;

    public MainViewBuilder(JFrame frame) {
        this.frame = frame;
    }

    /**
     * Add the calendar view.
     * @return this builder
     */
    public MainViewBuilder addCalendarView() {
        calendarView = new CalendarView();
        return this;
    }

    /**
     * Add the weather view.
     * @return this builder
     */
    public MainViewBuilder addWeatherView() {
        weatherView = WeatherUseCaseFactory.create(apiKey);
        return this;
    }

    /**
     * Add the Checklist Use Case and view.
     * @return this builder
     */
    public MainViewBuilder addCheckListView() {
        final ChecklistViewModel checklistViewModel = new ChecklistViewModel();

        final CreateTaskPresenter createTaskPresenter = new CreateTaskPresenter(checklistViewModel);
        final CreateTaskInteractor createTaskInteractor = new CreateTaskInteractor(
                checkListDataAccessObject, createTaskPresenter, new CommonTaskFactory());
        createTaskController = new CreateTaskController(createTaskInteractor);

        final ToggleTaskPresenter toggleTaskPresenter = new ToggleTaskPresenter(checklistViewModel);
        final ToggleTaskInteractor toggleTaskInteractor = new ToggleTaskInteractor(
                checkListDataAccessObject, toggleTaskPresenter);
        final ToggleTaskController toggleTaskController = new ToggleTaskController(toggleTaskInteractor);

        final LoadChecklistPresenter loadChecklistPresenter = new LoadChecklistPresenter(checklistViewModel);
        final LoadChecklistInteractor loadChecklistInteractor = new LoadChecklistInteractor(
                checkListDataAccessObject, loadChecklistPresenter);
        loadChecklistController = new LoadChecklistController(loadChecklistInteractor);

        checklistView = new ChecklistView(checklistViewModel, toggleTaskController);
        return this;
    }

    /**
     * Add the "view events in the displayed month" use case and view.
     * @return this builder
     */
    public MainViewBuilder addEventListView() {
        final ViewEventsViewModel viewEventsViewModel = new ViewEventsViewModel();
        viewEventsController = new ViewEventsController(
                eventDataAccessObject, viewEventsViewModel);
        final ViewMonthlySchedulePresenter presenter =
                new ViewMonthlySchedulePresenter(viewEventsViewModel);
        final ViewMonthlyScheduleInteractor interactor =
                new ViewMonthlyScheduleInteractor(eventDataAccessObject, presenter);
        viewMonthlyScheduleController =
                new ViewMonthlyScheduleController(interactor);
        eventListView = new EventListView(viewEventsViewModel);
        return this;
    }

    /**
     * Add the Study Timer view.
     * @return this builder
     */
    public MainViewBuilder addStudyTimerView() {
        studyTimerView = new StudyTimerView();
        return this;
    }

    /**
     * Add the Add Event Use Case and view.
     * @return this builder
     */
    public MainViewBuilder addAddEventView() {
        final AddEventViewModel addEventViewModel = new AddEventViewModel();
        final AddEventPresenter addEventPresenter = new AddEventPresenter(addEventViewModel);
        final AddEventInteractor addEventInteractor = new AddEventInteractor(
                eventDataAccessObject, addEventPresenter, new EventFactory());
        final AddEventController addEventController = new AddEventController(addEventInteractor);
        final ReminderScheduler reminderScheduler = new ReminderScheduler();
        addEventView = new AddEventView(addEventController, addEventViewModel, reminderScheduler);
        addEventViewModel.addPropertyChangeListener(event -> {
            if (AddEventViewModel.CLOSE_PROPERTY.equals(event.getPropertyName())) {
                refreshEvents();
            }
        });
        return this;
    }

    /**
     * Add the Edit Event Use Case and view.
     * @return this builder
     */
    public MainViewBuilder addEditEventView() {
        final EditEventViewModel editEventViewModel = new EditEventViewModel();
        final EditEventPresenter editEventPresenter = new EditEventPresenter(editEventViewModel);
        final EditEventInteractor editEventInteractor = new EditEventInteractor(
                eventDataAccessObject, editEventPresenter, new EventFactory());
        final EditEventController editEventController = new EditEventController(editEventInteractor);
        editEventView = new EditEventView(editEventController, editEventViewModel);
        editEventViewModel.addPropertyChangeListener(event -> {
            if (EditEventViewModel.CLOSE_PROPERTY.equals(event.getPropertyName())) {
                refreshEvents();
            }
        });
        return this;
    }

    /**
     * Add the Delete Event Use Case and view.
     * @return this builder
     */
    public MainViewBuilder addDeleteEventView() {
        final DeleteEventViewModel deleteEventViewModel = new DeleteEventViewModel();
        final DeleteEventPresenter deleteEventPresenter = new DeleteEventPresenter(deleteEventViewModel);
        final DeleteEventInteractor deleteEventInteractor = new DeleteEventInteractor(
                eventDataAccessObject, deleteEventPresenter);
        final DeleteEventController deleteEventController = new DeleteEventController(deleteEventInteractor);
        deleteEventView = new DeleteEventView(deleteEventController, deleteEventViewModel);
        deleteEventViewModel.addPropertyChangeListener(event -> {
            if (DeleteEventViewModel.CLOSE_PROPERTY.equals(event.getPropertyName())) {
                refreshEvents();
            }
        });
        return this;
    }

    private void refreshEvents() {
        if (calendarView != null && calendarView.getSelectedDate() != null
                && viewEventsController != null) {
            viewEventsController.execute(calendarView.getSelectedDate());
        }
        else if (calendarView != null && viewMonthlyScheduleController != null) {
            viewMonthlyScheduleController.execute(calendarView.getDisplayedYearMonth());
        }
    }

    /**
     * Create and return the MainView for the application.
     * @return the MainView
     */
    public MainView build() {
        return new MainView(calendarView, weatherView, checklistView, eventListView,
                studyTimerView, createTaskController, loadChecklistController,
                viewEventsController, viewMonthlyScheduleController,
                () -> {
                    final AddEventDialog dialog = new AddEventDialog(frame, addEventView);
                    dialog.setVisible(true);
                },
                () -> {
                    final EditEventDialog dialog = new EditEventDialog(frame, editEventView);
                    dialog.setVisible(true);
                },
                () -> {
                    final DeleteEventDialog dialog = new DeleteEventDialog(frame, deleteEventView);
                    dialog.setVisible(true);
                }
        );
    }
}
