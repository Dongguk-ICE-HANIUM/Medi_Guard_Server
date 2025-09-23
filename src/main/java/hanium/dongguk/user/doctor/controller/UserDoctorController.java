package hanium.dongguk.user.doctor.controller;

import hanium.dongguk.user.doctor.domain.EDepartment;
import hanium.dongguk.user.doctor.dto.DoctorInfo;
import hanium.dongguk.user.doctor.service.UserDoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class UserDoctorController implements  UserDoctorApiSwagger {

    private final UserDoctorService userDoctorService;

    @Override
    @GetMapping()
    public ResponseEntity<List<DoctorInfo>> searchDoctorList(
            @RequestParam EDepartment department,
            @RequestParam(required = false) String nameKeyWord
            ) {
        return ResponseEntity.ok(userDoctorService.searchDoctorList(department, nameKeyWord));
    }
}
