# Engineering Manager Copilot — Android App

An AI-powered Android application that helps engineering managers review projects, track findings, manage decisions, and analyse engineering sources — built with Jetpack Compose and Kotlin.

---

## Current Status

### ✅ UI Complete (Mock Data)
All screens have been built and are fully functional with hardcoded mock data for preview and demonstration purposes.

### 🔄 API Integration In Progress
Backend integration has been started. The data layer foundation is in place and the first endpoints are being wired up.

---

## Screens Completed (UI Only)

| Screen | Package | Status |
|---|---|---|
| Welcome | `ui/welcome` | ✅ UI Done |
| Onboarding | `ui/onboarding` | ✅ UI Done |
| Login | `ui/auth/login` | ✅ UI + API Integrated |
| Projects List | `ui/projects` | ✅ UI + API Integrated |
| Create Project | `ui/projects` | ✅ UI + API Integrated |
| Project Detail | `ui/projectdetail` | ✅ UI Done (mock data) |
| Add Sources | `ui/sources` | ✅ UI Done (mock data) |
| No Sources | `ui/nosources` | ✅ UI Done (mock data) |
| Findings | `ui/findings` | ✅ UI Done (mock data) |
| Questions | `ui/questions` | ✅ UI Done (mock data) |
| Decisions | `ui/decisions` | ✅ UI Done (mock data) |
| Review Brief | `ui/reviewbrief` | ✅ UI Done (mock data) |

---

## API Integration Progress

### ✅ Done
- `POST /api/v1/auth/signin` — Login with email & password, token saved to DataStore
- `POST /api/v1/projects` — Create a new project
- `GET /api/v1/projects` — Fetch and display real projects list

### ⬜ Pending
- Project detail API
- Sources upload/listing
- Findings API
- Questions API
- Decisions API
- Review Brief API

---

## Architecture

**Pattern:** MVVM (Model-View-ViewModel)
**UI:** Jetpack Compose
**DI:** Hilt
**Networking:** Retrofit + OkHttp + Kotlinx Serialization
**Local Storage:** DataStore Preferences (JWT token)
**State Management:** StateFlow + sealed interface UiState per screen

```
app/
├── data/
│   ├── local/
│   │   └── datastore/          # TokenDataStore — JWT persistence
│   ├── remote/
│   │   ├── api/                # ApiService (Retrofit interface)
│   │   ├── interceptor/        # AuthInterceptor, AuthAuthenticator
│   │   └── model/
│   │       ├── request/        # SignInRequest, CreateProjectRequest
│   │       └── response/       # SignInResponse, ProjectsResponse, etc.
│   └── repository/             # AuthRepository, ProjectRepository (interface + impl)
├── di/
│   ├── NetworkModule.kt        # OkHttp, Retrofit, ApiService
│   └── RepositoryModule.kt     # Hilt bindings: interface → impl
├── navigation/
│   ├── NavGraph.kt
│   └── Screen.kt
└── ui/
    ├── auth/login/             # LoginScreen, LoginViewModel, LoginUiState
    ├── projects/               # ProjectsScreen, CreateProjectScreen + ViewModels + UiStates
    ├── projectdetail/          # UI only
    ├── sources/                # UI only
    ├── findings/               # UI only
    ├── questions/              # UI only
    ├── decisions/              # UI only
    ├── reviewbrief/            # UI only
    ├── onboarding/             # UI only
    ├── welcome/                # UI only
    └── theme/                  # AppColors, Typography, Theme
```

---

## Tech Stack

| Layer | Library |
|---|---|
| UI | Jetpack Compose, Material 3 |
| Navigation | Navigation Compose |
| DI | Hilt |
| Networking | Retrofit 2, OkHttp 3 |
| Serialization | Kotlinx Serialization |
| Auth Token | DataStore Preferences |
| ViewModel | AndroidX ViewModel + StateFlow |
| Font | Poppins (Regular, Medium, SemiBold) |

---

## Running Locally

### Backend (FastAPI)
The app currently connects to a local FastAPI backend.

```bash
# From the backend project root (with venv activated)
python -m uvicorn app.main:app --host 0.0.0.0 --port 8000 --reload
```

### Android — Emulator
```kotlin
// build.gradle (app) — debug block
buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8000/\"")
```

### Android — Physical Device
Ensure phone and PC are on the same WiFi network, then:
```kotlin
buildConfigField("String", "BASE_URL", "\"http://<your-pc-ip>:8000/\"")
```
Find your PC IP with `ipconfig` (Windows) or `ifconfig` (Mac/Linux).

> **Note:** `network_security_config.xml` is configured to allow cleartext traffic for local development. This must be restricted before production.

---

## What's Next

1. Wire remaining screens to real API responses (Sources, Findings, Questions, Decisions, Review Brief)
2. Implement token refresh logic in `AuthAuthenticator`
3. Add logout flow with token clearing
4. Handle onboarding completion flag from `user.onboardingCompleted`
5. Add loading skeletons and empty states for all list screens
