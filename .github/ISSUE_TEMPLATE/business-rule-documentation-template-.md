---
name: 'BUSINESS RULE Documentation Template '
about: Template of BUSINESS RULE
title: "[BR001] Minimum Stay Duration per Destination"
labels: documentation
assignees: IcaroLSL, Vittas

---

## 📋 BUSINESS RULE INFORMATION:

### Rule ID: BR001
### Domain: Itinerary
### Layer: Core (Domain)
### Priority: High
### Status: To Do

---

## 📖 USER STORY (BDD Format)

```gherkin
As an itinerary creation system
I want to validate minimum stay duration at each destination
So that travelers have sufficient time to enjoy each location
```

**Business Value**: Increase user satisfaction and reduce negative reviews

---

## ✅ ACCEPTANCE CRITERIA (BDD Scenarios)

### 🟢 Scenario 1: Capital destination with adequate duration

- [ ] **GIVEN** an itinerary is being created
- [ ] **AND** I add destination "São Paulo" type CAPITAL
- [ ] **AND** I set stay duration of 3 days
- [ ] **WHEN** system validates the itinerary
- [ ] **THEN** duration is accepted
- [ ] **AND** destination is added to itinerary
- [ ] **AND** message "Destination successfully added" is displayed

**Related Test**: `StayTest.shouldAcceptCapitalWithAdequateDuration()`

---

### 🔴 Scenario 2: Capital destination with insufficient duration

- [ ] **GIVEN** an itinerary is being created
- [ ] **AND** I add destination "Rio de Janeiro" type CAPITAL
- [ ] **AND** I set stay duration of 1 day
- [ ] **WHEN** system validates the itinerary
- [ ] **THEN** system returns error "MINIMUM_DURATION_NOT_MET"
- [ ] **AND** message "Capitals require a minimum of 2 days stay"
- [ ] **AND** destination is NOT added
- [ ] **AND** suggests adjusting to minimum duration

**Related Test**: `StayTest.shouldRejectCapitalWithInsufficientDuration()`

---

### 🟢 Scenario 3: Beach destination with adequate duration

- [ ] **GIVEN** an itinerary with destination "Florianópolis" type BEACH
- [ ] **AND** stay duration of 4 days
- [ ] **WHEN** system validates
- [ ] **THEN** duration is accepted

**Related Test**: `StayTest.shouldAcceptBeachWithAdequateDuration()`

---

### 📊 Scenario 4: Validation of all types (Outline)

- [ ] **GIVEN** different destination types
- [ ] **WHEN** I validate various durations
- [ ] **THEN** respect the following rules:

| Type | Minimum | Duration Tested | Result |
|------|---------|-----------------|--------|
| CAPITAL | 2 days | 1 day | ❌ Rejected |
| CAPITAL | 2 days | 2 days | ✅ Accepted |
| CAPITAL | 2 days | 3 days | ✅ Accepted |
| BEACH | 3 days | 2 days | ❌ Rejected |
| BEACH | 3 days | 3 days | ✅ Accepted |
| HISTORIC | 1 day | 1 day | ✅ Accepted |
| NATURE | 2 days | 1 day | ❌ Rejected |
| RURAL | 2 days | 2 days | ✅ Accepted |

**Related Test**: `StayTest.shouldValidateMinimumDurationByDestinationType()`

---

## 🎨 UI/UX MOCKUPS

### Screen: Add Destination to Itinerary

```
┌─────────────────────────────────────────┐
│  Add Destination to Itinerary           │
├─────────────────────────────────────────┤
│                                         │
│  Destination: [São Paulo        ▼]     │
│  Type:        [Capital          ▼]     │
│                                         │
│  Stay Duration:                         │
│  ┌─────┐                                │
│  │  3  │ days                           │
│  └─────┘                                │
│                                         │
│  ℹ️ Minimum duration: 2 days            │
│                                         │
│  [ Cancel ]  [ Add Destination ]       │
└─────────────────────────────────────────┘
```

### Screen: Insufficient Duration Error

```
┌─────────────────────────────────────────┐
│  ⚠️  Insufficient Duration              │
├─────────────────────────────────────────┤
│                                         │
│  Capitals require a minimum of          │
│  2 days stay.                           │
│                                         │
│  You provided: 1 day                    │
│  Minimum required: 2 days               │
│                                         │
│  💡 How about increasing to 2 days?     │
│                                         │
│  [ Adjust Duration ]  [ Go Back ]      │
└─────────────────────────────────────────┘
```

---

## 🏗️ TECHNICAL DETAILS

### Affected Layers (Hexagonal Architecture)

```
✅ Domain (Core)
  └── domain.itinerary.Stay (Value Object)
  └── domain.itinerary.DestinationType (Enum)
  └── domain.itinerary.Destination (Entity)
  └── domain.itinerary.Itinerary (Aggregate)
  └── exceptions.MinimumDurationException

✅ Application
  └── usecase.AddDestinationUseCase
  └── dto.AddDestinationRequest
  └── dto.AddDestinationResponse

✅ Infrastructure
  └── controller.ItineraryController
  └── exception.GlobalExceptionHandler
```

### Affected Endpoints

```
POST /api/v1/itineraries/{id}/destinations
  → validates duration when adding destination
  
GET /api/v1/destination-types
  → returns types with minimum durations
```

---

## 📡 ENDPOINT INFORMATION (if applicable):

### Method: POST
### URL: https://localhost:8080/api/v1/itineraries/{id}/destinations
### Header:
```json
{
    "Authorization": "Bearer ${token}"
}
```

