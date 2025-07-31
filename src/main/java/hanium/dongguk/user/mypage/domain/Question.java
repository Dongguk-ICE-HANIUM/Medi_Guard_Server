package hanium.dongguk.user.mypage.domain;

import hanium.dongguk.global.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false)
    private String type;    // 질문 타입

    @Column(columnDefinition = "TEXT") // 긴 답변 대비
    private String answer;  // 사용자가 작성한 답변
}