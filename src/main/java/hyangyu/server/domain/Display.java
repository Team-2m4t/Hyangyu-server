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
public class Display {

    @Id
    @GeneratedValue
    private Long displayId;

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
    public static Display createDisplay(TestEventDto testEventDto) {
        Display display = new Display();
        display.title = testEventDto.getTitle();
        display.startDate = testEventDto.getStartDate();
        display.endDate = testEventDto.getEndDate();
        display.weekdayOpen = testEventDto.getWeekdayOpen();
        display.weekdayClose = testEventDto.getWeekdayClose();
        display.weekendOpen = testEventDto.getWeekendOpen();
        display.weekendClose = testEventDto.getWeekendClose();
        display.location = testEventDto.getLocation();
        display.site = testEventDto.getSite();
        display.holiday = testEventDto.getHoliday();
        display.content = testEventDto.getContent();
        display.photo1 = testEventDto.getPhoto1();
        display.photo2 = testEventDto.getPhoto2();
        display.photo3 = testEventDto.getPhoto3();
        display.price = testEventDto.getPrice();

        return display;
    }
}
