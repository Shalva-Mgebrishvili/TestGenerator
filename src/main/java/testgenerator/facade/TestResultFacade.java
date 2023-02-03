package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.*;
import testgenerator.model.dto.TestResultByUserIdAndTestResultIdDto;
import testgenerator.model.dto.TestResultDto;
import testgenerator.model.dto.TestResultShortDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.TestResultMapper;
import testgenerator.service.KeycloakService;
import testgenerator.service.TestResultService;
import testgenerator.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TestResultFacade {

    private final TestResultService service;
    private final UserService userService;
    private final KeycloakService keycloakService;
    private final TestResultService testResultService;

    public TestResultDto findById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);

        return TestResultMapper.testResultDto(testResult);
    }

    public Page<TestResultDto> findAll(Pageable pageable){
        Page<TestResult> allTestResults = service.findAll(Status.ACTIVE, pageable);

        return allTestResults.map(TestResultMapper::testResultDto);
    }

    public Page<TestResultShortDto> myTestResults(Pageable pageable, Jwt jwt) {
        String email = (String) jwt.getClaims().get("email");
        UserEntity user = userService.findByEmail(email, Status.ACTIVE);
        String userKeycloakId = keycloakService.searchUserIdInKeycloakByUsername(user.getUsername());

        Map<String, List<String>> realmAccess = (Map<String, List<String>>) jwt.getClaims().get("realm_access");
        List<String> roles = realmAccess.get("roles");
        System.out.println(roles);

        if(!userKeycloakId.equals(jwt.getSubject()) && !roles.contains(Role.ADMIN.name())
                && !roles.contains(Role.SUPER_ADMIN.name()) && !roles.contains(Role.CORRECTOR.name()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");

        Page<TestResult> allUserTestResults = service.findAllByUserId(Status.ACTIVE, user.getId(), pageable);

        return allUserTestResults.map(TestResultMapper::testResultShortDto);
    }

    public TestResultByUserIdAndTestResultIdDto myTestResultById(Long testResultId, Jwt jwt) {
        String email = (String) jwt.getClaims().get("email");
        UserEntity user = userService.findByEmail(email, Status.ACTIVE);
        TestResult testResult = testResultService.findById(testResultId,Status.ACTIVE);
        String userKeycloakId = keycloakService.searchUserIdInKeycloakByUsername(user.getUsername());

        Map<String, List<String>> realmAccess = (Map<String, List<String>>) jwt.getClaims().get("realm_access");
        List<String> roles = realmAccess.get("roles");

        if(!userKeycloakId.equals(jwt.getSubject()) && !roles.contains(Role.ADMIN.name())
                && !roles.contains(Role.SUPER_ADMIN.name()) && !roles.contains(Role.CORRECTOR.name()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is not authorized");

        return TestResultMapper.testResultByUserIdAndTestResultIdDto(testResult);
    }

    public void deleteById(Long id) {
        TestResult testResult = service.findById(id, Status.ACTIVE);
        testResult.setStatus(Status.DEACTIVATED);

        service.add(testResult);
    }
}
