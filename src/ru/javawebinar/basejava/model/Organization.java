package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final List<PositionDetails> positionDetailsList = new ArrayList<>();
    private final String companyName;
    private final String url;

    public Organization(String companyName, String url) {
        this.companyName = companyName;
        this.url = url;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUrl() {
        return url;
    }

    public Organization addPositionDetails(String position, LocalDate startDate, LocalDate endDate, String description){
        this.positionDetailsList.add(new PositionDetails(position, startDate, endDate, description));
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return positionDetailsList.equals(that.positionDetailsList) && companyName.equals(that.companyName) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionDetailsList, companyName, url);
    }

    @Override
    public String toString() {
        return "Occupation{" + "companyName='" + companyName + '\'' + ", positionDetailsList=" + positionDetailsList + ", url='" + url + '\'' + '}';
    }

    public static class  PositionDetails implements Serializable{
        private static final long serialVersionUID = 1L;

        private final String title;
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String description;

        public PositionDetails(String title, LocalDate startDate, LocalDate endDate, String description) {
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PositionDetails that = (PositionDetails) o;
            return title.equals(that.title) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, startDate, endDate, description);
        }

        @Override
        public String toString() {
            return "PositionDetails{" +
                    "description='" + description + '\'' +
                    ", endDate=" + endDate +
                    ", startDate=" + startDate +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}
