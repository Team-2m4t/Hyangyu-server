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
public class Fair {

    @Id
    @GeneratedValue
    private Long fairId;

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
    public static Fair createFair(TestEventDto testEventDto) {
        Fair fair = new Fair();
        fair.title = testEventDto.getTitle();
        fair.startDate = testEventDto.getStartDate();
        fair.endDate = testEventDto.getEndDate();
        fair.weekdayOpen = testEventDto.getWeekdayOpen();
        fair.weekdayClose = testEventDto.getWeekdayClose();
        fair.weekendOpen = testEventDto.getWeekendOpen();
        fair.weekendClose = testEventDto.getWeekendClose();
        fair.location = testEventDto.getLocation();
        fair.site = testEventDto.getSite();
        fair.holiday = testEventDto.getHoliday();
        fair.content = testEventDto.getContent();
        fair.photo1 = testEventDto.getPhoto1();
        fair.photo2 = testEventDto.getPhoto2();
        fair.photo3 = testEventDto.getPhoto3();
        fair.price = testEventDto.getPrice();

        return fair;
    }

}
