# Accessibility Report

This file aims to answer questions on the **Principles of Universal Design** as required for the CSC207 course project.</p>

## 1. Which features adhere to the given principles, if any?

1. **Equitable Use:** The simple UI provided by the application *adheres* to this principle, allowing for use by anyone of varying abilities. Features are distinguishable, styling is not elaborate, and navigation exists through only a few interactions. For better use by people with stronger visual impairments, text-to-speech features may be added in the future.
2. **Flexible Use:** The views for many use cases adhere to this principle, *except* in functionality for adding, editing, and deleting events, due to its limited implementation. Currently, users have to specify start/end dates for events in its associative view window, rather than clicking a date on the calendar UI to set the start/end date. Additionally, editing and deleting events requires memorizing event details and use of separate view windows, instead of quickly editing or deleting on the main UI. Future implementation may include secondary ways to manage events to accomodate for this principle.
3. **Simple and Intuitive Use:** The use cases do not require extensive input data, making it *adhere* to this principle. Short descriptions and requests from associating views further show this, minimizing stress from using the application.
4. **Perceptible Information:** The application does *not* adhere to this principle, as output and feedback are only shown visually, and not also through audible or tactile means. Furthermore, legibility is not easily achieved given the use of Java Swing's default appearance.
5. **Tolerance for Error:** Event management and weather API calls *adhere* to this principle, in the following ways:
- Addition of events enforces that event titles be unique, to prevent multiple events of the same title from being edited if the user chooses to edit only one.
- Similarly, this enforcement prevents deletion of multiple events of the same title.
- Failed API calls display error messages rather than crash the application. These messages include missing API keys, invalid locations, and invalid dates.
6. **Low Physical Effort:** The application does *not* necessarily adhere to this principle, due to repetitive clicks and mouse movement for functionality. This is especially shown in event management, with repeatedly opening views rather than using keyboard shortcuts, which may be a future implementation. This also stems from how users must remember event details to correctly modify them.
7. **Size and Space for Approach and Use:** The application *adheres* to this principle in use of necessary spacing of elements. However, the size of said elements would pose as a disadvantage for near-sighted users, as no changes to sizes were implemented. To accomodate for this, a future implementation would include a resizing feature for all text labels and buttons. 

## 2: Who would this application be marketed towards, if it were to be sold/licensed?

This application would be marketed towards people in academic and/or office fields, particularly students and faculty. Users can plan ahead for events and deadlines by viewing their information consistently, setting reminders in the form of pop-ups, viewing weather information for select dates, and managing tasks related to said events and deadlines. Users also benefit from use without internet access, allowing for more specialized planning during travel or extreme conditions.

## 3: Would certain demographics of people be less likely to use your application?

Elderly users would be less likely to use the application, due to repetitive mouse input, element size, and API calls. As previously mentioned, event handling would require multiple and precise mouse clicks to provide details and to proceed with its functionality, especially with the use of small combo boxes for specifying dates. With use of Java Swing's default, small appearance, users with nearsightedness or other visual imparities would barely recognize small text, buttons, or input fields. Finally, users would have difficulty making use of weather features, requiring knowledge and use of the given API and configuration settings, which may immediately prove confusing.