package testgenerator.facade;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.model.domain.Candidate;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.dto.CandidateDto;
import testgenerator.model.enums.Role;
import testgenerator.model.enums.Status;
import testgenerator.model.mapper.CandidateMapper;
import testgenerator.model.params.CandidateAddParam;
import testgenerator.service.CandidateService;
import testgenerator.service.KeycloakService;
import testgenerator.service.UserService;


import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateFacade {

    private final CandidateService service;
    private final UserService userService;
    private final KeycloakService keycloakService;


//    public CandidateDto findById(Long id) {
//        Candidate candidate = service.findById(id, Status.ACTIVE);
//
//        return CandidateMapper.candidateDto(candidate);
//    }
//
//    public Page<CandidateDto> findAll(Pageable pageable){
//        Page<Candidate> allCandidates = service.findAll(Status.ACTIVE, pageable);
//
//        return allCandidates.map(CandidateMapper::candidateDto);
//    }

    @Transactional
    public CandidateDto add(CandidateAddParam param) {
        UserEntity user = userService.findById(param.getUser(), Status.ACTIVE);

        if(service.existsByUserIdAndStatus(user.getId(), Status.ACTIVE))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Candidate with this userId already exists.");

        Candidate candidate = new Candidate(oneTimeUsername(), oneTimePassword(), user);
        candidate.setStatus(Status.ACTIVE);
        service.add(candidate);

        UserEntity candidateUser = new UserEntity(oneTimeUsername(), user.getName(), user.getSurname(),
                user.getEmail(), Role.CANDIDATE, new ArrayList<>(), new ArrayList<>());
        candidateUser.setId(candidate.getId());

        Response response = keycloakService.addUserInKeycloak(candidateUser, candidate.getOneTimePassword());

        if(response.getStatus() != HttpStatus.CREATED.value())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User creation failed on keycloak");

        return CandidateMapper.candidateDto(candidate);
    }

//    public CandidateDto update(Long id, CandidateUpdateParam param) {
//        TestResult testResult = testResultService.findById(param.getTestResult(), Status.ACTIVE);
//        Candidate updateCandidate = service.findById(id,Status.ACTIVE);
//        updateCandidate.getTestResults().add(testResult);
//
//        return CandidateMapper.candidateDto(service.add(updateCandidate));
//        return null;
//    }

//    public void deleteById(Long id) {
//        Candidate candidate = service.findById(id, Status.ACTIVE);
//        candidate.setStatus(Status.DEACTIVATED);
//
//        service.add(candidate);
//    }

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
