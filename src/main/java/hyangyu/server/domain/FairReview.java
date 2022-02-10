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
public class FairReview {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fair_id")
    @NotNull
    private Fair fair;

    @OneToMany(mappedBy = "fairReview", cascade = CascadeType.ALL)
    private List<FairWarn> warnList = new ArrayList<>();

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
    public static FairReview createFairReview(User user, Fair fair, LocalDateTime createTime, String content, Integer score, Integer warn) {
        FairReview fairReview = new FairReview();
        fairReview.user = user;
        fairReview.fair = fair;
        fairReview.createTime = createTime;
        fairReview.content = content;
        fairReview.score = score;
        fairReview.warn = warn;
        return fairReview;
    }

    public void updateFairReview(String content, Integer score) {
        this.content = content;
        this.score = score;
    }

    public void increaseWarn() {
        this.warn += 1;
    }
}
