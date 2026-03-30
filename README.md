# CrediMacpato

## 📋 Descripción del Proyecto

**CrediMacpato** es una aplicación web para gestionar una **cooperativa de ahorro y crédito**. El sistema permite a los miembros realizar contribuciones, solicitar préstamos, registrar pagos y administrar sus cuentas, mientras que los directivos pueden evaluar solicitudes, autorizar desembolsos y gestionar votaciones sobre créditos.

**Contexto académico:** Proyecto de curso *Desarrollo de Aplicaciones Web* (Cibertec)

**Arquitectura:** Patrón **cliente-servidor** implementado con **Spring Boot** 3.x, siguiendo principios de arquitectura limpia con separación clara de capas.

## 🏗️ Arquitectura de Capas
- `controller`: REST controllers que exponen APIs HTTP y orquestan peticiones.
- `usecase`: interfaces de casos de uso (puertos de dominio).
- `repository`: JPA repositories para acceso a datos.
- `mapper`: MapStruct para conversiones DTO <-> Entity.
- `dto`: objetos de transporte entre capa web y dominio.
- `entity`: modelo de base de datos (JPA).
- `facade` (nuevo): orquesta varios casos de uso para simplificar al controller.

## 🔄 Flujo General de Datos

```
┌────────────┐
│  Cliente   │ (HTTP request: JSON)
└─────┬──────┘
      │
      ▼
┌──────────────────────────────────────────────────────────────┐
│              API REST - Spring Boot Server                   │
├──────────────────────────────────────────────────────────────┤
│                                                              │
│  Controller (REST Endpoints)                                │
│  ├─ Recibe request JSON → DTO                              │
│  ├─ Invoca Facade/UseCase                                   │
│  └─ Retorna response (DTO → JSON)                           │
│          ▲                                                   │
│          │                                                   │
│  Facade (Orquestación opcional)                             │
│  ├─ Agrupa múltiples UseCases                              │
│  ├─ Aplica validaciones de negocio                          │
│  └─ Facilita conversión entre DTOs                          │
│          ▲                                                   │
│          │                                                   │
│  UseCase (Lógica de Dominio - @Service)                     │
│  ├─ Implementaciones concretas del negocio                  │
│  ├─ Valida reglas (ej: préstamo solo si hay contribuciones)│
│  └─ Usa Repository y Mapper                                 │
│          ▲                                                   │
│          │                                                   │
│  Repository (Acceso a Datos - JPA)                          │
│  ├─ Encuentra/crea/actualiza Entities                       │
│  └─ Ejecuta queries SQL en la BD                            │
│          ▲                                                   │
│          │                                                   │
│  JPA Entity ◄─────► Mapper ◄─────► DTO                      │
│  (Base de datos)    (MapStruct)    (Transferencia)          │
│                                                              │
└──────────────────────────────────────────────────────────────┘
                        ▲
                        │
┌─────────────────┐     │
│   PostgreSQL/   ├─────┘
│   MySQL         │ (JDBC)
└─────────────────┘
```

**Secuencia típica:**

1. Cliente envía `POST /api/directors/requests/5/approve` (HTTP JSON)
2. `DirectorRestController` recibe y delega a `DirectorFacade`
3. `DirectorFacade` valida negocio y delega a `DirectorsUseCaseImpl`
4. `DirectorsUseCaseImpl` (lógica): busca solicitud, crea préstamo, guarda en DB
5. `LoanMapper` convierte `Loan` entity → `LoanDTO`
6. Response retorna `LoanDTO` como JSON al cliente

**Ventajas de esta arquitectura:**

- **Separación de responsabilidades:** cada capa tiene un rol claro
- **Testeable:** fácil mockear dependencias
- **Escalable:** agregar nuevos casos de uso sin tocar controllers
- **Mantenible:** cambios en DB/lógica no afectan APIs públicas

## 🎯 ¿Por qué Facade?

Aunque muchos controladores pueden invocar directamente el `usecase`, la capa **Facade** aporta:

- **Orquestación:** agrupa múltiples operaciones en una sola llamada
  - Ej: `evaluateApproveAndDisburse` = evalúa + aprueba + disbursa en 1 paso
- **Validación de negocio:** aplica reglas de alto nivel antes de persister
  - Ej: `disburseLoan` rechaza si solicitud no está en estado `APPROVED`
- **Transformación de datos:** convierte parámetros HTTP/DTOs en objetos de dominio
  - Ej: `voteRequest(requestId, userId, approved=true)` → crea `VoteDTO` internamente
- **Testing:** simplifica mocks al tener un único punto de entrada
- **Reutilización:** permite exponer la misma lógica en múltiples canales (REST, gRPC, CLI)

## 📌 Casos de Uso Principales

### Flujo Miembros
1. **Registrarse** en la cooperativa (`/api/users/register`)
2. **Abrir cuenta** (`/api/accounts/open`)
3. **Realizar contribuciones** mensuales (`/contribution/make`)
4. **Solicitar préstamo** (`/api/users/` + `/requestLoan`)
5. **Ver calendario de pagos** (`/api/users/` + `/getPaymentsCalendary`)
6. **Realizar pagos** (`/api/payments/makePayment`)

### Flujo Directivos
1. **Evaluar solicitud de préstamo** (cuenta votos): `/api/directors/requests/{id}/evaluate`
2. **Votar solicitud** (aprobación/rechazo): `/api/directors/requests/{id}/vote/{usuarioId}?approved=true`
3. **Aprobar solicitud** (crear préstamo): `/api/directors/requests/{id}/approve`
4. **Desembolsar dinero** (transacción): `/api/directors/requests/{id}/disburse`

## 🔐 Seguridad

