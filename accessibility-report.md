# Accessibility Report

This file aims to answer questions on the **Principles of Universal Design** as required for the CSC207 course project.</p>

## 1. Which features adhere to the given principles, if any?

1. **Equitable Use:** The simple UI provided by the application *adheres* to this principle, allowing for use by anyone of varying abilities. Features are distinguishable, styling is not elaborate, and navigation exists through only a few interactions. For better use by people with stronger visual impairments, text-to-speech features may be added in the future.
2. **Flexible Use:** The views for many use cases adhere to this principle, *except* in functionality for adding, editing, and deleting events, due to its limited implementation. Currently, users have to specify start/end dates for events in a separate window, rather than clicking a date on the calendar UI to set the start date. Additionally, deleting events requires use of a separate window instead of doing so on the main UI. Future implementation may include secondary ways to manage events to accomodate for this principle.
3. **Simple and Intuitive Use:** The use cases do not require extensive input data, making it *adhere* to this principle. Short descriptions and requests from associating views further show this, minimizing stress from using the application.
4. **Perceptible Information:**
5. **Tolerance for Error:** Event management and weather API calls *adhere* to this principle, particularly in uniqueness of titling events and failed API calls. To prevent manipulation of multiple events of the same title in one use case execution, unique naming is enforced. Additionally, failed API calls display error messages rather than crash the application.
6. **Low Physical Effort:** The application does *not* necessarily adhere to this principle, due to repetitive clicks and mouse movement for functionality. This is especially shown in event management, with repeatedly opening views rather than using keyboard shortcuts, which may be a future implementation.
7. **Size and Space for Approach and Use:**

## 2: Who would this application be marketed towards, if it were to be sold/licensed?

This application would be marketed towards people in academic and/or office fields, particularly students and faculty. Users can plan ahead for events and deadlines by viewing their information consistently, setting reminders in the form of pop-ups, viewing weather information for select dates, and managing tasks related to said events and deadlines. Users also benefit from use without internet access, allowing for more specialized planning during travel or extreme conditions.

## 3: Would certain demographics of people be less likely to use your application?

Users without profound English skills would be less likely to use the application, as it does not provide translation features.