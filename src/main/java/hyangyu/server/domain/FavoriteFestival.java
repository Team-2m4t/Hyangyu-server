package hyangyu.server.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteFestival {

    @EmbeddedId
    private FavoriteFestivalId favoriteFestivalId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("festivalId")
    @JoinColumn(name = "festival_id")
    private Festival festival;

    //생성 메서드
    public FavoriteFestival(User user, Festival festival) {
        FavoriteFestivalId favoriteFestivalId = new FavoriteFestivalId(user.getUserId(), festival.getFestivalId());
        this.favoriteFestivalId = favoriteFestivalId;
        this.user = user;
        this.festival = festival;
    }

}
