package hyangyu.server.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayReview {

    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_id")
    @NotNull
    private Display display;

    @OneToMany(mappedBy = "displayReview", cascade = CascadeType.ALL)
    private List<DisplayWarn> warnList = new ArrayList<>();

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
    public static DisplayReview createDisplayReview(User user, Display display, LocalDateTime createTime, String content, Integer score, Integer warn) {
        DisplayReview displayReview = new DisplayReview();
        displayReview.user = user;
        displayReview.display = display;
        displayReview.createTime = createTime;
        displayReview.content = content;
        displayReview.score = score;
        displayReview.warn = warn;
        return displayReview;
    }

    public void updateDisplayReview(String content, Integer score) {
        this.content = content;
        this.score = score;
    }

    public void increaseWarn() {
        this.warn += 1;
    }
}
