package hanium.dongguk.question.controller;

import hanium.dongguk.question.dto.request.QuestionSaveRequestDto;
import hanium.dongguk.question.dto.request.QuestionUpdateRequestDto;
import hanium.dongguk.question.dto.response.QuestionResponseDto;
import hanium.dongguk.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import hanium.dongguk.user.core.dto.UserSecurityForm;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Tag(name = "오늘의 질문", description = "오늘의 질문 저장, 수정, 조회 기능 제공")
public class QuestionController {

    private final QuestionService questionService;

    /**
     * 오늘의 질문 저장
     */
    @Operation(
            summary = "오늘의 질문 저장",
            description = "로그인된 환자의 오늘의 질문을 저장합니다."
    )
    @PostMapping
    public ResponseEntity<QuestionResponseDto> saveQuestions(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody QuestionSaveRequestDto requestDto) {

        return ResponseEntity.ok(
                questionService.saveQuestions(loginUser.getId(), date, requestDto)
        );
    }

    /**
     * 오늘의 질문 수정
     */
    @Operation(
            summary = "오늘의 질문 수정",
            description = "로그인된 환자의 오늘의 질문을 수정합니다."
    )
    @PutMapping
    public ResponseEntity<QuestionResponseDto> updateQuestions(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody QuestionUpdateRequestDto requestDto) {

        return ResponseEntity.ok(
                questionService.updateQuestions(loginUser.getId(), date, requestDto)
        );
    }

    /**
     * 특정 날짜 기준 질문 조회
     */
    @Operation(
            summary = "오늘의 질문 조회",
            description = "로그인된 환자의 특정 날짜 질문을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<QuestionResponseDto> getQuestionsByDate(
            @AuthenticationPrincipal UserSecurityForm loginUser,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                questionService.getQuestionsByDate(loginUser.getId(), date)
        );
    }
}