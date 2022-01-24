package hyangyu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayReview {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "display_id")
    @NotNull
    private Display display;

    @NotNull
    @Column(length = 20)
    private String nickname;

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    @NotNull
    @Column(length = 300)
    private String content;

    @NotNull
    private Integer score;

    // 생성 메서드
    public static DisplayReview createDisplayReview(User user, Display display, String nickname, LocalDateTime createTime, String content, Integer score) {
        DisplayReview displayReview = new DisplayReview();
        displayReview.user = user;
        displayReview.display = display;
        displayReview.nickname = nickname;
        displayReview.createTime = createTime;
        displayReview.content = content;
        displayReview.score = score;
        return displayReview;
    }
}
