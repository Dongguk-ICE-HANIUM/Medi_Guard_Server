package hanium.dongguk.user.core.service;

import hanium.dongguk.user.core.domain.UserRepository;
import hanium.dongguk.user.core.dto.request.NormalRegisterRequestDto;
import hanium.dongguk.user.core.validator.UserValidator;
import hanium.dongguk.user.patient.UserPatient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRetriever userRetriever;
    private final UserSaver userSaver;

    @Transactional
    public void normalRegister(NormalRegisterRequestDto request)
    {

        LocalDate birthday = LocalDate.parse(request.birthday());
        LocalDate dueDate = LocalDate.parse(request.dueDate());

        userValidator.validateBirthday(birthday);
        userValidator.validateDueDate(dueDate);
        userValidator.validateEmailNotExist(request.email());


        UserPatient userPatient = UserPatient.normalCreate(
                request.email(),
                encodePassword(request.password()),
                request.name(),
                birthday,
                request.height(),
                request.weight(),
                dueDate,
                request.pregnancyWeek(),
                request.feeding());

        userSaver.saveUser(userPatient);
    }


    // == password 암호화 == //
    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }


}
