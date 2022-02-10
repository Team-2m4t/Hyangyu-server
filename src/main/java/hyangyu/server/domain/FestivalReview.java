package hyangyu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalReview {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "festival_id")
    @NotNull
    private Festival festival;

    @OneToMany(mappedBy = "festivalReview", cascade = CascadeType.ALL)
    private List<FestivalWarn> warnList = new ArrayList<>();

    @NotNull
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    @NotNull
    @Column(length = 300)
    private String content;

    @NotNull
    private Integer score;

    @NotNull
    private Integer warn;

    // 생성 메서드
    public static FestivalReview createFestivalReview(User user, Festival festival, LocalDateTime createTime, String content, Integer score, Integer warn) {
        FestivalReview festivalReview = new FestivalReview();
        festivalReview.user = user;
        festivalReview.festival = festival;
        festivalReview.createTime = createTime;
        festivalReview.content = content;
        festivalReview.score = score;
        festivalReview.warn = warn;
        return festivalReview;
    }

    public void updateFestivalReview(String content, Integer score) {
        this.content = content;
        this.score = score;
    }

    public void increaseWarn() {
        this.warn += 1;
    }
}
