# dragon-rockets
A simple library for tracking the current status of SpaceX Dragon spacecraft.

## Overview

**dragon-rockets** is a lightweight Java library designed to help developers query and track the current status of SpaceX's Dragon spacecraft. 
Built with simplicity and extensibility in mind, this library is ideal for integration into internal tools or space mission dashboards.

## Features

1. **Add New Rocket**
2. **Assign Rocket to a Mission**
3. **Change Rocket Status**
4. **Add New Mission**
5. **Assign Rockets to a Mission**
6. **Change Mission Status**
7. **Get Mission Summary by Rocket Count**

## Assumptions per feature
1. **Add New Rocket**
    - Create a new rocket entry with default status set to **"On ground"**.
    - Prevent duplicate rocket entries by unique identifier.

2. **Assign Rocket to a Mission**
    - Allow assigning a rocket to **only one mission** at a time.
    - Enforce one-to-one relationship from rocket to mission.

3. **Change Rocket Status**
    - Update the status of an existing rocket (e.g., from “On ground” to “In space”, etc.).
    - Rocket can have statuses:
      - “On ground” – initial status, where the rocket is not assigned to any mission 
      - “In space” – the rocket was assigned to the mission 
      - “In repair” – the rocket is due to repair, it implies “Pending” status of the mission

4. **Add New Mission**
    - Create a new mission entry with default status set to **"Scheduled"**.

5. **Assign Rockets to a Mission**
    - Allow a mission to have **multiple rockets** assigned.
    - Maintain referential integrity between missions and rockets.

6. **Change Mission Status**
    - Update the status of an existing mission (e.g., “Scheduled” → “Pending”, etc.).
    - Mission can have statuses:
      - “Scheduled” – initial status, where no rockets are assigned
      - “Pending” – at least one rocket is assigned and one or more assigned rockets are in
        repair
      - “In Progress” – at least one rocket is assigned and none of them is in repair
      - “Ended” – the final stage of the mission, at this point rockets should not be assigned
        anymore to a mission
7. **Get Mission Summary by Rocket Count**
    - Retrieve a summary of all missions grouped by the **number of rockets assigned**.
    - Sort missions:
        - First by **number of rockets (descending)**.
        - Then by **mission name in descending alphabetical order** for ties.

## Technical assumptions
- Architecture selection - Hexagonal Architecture + Tactical DDD:
  - Clean separation of concerns
  - Robust and expressive domain model

## LLM usage
- Basic .gitignore file generation taking into account IDE, programming language and OS.

## Getting Started

### Prerequisites

- Java 24+
- Gradle 8.14+

### Installation