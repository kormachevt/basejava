package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public enum ContactType {
    PHONE_NUMBER("Телефон"),
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + "<img src='img/skype.png'> " + value + "</a>";
        }
    },
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + "<img src='img/email.png'> " + value + "</a>";
        }
    },
    LIKEDIN_PROFILE("Профиль LinkedIn") {
        @Override
        public String toHtml0(String value) {
            return "<a href=" + value + ">" + "<img src='img/lin.png'> " + value + "</a>";
        }
    },
    GITHUB_PROFILE("Профиль GitHub") {
        @Override
        public String toHtml0(String value) {
            return "<a href=" + value + ">" + "<img src='img/gh.png'> " + value + "</a>";
        }
    },
    STACKOVERFLOW_PROFILE("Профиль Stackoverflow") {
        @Override
        public String toHtml0(String value) {
            return "<a href=" + value + ">" + "<img src='img/so.png'> " + value + "</a>";
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        public String toHtml0(String value) {
            return "<a href=" + value + ">" + "<img src='img/home.svg' width='16' height='16'> " + value + "</a>";
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}
