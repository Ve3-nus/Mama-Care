# MamaCare 
An Android app that helps pregnant women and new mothers in Kenya track medications, health readings and maternal health information, with reminders and one-tap emergency calling.

## The problem

Many expectant and new mothers, especially in lower-income areas, rely on paper records, memory or no system at all. Records get lost, medication doses are missed, and there is no easy way to show a doctor how weight or blood pressure has changed over time. Poor tracking contributes to missed appointments and avoidable complications.

## What MamaCare does

| Feature | Description |
|---|---|
| Dashboard | Today's medication and quick navigation to every section |
| Medications (CRUD) | Add, view, edit and delete medication schedules |
| Health Log (CRUD) | Record weight and blood pressure over time |
| Reminders | WorkManager notifications for medications, even when the app is closed |
| Health Info | Maternal health articles fetched from an API with Retrofit |
| SOS | One tap opens the phone dialer with an emergency number |

## Architecture

```
UI (Fragments, XML, Data Binding)
        │  observes LiveData
        ▼
ViewModel (MedicationViewModel, HealthLogViewModel, HealthInfoViewModel)
        │  viewModelScope + Dispatchers.IO
        ▼
Repository (single source of truth)
        │
        ├── Room Database (local persistence)
        └── Retrofit (remote API)
```

**Why this architecture?** The UI never talks to data sources directly. This separates concerns, survives screen rotation (ViewModels outlive Fragments), and lets each layer be tested on its own.

## Navigation

Single-Activity app using the Jetpack Navigation Component. `MainActivity` hosts a `NavHostFragment` and a `BottomNavigationView`. Data is passed between screens with Safe Args (e.g. `medicationId` to the Add/Edit screen).

```
Dashboard ─┬─ Medication List ── Add/Edit Medication
           ├─ Health Log List ── Add/Edit Health Log
           ├─ Health Info
           └─ SOS
```

## Tech stack

- Kotlin, XML layouts, Material Design, Data Binding
- Navigation Component with Safe Args
- Room (SQLite), LiveData, ViewModel, Coroutines
- Retrofit + Gson/Moshi, Glide
- WorkManager, Notification Channels

## Getting started

1. Clone the repo: `git clone https://github.com/Ve3-nus/Mama-Care.git` then `git checkout develop`
2. Open in Android Studio (latest stable).
3. Let Gradle sync.
4. Run on an emulator or a device with Android 8.0 (API 26) or higher. Min SDK 26, compile/target SDK 34.

On Android 13+ the app asks for notification permission so reminders can appear.

## Project structure

```
com.mamacare.app
├── data
│   ├── local        (entities, DAOs, AppDatabase)
│   ├── remote       (ApiService, response models)
│   └── repository   (MamaCareRepository)
├── ui
│   ├── dashboard
│   ├── medication
│   ├── healthlog
│   ├── healthinfo
│   └── sos
├── worker           (ReminderWorker, notification helper)
└── MainActivity.kt
```

## Team and responsibilities

| Member | Role |
|---|---|
| Venus Kabera | Project Lead and Architect: repo, README, project setup, Navigation |
| Mapelu Joe | UI/UX: layouts, adapters |
| Abraham Kwikiriza | UI/UX: layouts, adapters |
| Boniface Mwangi | Database: Room entities, DAOs, unit tests |
| Dollan James Ochele | Repository and ViewModels |
| Brian Omoto | API and networking (Retrofit) |
| Ryan | WorkManager reminders and SOS |

## Git workflow

- `main`: stable, submission-ready. No direct commits.
- `develop`: integration branch.
- `feature/<task>`: one branch per task, e.g. `feature/medication-crud`.
- Small, frequent commits using prefixes: `feat:`, `fix:`, `docs:`, `test:`, `refactor:`.
- Open a Pull Request into `develop`; the Project Lead reviews before merge.
- `develop` is merged into `main` once at each stable milestone.

## Testing

- Room DAO instrumented tests (insert, query, update, delete)
- Input validation for empty fields and invalid dosage
- Manual testing of network failure, empty list and permission-denied states

## Screenshots

_Add screenshots of Dashboard, Medication List, Health Log, Health Info and SOS here before submission._

## Known limitations and future work

- Clinic locator map
- Appointment tracking
- Cloud backup and sync
- Swahili language support
