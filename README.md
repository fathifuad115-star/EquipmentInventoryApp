# Computer Laboratory Equipment Inventory

**Project status: In progress**

A Kotlin/JVM console application for managing computer laboratory equipment. The project is being developed in small steps, with a focus on clear responsibilities, Clean Architecture, and testable business logic. It is not yet considered complete; see [Development roadmap](#development-roadmap).

## Current Features

- List all equipment.
- Search for equipment by ID.
- Add equipment while rejecting duplicate IDs.
- Update an existing equipment record.
- Delete equipment after user confirmation.
- Validate required fields and reject blank values.
- Persist equipment records as JSON between application runs.
- Load ten sample records the first time the runtime data file is created.

Each equipment record contains an ID, name, location, and condition.

## Architecture

The source code is organized into three layers:

```text
src/main/kotlin/org/example/
├── data/
│   ├── datasource/   # JSON file access and JSON record mapping
│   └── repository/   # EquipmentRepository implementation
├── domain/
│   ├── model/        # Equipment entity and required-field validation
│   ├── repository/   # Repository contract used by the domain
│   └── usecase/      # Add, delete, list, search, and update operations
├── presentation/
│   └── cli/          # Console menu and user interaction
└── Main.kt           # Composition root: creates and connects application components
```

The domain defines the equipment model, repository contract, and use cases. The data layer implements the repository and handles JSON persistence. The presentation layer reads user input and displays results. `Main.kt` wires these components together at startup.

## Requirements

- JDK 17
- Gradle Wrapper (the project wrapper is configured for Gradle 8.14)

The project uses Kotlin/JVM 2.2.21 and `kotlinx.serialization-json` 1.9.0. Tests use Kotlin test with the JUnit 5 platform.

## Run the Application

Open the project in IntelliJ IDEA, locate `src/main/kotlin/org/example/Main.kt`, and run its `main` function.

The project does not currently configure a Gradle `run` task.

## Run Tests

On Windows, from the project root:

```powershell
.\gradlew.bat test
```

On Linux or macOS:

```bash
./gradlew test
```

Make sure Gradle is using a JDK 17 installation, rather than a JRE.

## Data Files

- `src/main/resources/equipment-sample.json` contains the sample records used for initial setup.
- `data/equipment.json` is the runtime data file. It is created from the sample resource when it does not yet exist, then updated as equipment changes.

Keep these roles separate: the resource provides initial examples, while the data file stores the working inventory.

## Tests

Use case tests are located under `src/test/kotlin/org/example/domain/usecase/`. Repository persistence tests are under `src/test/kotlin/org/example/data/repository/`. A fake repository supports isolated use case tests.

The test suite is present, but its latest run has not completed successfully in the current development environment because Gradle was using a Java 8 JRE without the required JDK tools. Configure Gradle to use JDK 17 and rerun the tests before treating the build as verified.

## Development Roadmap

This project is actively being refactored and extended. Potential next steps include improving automated verification, refining error handling and the console interaction, and continuing code review of the architecture. These are development goals, not claims about completed functionality.

## Contributing

For contributions, use a focused feature or refactoring branch, keep commits focused, and request review before merging.

