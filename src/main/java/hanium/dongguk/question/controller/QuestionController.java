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
    public ResponseEntity<Void> saveQuestions(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionSaveRequestDto requestDto,
            @UserId UUID userId) {

        questionService.saveQuestions(userId, date, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> updateQuestions(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Valid @RequestBody QuestionUpdateRequestDto requestDto,
            @UserId UUID userId) {

        questionService.updateQuestions(userId, date, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @GetMapping
    public ResponseEntity<QuestionResponseDto> getQuestionsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @UserId UUID userId) {

        return ResponseEntity.ok(questionService.getQuestionsByDate(userId, date));
    }
}