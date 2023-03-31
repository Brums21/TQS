## Notas quadro 

Rest Assure (DSL) 
    - Standard (URL)
    - Spring Boot Integration

Test Containers - with DB?
    - Standard JPA init
    - DB migrations (fly away)

IT w/ db

1. Spin db Container
2. init db with required state (clean?, scripted?)
3. run Test
4. drop container

when testing containers, use dynamic property source
@TestContainer
@SpringBootTest
class ApplicationTests{
    @container
    public static PostgreSQLContainer container = new PostgreSQLContainer()
    .withUsername("username")
    .withPassword("password")
    .withDatabaseName("test");
}

@Autowired
private BookRepository bookRepository;

@DinamicPropertySource
static void properties(DinamicPropertyRegistry registry){
    registry.add("spring.datasource.url", (container::getJdburl);
    registry.add("spring.datasource.password", (container::getPassword);
    registry.add("spring.datasource.username", (container::getUsername);
}
