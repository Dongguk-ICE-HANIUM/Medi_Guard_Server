package hanium.dongguk.question.controller;

import hanium.dongguk.global.annotation.UserId;
import hanium.dongguk.global.dto.ResponseDto;
import hanium.dongguk.question.dto.request.QuestionSaveRequestDto;
import hanium.dongguk.question.dto.request.QuestionUpdateRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController implements QuestionApiSwagger {

    private final QuestionService questionService;

    @Override
    @PostMapping
    public ResponseEntity<ResponseDto<Void>> saveQuestions(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionSaveRequestDto requestDto,
            @UserId UUID userId) {

        questionService.saveQuestions(userId, date, requestDto);
        return ResponseEntity.ok(
                ResponseDto.success(null)
        );
    }

    @Override
    @PutMapping
    public ResponseEntity<ResponseDto<Void>> updateQuestions(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionUpdateRequestDto requestDto,
            @UserId UUID userId) {

        questionService.updateQuestions(userId, date, requestDto);
        return ResponseEntity.ok(
                ResponseDto.success(null)
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<ResponseDto<QuestionResponseDto>> getQuestionsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @UserId UUID userId) {

        return ResponseEntity.ok(
                ResponseDto.success(questionService.getQuestionsByDate(userId, date))
        );
    }
}