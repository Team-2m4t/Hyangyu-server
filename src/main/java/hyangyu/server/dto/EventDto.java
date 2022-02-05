package hyangyu.server.dto;
import hyangyu.server.domain.Display;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@AllArgsConstructor
public class EventDto {
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
        this.title = display.getTitle();
        this.startDate = display.getStartDate();
        this.endDate = display.getEndDate();
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

}


