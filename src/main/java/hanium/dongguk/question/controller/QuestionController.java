package hanium.dongguk.question.controller;

import hanium.dongguk.global.annotation.UserId;
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
    public ResponseEntity<QuestionResponseDto> saveQuestions(
            @UserId UUID userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionSaveRequestDto requestDto) {

        return ResponseEntity.ok(
                questionService.saveQuestions(userId, date, requestDto)
        );
    }

    @Override
    @PutMapping
    public ResponseEntity<QuestionResponseDto> updateQuestions(
            @UserId UUID userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionUpdateRequestDto requestDto) {

        return ResponseEntity.ok(
                questionService.updateQuestions(userId, date, requestDto)
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<QuestionResponseDto> getQuestionsByDate(
            @UserId UUID userId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                questionService.getQuestionsByDate(userId, date)
        );
    }
}