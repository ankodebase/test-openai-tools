# Spring AI Function Calling Demo - FX Rate

A minimal Spring Boot (3.5) demo using Spring AI with OpenAI function calling to fetch FX rates from Fixer API and return a friendly response.

## Prerequisites
- Java 17+
- Gradle (wrapper included)
- OpenAI API key
- Fixer API key (or compatible endpoint returning `latest` with `base`, `symbols`, and `rates`)

## Configuration
Configure via environment variables or edit `src/main/resources/application.properties`.

Required properties:
- `OPENAI_API_KEY` – OpenAI key used by Spring AI
- `FXRATE_API_KEY` – Fixer (or compatible) API key
- `fxrate.api-url` – Base URL for latest rates (default: `https://data.fixer.io/api/latest`)

Examples (PowerShell):
```powershell
$env:OPENAI_API_KEY = "sk-..."
$env:FXRATE_API_KEY = "your_fixer_key"
```

The project defaults to model `gpt-5-nano` and temperature `1.0`:
- `spring.ai.openai.chat.options.model=gpt-5-nano`
- `spring.ai.openai.chat.options.temperature=1.0`

## Run
Using the Gradle wrapper:
```powershell
./gradlew bootRun
```
Or build a jar and run:
```powershell
./gradlew clean build
java -jar build/libs/test-openai-functions-0.0.1-SNAPSHOT.jar
```

## API
### GET `/fx/rate`
Query params:
- `from` – source currency code, e.g. `USD`
- `to` – target currency code, e.g. `EUR`

Example:
```text
GET http://localhost:8080/fx/rate?from=USD&to=EUR
```
Response: A human-readable string generated via Spring AI function calling, for example:
```text
Current FX rate from USD to EUR is 0.92 at 2025-01-01T12:34:56Z
```

## How it works
- `FxRateController` builds a prompt with system and user messages and enables a tool: `FxRateService`.
- `FxRateService.getRates` is annotated with `@Tool` so the LLM can call it. It fetches data from the configured `fxrate` API using `RestClient` with params `access_key`, `base`, and `symbols`.
- `CustomToolCallResultConverter` formats the tool result into a concise plain-text message returned to the client.

## Notes
- Ensure your FX API plan supports the `base` parameter; free Fixer plans may restrict it.
- Replace `fxrate.api-url` or wrap with a proxy if your provider differs.
- Logging is enabled via `@Slf4j` for request/response visibility.
