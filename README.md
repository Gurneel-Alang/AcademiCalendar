# AcademiCalendar

An all-in-one desktop calendar app for students to manage events, checklists, weather,
and reminders. 

## Authors & Contributors


- Yiting Jin
- Tran Truong
- Gurneel Alang
- Emma Hong

## Table of Contents

- [Summary](#summary)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)
- [Feedback](#feedback)
- [Contributing](#contributing)

## Summary

AcademiCalendar helps students track academic deadlines and daily tasks in
one place instead of juggling separate apps for events, to-do lists, and
weather. It combines a calendar, event manager, checklist, and weather view
so students can plan their day without switching tools. It's diverse features 
are designed to  

## Features

- **Calendar view** — select any date on an interactive calendar.
- **Events** — add, edit, and delete events tied to specific dates.
- **Checklist** — add tasks with an optional due date, check them off, and see completed tasks marked visually. View checklist sorted by due date and overdue tasks showing up in red. 
- **Weather** — view weather for the selected date and location via the OpenWeather API. View weather-related suggestions to prepare for scheduled activities.
- **Reminders** — get notified 1, 3, or 7 days ahead of upcoming events. 

*(Screenshots/GIFs of each view go here — capture the calendar, checklist,
and weather panels from a running build.)*

## Installation

**Requirements:** JDK 16+, Maven 3.8+, Git. Cross-platform (macOS, Windows,
Linux with a graphical desktop — Swing needs a display).

```bash
git clone https://github.com/Gurneel-Alang/AcademiCalendar.git
cd AcademiCalendar
export OPENWEATHER_API_KEY="your_key_here"   # get a free key at openweathermap.org/api
mvn clean install
```

**Run via IntelliJ IDEA:** open the project, right-click
`src/main/java/app/CalendarPreviewMain.java` → **Run**. Make sure
`OPENWEATHER_API_KEY` is set in the run configuration's environment
variables (IntelliJ doesn't inherit shell variables automatically).

**Dependencies (auto-installed by Maven):** LGoodDatePicker `11.2.1`,
sqlite-jdbc `3.53.1.0`, org.json `20240303`, Gson `2.14.0`, JUnit `4.13.1`
(tests only).

**Common issue:** if the build fails with "cannot find symbol" for classes
like `EventDataAccessObject` or `CalendarView`, run `git pull origin main`
— you're likely on a stale checkout.

**API:**
openweatherAPI key under configurations for CalendarPreviewMain

## Usage

1. Launch the app — the calendar view opens by default.
2. Click a date to select it.
3. Use the **Events**, **Weather**, and **Checklist** buttons to switch
   views.
4. In the Checklist view, type a task and click **Add Task** (or press
   Enter) to add it for the selected date. Click a task's checkbox to mark
   it complete.
5. Use **Add Event** / **Edit Event** / **Delete Event** to manage events
   on the selected date.

### Note:
**Weather:** make sure that the date is the same as the city you are searching (i.e Bejing's date would be one day ahead of Toronto's)
- It automatically creates a local SQLite database at:

  - macOS/Linux: `~/.academicalendar/academicalendar.db`
  - Windows: `%USERPROFILE%\.academicalendar\academicalendar.db`

The database is generated automatically. Weather data must be fetched online at least once before it is available offline.

## License

`[choose and add a license — e.g. MIT , then add a LICENSE file to the repo
root and update this section to match exactly]`

## Feedback
For now: open a [GitHub Issue](https://github.com/Gurneel-Alang/AcademiCalendar/issues)
describing the bug or suggestion.

## Contributing

This is a course project — contributions from outside the team are
currently closed. Team members:

1. Fork or branch from `main`.
2. Make changes on a feature branch (`feature/your-feature-name`).
3. Open a pull request into `main` with a clear description of the change.
4. At least one other team member reviews and approves before merging.