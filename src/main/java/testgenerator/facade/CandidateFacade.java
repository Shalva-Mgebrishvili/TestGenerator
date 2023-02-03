package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.*;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.enums.TestStatus;
import testgenerator.model.mapper.CandidateMapper;
import testgenerator.model.params.CandidateAddParam;
import testgenerator.service.*;


import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateFacade {

    private final CandidateService service;
    private final UserService userService;
    private final KeycloakService keycloakService;
    private final TestResultService testResultService;
    private final TestQuestionService testQuestionService;
    private final TestService testService;


    public CandidateDto findById(Long id) {
        Candidate candidate = service.findById(id, Status.ACTIVE);

        return CandidateMapper.candidateDto(candidate);
    }

    public Page<CandidateDto> findAll(Pageable pageable) {
        Page<Candidate> allCandidates = service.findAll(Status.ACTIVE, pageable);

        return allCandidates.map(CandidateMapper::candidateDto);
    }

    @Transactional
    public void create(Long testId, CandidateAddParam param) {
        Test test = testService.findById(testId, Status.ACTIVE);

        if (test.getTestStatus() != TestStatus.CREATED)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Test with Id: " + testId + " isn't available");

        List<UserEntity> users = param.getUser().stream().map
                (currentUser -> userService.findById(currentUser, Status.ACTIVE)).toList();

        double totalPoint = testQuestionService.getPointSum(test);
        List<Candidate> candidateList = new ArrayList<>();

        List<Stack> testStacks = test.getTestStacks().stream().map(TestStack::getStack).toList();

        users.forEach(currUser -> {
            if(currUser.getStacks().isEmpty())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "User with Id " + currUser.getId() + " does not have stacks");

            if (service.existsByUserIdAndStatus(currUser.getId(), Status.ACTIVE)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Candidate with this userId already exists.");
            }

            List<Stack> userStackList = currUser.getStacks();

            testStacks.forEach(stack -> {
                if (!userStackList.contains(stack))
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "User with Id " + currUser.getId() + " does not belong to given stack(s)");
            });

            Candidate candidate = new Candidate(oneTimeUsername(), oneTimePassword(), currUser);
            candidate.setStatus(Status.ACTIVE);
            service.add(candidate);
            candidateList.add(candidate);

            UserEntity candidateUser = new UserEntity(candidate.getOneTimeUsername(), currUser.getName(),
                    currUser.getSurname(), currUser.getEmail(), Role.CANDIDATE, new ArrayList<>(), new ArrayList<>());

            Response response = keycloakService.addUserInKeycloak(candidateUser, candidate.getOneTimePassword());
            keycloakService.changeUserKeycloakRole(candidateUser, "CANDIDATE");

            if (response.getStatus() != HttpStatus.CREATED.value())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User creation failed on keycloak");

            TestResult testResult = new TestResult(null, null, null,
                    totalPoint, null, test, new ArrayList<>(), currUser, candidate);

            testResult.setStatus(Status.ACTIVE);

            testResultService.add(testResult);
        });
    }

    @Transactional
    public void createForYourself(Long testId, Jwt jwt) {
        Test test = testService.findById(testId, Status.ACTIVE);

        if (test.getTestStatus() != TestStatus.CREATED)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Test with Id: " + testId + " isn't available");

        String email = (String) jwt.getClaims().get("email");
        UserEntity user = userService.findByEmail(email, Status.ACTIVE);

        if(user.getStacks().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User with Id " + user.getId() + " does not have stacks");

        if (service.existsByUserIdAndStatus(user.getId(), Status.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Candidate with this userId already exists.");
        }

        List<Stack> testStacks = test.getTestStacks().stream().map(TestStack::getStack).toList();

        testStacks.forEach(stack -> {
            if (!user.getStacks().contains(stack))
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "You do not belong to given stack(s)");
        });

        Candidate candidate = new Candidate(oneTimeUsername(), oneTimePassword(), user);
        candidate.setStatus(Status.ACTIVE);
        service.add(candidate);

        UserEntity candidateUser = new UserEntity(candidate.getOneTimeUsername(), user.getName(),
                user.getSurname(), user.getEmail(), Role.CANDIDATE, new ArrayList<>(), new ArrayList<>());

        Response response = keycloakService.addUserInKeycloak(candidateUser, candidate.getOneTimePassword());
        keycloakService.changeUserKeycloakRole(candidateUser, "CANDIDATE");

        if (response.getStatus() != HttpStatus.CREATED.value())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User creation failed on keycloak");

        double totalPoint = testQuestionService.getPointSum(test);

        TestResult testResult = new TestResult(null, null, null,
                totalPoint, null, test, new ArrayList<>(), user, candidate);

        testResult.setStatus(Status.ACTIVE);

        testResultService.add(testResult);
    }

    private String oneTimeUsername() {
        return RandomStringUtils.random(10, true, true).toLowerCase();
    }

    private String oneTimePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "INVALID_SPECIAL_CHARS";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
        return password;
    }
}
