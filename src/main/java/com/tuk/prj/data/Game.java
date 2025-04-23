package com.tuk.prj.data;

import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Game extends AbstractEntity {

    private String title;
    private String publisher;
    private LocalDate publishdate;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public LocalDate getPublishdate() {
        return publishdate;
    }
    public void setPublishdate(LocalDate publishdate) {
        this.publishdate = publishdate;
    }

}
