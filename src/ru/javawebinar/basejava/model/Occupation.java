package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Occupation {
    private final String position;
    private final String companyName;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String description;
    private final String url;

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

    public String getCompanyName() {
        return companyName;
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

    public String getUrl() {
        return url;
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