- Configuración en `config/SecurityConfig.java`
- Autenticación con username/password vía `CustomUserDetailsService`
- Autorización basada en roles: `ROLE_ADMIN`, `ROLE_DIRECTOR`, `ROLE_MEMBER`

## Implementación: Directors UseCase + Facade + Controller

### 1. Implementación de `DirectorsUseCaseImpl`

**Ubicación:** `src/main/java/com/runaitec/credimacpato/usecase/impl/DirectorsUseCaseImpl.java`

**Características:**
- `@Service` + `@Transactional`: manejo automático de transacciones a nivel de clase
- Inyecta 7 dependencias: repos, mappers, etc.

**Métodos:**
- `evaluateLoanRequest(Long requestId)` → `LoanRequestState`:
  - valida existencia de solicitud
  - calcula estado según votos: `yes` > `no` → `APPROVED`, caso contrario → `DENIED`, sin votos → `PENDING`
  - guarda cambio en DB y retorna estado
  
- `approveRequest(Long requestId)` → `LoanDTO`:
  - marca solicitud como `APPROVED`
  - crea `Loan` con valores predeterminados (12 meses, 0% tasa)
  - persiste y retorna `LoanDTO`
  
- `denyRequest(Long requestId)` → `void`:
  - marca solicitud como `DENIED` y persiste
  
- `voteRequest(VoteDTO voteDTO)` → `void`:
  - crea entidad `Vote` enlazando `LoanRequest` + `User`
  - persiste voto y actualiza lista de votos en solicitud
  
- `disburseMoney(LoanDisburseDTO loanDisburseDTO)` → `LoanDisburseDTO`:
  - convierte DTO a `LoanDisburse` vía `TransactionMapper`
  - persiste transacción y retorna como `LoanDisburseDTO`

### 2. Facade: `DirectorFacade`

**Ubicación:** `src/main/java/com/runaitec/credimacpato/facade/DirectorFacade.java`

**Responsabilidades:**
- Orquesta múltiples usecases en operaciones de alto nivel
- Validaciones de negocio antes de delegar
- Manejo de excepciones (`IllegalStateException`)

**Métodos:**
- `disburseLoan(Long requestId, LoanDisburseDTO loanDisburseDTO)` → `LoanDisburseDTO`:
  - **Validación:** evalúa solicitud y rechaza si no está `APPROVED`
  - delega a usecase para persistencia
  
- `evaluateLoanRequest(Long requestId)` → `LoanRequestState`:
  - wrapper del usecase
  
- `approveRequest(Long requestId)` → `LoanDTO`:
  - wrapper del usecase
  
- `voteRequest(Long requestId, Long userId, boolean approved)` → `void`:
  - **Facilitador:** crea `VoteDTO` internamente desde parámetros
  - delega a usecase
  
- `evaluateApproveAndDisburse(Long requestId, LoanDisburseDTO disburseDTO)` → `LoanDisburseDTO`:
  - **Operación compuesta:** evalúa → aprueba → disbursa
  - lanza `IllegalStateException` si estado final no es `APPROVED`

### 3. Controller: `DirectorRestController`

**Cambios:**
- Inyecta `DirectorFacade` (en lugar de `DirectorsUseCase`)
- Retorna DTOs específicos (no solo mensajes genéricos)

**Endpoints:**

| Método | Path | Request | Response |
|--------|------|---------|----------|
| `POST` | `/api/directors/requests/{solicitudId}/disburse` | `LoanDisburseDTO` body | `LoanDisburseDTO` |
| `POST` | `/api/directors/requests/{solicitudId}/evaluate` | — | `{"message": "...", "estado": "APPROVED\|DENIED\|PENDING"}` |
| `POST` | `/api/directors/requests/{solicitudId}/approve` | — | `LoanDTO` |
| `POST` | `/api/directors/requests/{solicitudId}/deny` | — | `{"message": "..."}` |
| `POST` | `/api/directors/requests/{solicitudId}/vote/{usuarioId}?approved=true` | — | `{"message": "..."}` |

**Mejora clave:** El endpoint `/evaluate` ahora devuelve el estado real (`APPROVED`, `DENIED`, `PENDING`) en el JSON, permitiendo que el frontend sepa si la evaluación favoreció o rechazó la solicitud.

## Ejemplo de flujo completo

1. Cliente hace `POST /api/directors/requests/5/vote/1?approved=true`
2. Controller → `directorfacade.voteRequest(5, 1, true)`
3. Facade → crea `VoteDTO` y delega a `DirectorsUseCase.voteRequest(...)`
4. UseCase → busca `LoanRequest` + `User`, crea `Vote`, persiste
5. Response: `{"message": "Voto registrado correctamente."}`

---

6. Cliente hace `POST /api/directors/requests/5/evaluate`
7. Controller → `directorFacade.evaluateLoanRequest(5)`
8. Facade/UseCase → evalúa votos, actualiza estado de solicitud en DB
9. Response: `{"message": "Solicitud evaluada correctamente.", "estado": "APPROVED"}`

---

10. Cliente hace `POST /api/directors/requests/5/disburse` + body `LoanDisburseDTO`
11. Controller → `directorFacade.disburseLoan(5, loanDisburseDTO)`
12. Facade → valida que estado sea `APPROVED`, rechaza si no lo es
13. Facade → delega a `DirectorsUseCase.disburseMoney(...)`
14. UseCase → persiste transacción
15. Response: `LoanDisburseDTO` con ID generado, fecha, montos, cuentas origen/destino

## Convenciones internas en este repo

- Controllers usan `ResponseEntity<Map<String, String>>` para mensajes simples.
- CRUD genérico usa `CrudUseCase<DTO, Entity, ID>`.
- El DTO `BoardOfDirectorsDTO` usa `List<UserDTO> members`.
- Seguridad config en `config/SecurityConfig.java`, usa `CustomUserDetailsService`.
