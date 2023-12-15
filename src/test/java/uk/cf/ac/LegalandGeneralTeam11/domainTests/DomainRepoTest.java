package uk.cf.ac.LegalandGeneralTeam11.domainTests;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import uk.cf.ac.LegalandGeneralTeam11.domain.Domain;
import uk.cf.ac.LegalandGeneralTeam11.domain.DomainRepoImpl;
import uk.cf.ac.LegalandGeneralTeam11.domain.DomainServiceImpl;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DomainRepoTest {

    @InjectMocks
    private DomainServiceImpl domainService;

    @Mock
    private DomainRepoImpl mockDomainRepo;

    @Mock
    private JdbcTemplate jdbcTemplate;


        @Test
        public void testGetDomainById() throws Exception{
            MockitoAnnotations.openMocks(this);
            //Given
            // Mock behavior of the DomainRepoImpl
            Domain expectedDomain = new Domain(1L, "example.com", true);
            when(mockDomainRepo.getDomainById(1L)).thenReturn(expectedDomain);
            //When

            // Call the method
            Domain resultDomain = domainService.getDomainById(1L);

            //Then
            // Verify that the method called
            Mockito.verify(mockDomainRepo, Mockito.times(1)).getDomainById(1L);

            // Assert the result
            assertEquals(expectedDomain.getId(), resultDomain.getId());
            assertEquals(expectedDomain.getDomain(), resultDomain.getDomain());
            assertEquals(expectedDomain.getEnabled(), resultDomain.getEnabled());
        }
    @Test
    public void testUpdateDomain() {
            //Given
        // Create a mock of DomainRepoImpl
        DomainRepoImpl mockDomainRepo = Mockito.mock(DomainRepoImpl.class);
        //When
        // Create a domain object for testing
        Domain expectedDomain = new Domain(1L, "example.com", true);

        // Call the method
        mockDomainRepo.updateDomain(expectedDomain);
        //Then
        // Verify that the method called
        Mockito.verify(mockDomainRepo, Mockito.times(1)).updateDomain(expectedDomain);
    }
    @Test
    public void testAddDomain() {
        //Given
        // Create a mock of DomainRepoImpl
        DomainRepoImpl mockDomainRepo = Mockito.mock(DomainRepoImpl.class);
        //When
        // Create a domain object for testing
        Domain expectedDomain = new Domain(1L, "example.com", true);

        // Call the method
        mockDomainRepo.addDomain(expectedDomain);
        //Then
        // Verify that the method called in DomainRepoImpl with the specified argument
        Mockito.verify(mockDomainRepo, Mockito.times(1)).addDomain(expectedDomain);
    }
    @Test
    public void testDeleteDomain() {
        //Given
        // Create a mock of DomainRepoImpl
        DomainRepoImpl mockDomainRepo = Mockito.mock(DomainRepoImpl.class);
        //When
        // Create a domain object for testing
        Domain expectedDomain = new Domain(1L, "example.com", true);

        // Call the method
        mockDomainRepo.deleteDomain(1L);
        //Then
        // Verify that the method called in DomainRepoImpl with the specified argument
        Mockito.verify(mockDomainRepo, Mockito.times(1)).deleteDomain(1L);
    }
}


