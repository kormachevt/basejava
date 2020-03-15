package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<PositionDetails> positionDetailsList = new ArrayList<>();
    private String companyName;
    private String url;

    public Organization(String companyName, String url) {
        this.companyName = companyName;
        this.url = url;
    }

    public Organization() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getUrl() {
        return url;
    }

    public List<PositionDetails> getPositionDetailsList() {
        return positionDetailsList;
    }

    public void setPositionDetailsList(List<PositionDetails> positionDetailsList) {
        this.positionDetailsList = positionDetailsList;
    }

    public Organization addPositionDetails(String position, LocalDate startDate, LocalDate endDate, String description) {
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class PositionDetails implements Serializable {
        private static final long serialVersionUID = 1L;

        private String title;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
        private LocalDate endDate;
        private String description;

        public PositionDetails(String title, LocalDate startDate, LocalDate endDate, String description) {
            this.title = Objects.requireNonNull(title);
            this.startDate = Objects.requireNonNull(startDate);
            this.endDate = Objects.requireNonNull(endDate);
            this.description = description;
        }

        public PositionDetails() {
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
