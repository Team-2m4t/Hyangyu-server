package hyangyu.server.domain;

import hyangyu.server.dto.event.TestEventDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Festival {

    @Id
    @GeneratedValue
    private Long festivalId;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String title;

    private int likey;

    private int reviews;

    @NotNull
    private Time weekdayOpen;

    @NotNull
    private Time weekdayClose;

    @NotNull
    private Time weekendOpen;

    @NotNull
    private Time weekendClose;

    @NotNull
    private String location;

    @NotNull
    private String site;

    private String holiday;

    @NotNull
    private String content;

    @NotNull
    private String photo1;

    private String photo2;

    private String photo3;

    @NotNull
    private int price;

    //생성 메서드
    public static Festival createFestival(TestEventDto testEventDto) {
        Festival festival = new Festival();
        festival.title = testEventDto.getTitle();
        festival.startDate = testEventDto.getStartDate();
        festival.endDate = testEventDto.getEndDate();
        festival.weekdayOpen = testEventDto.getWeekdayOpen();
        festival.weekdayClose = testEventDto.getWeekdayClose();
        festival.weekendOpen = testEventDto.getWeekendOpen();
        festival.weekendClose = testEventDto.getWeekendClose();
        festival.location = testEventDto.getLocation();
        festival.site = testEventDto.getSite();
        festival.holiday = testEventDto.getHoliday();
        festival.content = testEventDto.getContent();
        festival.photo1 = testEventDto.getPhoto1();
        festival.photo2 = testEventDto.getPhoto2();
        festival.photo3 = testEventDto.getPhoto3();
        festival.price = testEventDto.getPrice();

        return festival;
    }

}
