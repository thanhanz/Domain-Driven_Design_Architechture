# 0 - 4
- Understand the flow of **DDD (Domain-Driven Design) architecture**
- The project is protected by Resilience4j (CircuitBreaker and RateLimiter)

  **CircuitBreaker** (States: Closed, Open, Half-Open):  
  - Protects services from repeated failures by temporarily blocking calls to unstable APIs.

  **RateLimiter**:  
  - Limits the number of requests sent to backend APIs.  
  - Includes a fallback function to handle situations when the API is rate-limited.