### Request Body:
```json
{
    "name": "São Paulo",
    "destinationType": "CAPITAL",
    "stayDays": 3,
    "coordinates": {
        "latitude": -23.5505,
        "longitude": -46.6333
    }
}
```

### Response (statusCode: 201):
```json
{
    "id": "dest-uuid-123",
    "name": "São Paulo",
    "destinationType": "CAPITAL",
    "stay": {
        "days": 3,
        "minimumRequired": 2,
        "valid": true
    },
    "addedAt": "2024-12-28T10:30:00Z"
}
```

### Error Cases:
```json
// 400 Bad Request - Minimum duration not met
{
    "error": {
        "code": "MINIMUM_DURATION_NOT_MET",
        "message": "Capitals require a minimum of 2 days stay",
        "detail": {
            "destinationType": "CAPITAL",
            "minimumDuration": 2,
            "providedDuration": 1,
            "difference": 1
        },
        "suggestion": "How about increasing to 2 days?"
    }
}

// 401 Unauthorized - Invalid/missing token
{
    "error": {
        "code": "UNAUTHORIZED",
        "message": "Invalid or missing authentication token"
    }
}

// 404 Not Found - Itinerary not found
{
    "error": {
        "code": "ITINERARY_NOT_FOUND",
        "message": "Itinerary with id {id} not found"
    }
}

// 500 Internal Server Error
{
    "error": {
        "code": "INTERNAL_ERROR",
        "message": "An unexpected error occurred"
    }
}
```

---

## 📚 DOCUMENTATION

- 📄 **Technical docs**: [/docs/business-rules/BR001.md](link)
- 📖 **Detailed wiki**: [Wiki BR001](link)
- 🧪 **Tests**: [StayTest.java](link)
- 💻 **Code**: [Stay.java](link)

---

## 🧪 TEST STRATEGY

### Unit Tests (Domain)
```
✅ StayTest
  ├── shouldAcceptCapitalWithAdequateDuration()
  ├── shouldRejectCapitalWithInsufficientDuration()
  ├── shouldAcceptBeachWithAdequateDuration()
  ├── shouldRejectBeachWithInsufficientDuration()
  └── shouldValidateMinimumDurationByDestinationType()
```

### Integration Tests (Application)
```
✅ AddDestinationUseCaseTest
  ├── shouldValidateDurationWhenAddingDestination()
  └── shouldReturnErrorWithValidationDetails()
```

### E2E Tests (API)
```
✅ ItineraryControllerTest
  └── POST_shouldReturn400WhenInsufficientDuration()
```

---

## 📋 MINIMUM DURATION RULES TABLE

| Destination Type | Minimum Duration | Recommended | Justification |
|-----------------|------------------|-------------|---------------|
| CAPITAL | 2 days | 3-4 days | Multiple attractions, traffic, gastronomy |
| BEACH | 3 days | 5-7 days | Relaxation, climate, multiple beaches |
| HISTORIC | 1 day | 2 days | Compact historic centers |
| NATURE | 2 days | 3-4 days | Trails, acclimatization, climate |
| RURAL | 2 days | 3 days | Experience, internal travel |

---

## 🔗 RELATIONSHIPS

### Depends on
- None

### Used by
- **BR005**: Complete itinerary validation
- **BR012**: Total budget calculation
- **BR018**: Automatic activity suggestion

### Related Issues
- #12 - Create DestinationType enum
- #34 - Implement Value Objects
- #56 - Setup domain unit tests

---

## 📊 DEFINITION OF DONE (DoD)

- [ ] Code implemented in domain layer
- [ ] Unit tests with 100% coverage
- [ ] Integration tests passing
- [ ] Documentation updated (`/docs` + Wiki)
- [ ] Code review approved
- [ ] CI/CD pipeline passing
- [ ] Validated in staging environment
- [ ] Accepted by Product Owner

---

## 🎯 ESTIMATION

**Story Points**: 5  
**Complexity**: Medium  
**Risk**: Low

### Task Breakdown
- [ ] Create enum `DestinationType` with durations (1h)
- [ ] Implement Value Object `Stay` (2h)
- [ ] Create exception `MinimumDurationException` (0.5h)
- [ ] Integrate validation in `Destination` (1h)
- [ ] Write unit tests (3h)
- [ ] Write integration tests (2h)
- [ ] Update documentation (1h)
- [ ] Code review + adjustments (1.5h)

**Total estimated**: ~12h

---

## 🏷️ LABELS

`business-rule` `domain:itinerary` `priority:high` `status:ready` `size:medium` `layer:core`

---

## 👥 ASSIGNEES

**Developer**: @dev-backend  
**Reviewer**: @tech-lead  
**QA**: @qa-engineer  
**PO**: @product-owner

---

## 📈 SUCCESS METRICS

| Metric | Before | Target | Current |
|--------|--------|--------|---------|
| Itineraries with insufficient duration | 23% | 0% | - |
| Average stay duration | 1.8 days | 2.5 days | - |
| User NPS | 65 | 75 | - |
| Itinerary completion rate | 78% | 85% | - |

---

## 💬 COMMENTS

### @product-owner - Dec 28, 2024
> This rule is essential to reduce dissatisfaction. Data shows that 87% of users with short stays rate negatively.

### @dev-backend - Dec 28, 2024
> Will implement as immutable Value Object in domain. Validation will be done in constructor.

### @qa-engineer - Dec 28, 2024
> Prepared test matrix with 15 different scenarios. Ready for validation.

---

**Created at**: December 28, 2024  
**Sprint**: Sprint 3  
**Release**: v1.2.0
