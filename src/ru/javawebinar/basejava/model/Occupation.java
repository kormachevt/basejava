package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Occupation {
    private String position;
    private String companyName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String url;

    public Occupation(String position, String companyName, LocalDate startDate, LocalDate endDate, String description,
                      String url) {
        this.position = Objects.requireNonNull(position);
        this.companyName = Objects.requireNonNull(companyName);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.description = description;
        this.url = url;
    }

    public String getObjective() {
        return position;
    }

    public void setObjective(String objective) {
        this.position = objective;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Occupation that = (Occupation) o;
        return position.equals(that.position)
                && companyName.equals(that.companyName)
                && startDate.equals(that.startDate)
                && endDate.equals(that.endDate)
                && Objects.equals(description, that.description)
                && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, companyName, startDate, endDate, description, url);
    }

    @Override
    public String toString() {
        return "Occupation{position='" + position + '\'' + ", companyName='" + companyName + '\'' + ", startDate=" + startDate + ", endDate=" + endDate + ", description='" + description + '\'' + ", url='" + url + '\'' + '}';
    }
}
