# So sánh DDD và MVC trong Spring Boot

| Tiêu chí                     | DDD (Domain-Driven Design)                               | MVC (Model-View-Controller)                            |
|-----------------------------|-----------------------------------------------------------|--------------------------------------------------------|
| Mục tiêu chính              | Tập trung vào nghiệp vụ, logic domain                     | Tách giao diện, xử lý và dữ liệu                       |
| Cấu trúc code               | Theo Bounded Context và domain                            | Theo tầng kỹ thuật (Controller, Service, Repository)   |
| Logic nghiệp vụ chính nằm ở | Domain (Entity, Aggregate, Domain Service)                | Service layer                                           |
| Mức độ phù hợp              | Hệ thống phức tạp, quy mô lớn                             | Ứng dụng CRUD đơn giản                                 |
| Dễ bảo trì và mở rộng       | Cao, do tách biệt domain và hạ tầng rõ ràng               | Khó hơn nếu logic phức tạp                              |
| Áp dụng kiến trúc nào tốt   | Hướng dẫn cho microservices, event-driven, CQRS, v.v.     | Phù hợp với monolith CRUD thông thường                 |
| Độ phức tạp khi phát triển  | Cao hơn, cần hiểu domain rõ                               | Thấp hơn, dễ tiếp cận                                  |
| Test nghiệp vụ              | Dễ test domain độc lập (unit test)                        | Phải test cả service + controller                      |
| Phụ thuộc framework         | Giảm phụ thuộc vào Spring (domain không dùng annotation)  | Gắn chặt vào Spring (@Controller, @Service, ...)       |



# Video from 0 - 4
- Understand the flow of **DDD (Domain-Driven Design) architecture**
- The project is protected by Resilience4j (CircuitBreaker and RateLimiter)

  **CircuitBreaker** (States: Closed, Open, Half-Open):  
  - Protects services from repeated failures by temporarily blocking calls to unstable APIs.

  **RateLimiter**:  
  - Limits the number of requests sent to backend APIs.  
  - Includes a fallback function to handle situations when the API is rate-limited.

# Video 05-06:
  **Distributed Cache (Redisson)**:
  - Lock: Prevents multiple processes (Service, Thread, Request) access or modify the same resource at the same time
  - Unlock: Opposite vs Lock 
  * Example: 
    + Redis chua co Key "post:999"
    + Hơn 1000 nguoi cung click vao "api/post/999"
    + > 1000 request se truy cap vao DB cung luc => Chet DB
    + Giai phap: Chi co request dau tien den moi duoc truy cap xuong DB va set key post:999 -> Redis se Lock de chan nhung request tiep theo truy cap xuong DB. 
    + > **Nhung request sau se doi (khoang vai seconds hoac mili seconds tuy` vao code) -> Cache hit -> Tra ra du lieu**