package hyangyu.server.dto;

import hyangyu.server.domain.Display;
import hyangyu.server.domain.Fair;
import hyangyu.server.domain.Festival;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@AllArgsConstructor
public class EventDto {
    private Long eventId;
    private String title;
    private Date startDate;
    private Date endDate;
    private Time weekdayOpen;
    private Time weekdayClose;
    private Time weekendOpen;
    private Time weekendClose;
    private String location;
    private String site;
    private String holiday;
    private String content;
    private String photo1;
    private String photo2;
    private String photo3;
    private int price;
    private boolean isSaved;

    public EventDto(Display display) {
        this.eventId = display.getDisplayId();
        this.startDate = display.getStartDate();
        this.endDate = display.getEndDate();
        this.title = display.getTitle();
        this.weekdayOpen = display.getWeekdayOpen();
        this.weekdayClose = display.getWeekdayClose();
        this.weekendOpen = display.getWeekendOpen();
        this.weekendClose = display.getWeekendClose();
        this.location = display.getLocation();
        this.site = display.getSite();
        this.holiday = display.getHoliday();
        this.content = display.getContent();
        this.photo1 = display.getPhoto1();
        this.photo2 = display.getPhoto2();
        this.photo3 = display.getPhoto3();
        this.price = display.getPrice();
    }

    public EventDto(Fair fair) {
        this.eventId = fair.getFairId();
        this.startDate = fair.getStartDate();
        this.endDate = fair.getEndDate();
        this.title = fair.getTitle();
        this.weekdayOpen = fair.getWeekdayOpen();
        this.weekdayClose = fair.getWeekdayClose();
        this.weekendOpen = fair.getWeekendOpen();
        this.weekendClose = fair.getWeekendClose();
        this.location = fair.getLocation();
        this.site = fair.getSite();
        this.holiday = fair.getHoliday();
        this.content = fair.getContent();
        this.photo1 = fair.getPhoto1();
        this.photo2 = fair.getPhoto2();
        this.photo3 = fair.getPhoto3();
        this.price = fair.getPrice();

    }

    public EventDto(Festival festival) {
        this.eventId = festival.getFestivalId();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.title = festival.getTitle();
        this.weekdayOpen = festival.getWeekdayOpen();
        this.weekdayClose = festival.getWeekdayClose();
        this.weekendOpen = festival.getWeekendOpen();
        this.weekendClose = festival.getWeekendClose();
        this.location = festival.getLocation();
        this.site = festival.getSite();
        this.holiday = festival.getHoliday();
        this.content = festival.getContent();
        this.photo1 = festival.getPhoto1();
        this.photo2 = festival.getPhoto2();
        this.photo3 = festival.getPhoto3();
        this.price = festival.getPrice();

    }
}
