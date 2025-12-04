package com.myy803.traineeship;

import com.myy803.traineeship.domainmodel.Company;
import com.myy803.traineeship.domainmodel.TraineeshipPosition;
import com.myy803.traineeship.mappers.CompanyMapper;
import com.myy803.traineeship.mappers.TraineeshipMapper;
import com.myy803.traineeship.services.CompanyServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceImplTest {

    @Mock
    private TraineeshipMapper traineeshipMapper;
    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyServiceImpl companyService;

    public CompanyServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAvailablePositions() {
    	Company company = new Company();
    	company.setUsername("test");
    	Optional<Company> optionalCompany = Optional.of(company);
    	
    	TraineeshipPosition mockPosition = new TraineeshipPosition();
    	mockPosition.setCompany(optionalCompany.get());
        List<TraineeshipPosition> mockPositions = new ArrayList<>();
        mockPositions.add(mockPosition);
        
        when(companyMapper.findByUsername("test")).thenReturn(optionalCompany);
        when(traineeshipMapper.findAllByCompany(optionalCompany)).thenReturn(mockPositions);

        List<TraineeshipPosition> result = companyService.retrieveAvailablePositions("test");

        assertEquals(1, result.size());
    }

    @Test
    void testEmptyPositionsList() {
        when(traineeshipMapper.findAll()).thenReturn(new ArrayList<>());

        List<TraineeshipPosition> result = companyService.retrieveAssignedPositions(null);

        assertTrue(result.isEmpty());
    }
}

