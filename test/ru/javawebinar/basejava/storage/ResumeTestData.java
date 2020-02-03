package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResumeTestData {
    static final String UUID_1 = "uuid1";
    static final String UUID_2 = "uuid2";
    static final String UUID_3 = "uuid3";
    static final String UUID_4 = "uuid4";
    static final String NOT_EXISTED_UUID = UUID.randomUUID().toString();
    static final String FULL_NAME_1 = "c";
    static final String FULL_NAME_2 = "b";
    static final String FULL_NAME_3 = "a";
    static final String FULL_NAME_4 = "b";
    static final String NOT_EXISTED_FULL_NAME = UUID.randomUUID().toString();
    static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    static final Resume RESUME_NOT_EXISTED = new Resume(NOT_EXISTED_UUID, NOT_EXISTED_FULL_NAME);


    public static void main(String[] args) {
        Resume resume = new Resume("test resume fullname");

        resume.setContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.setContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LIKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        resume.setContact(ContactType.GITHUB_PROFILE, "https://github.com/gkislin");
        resume.setContact(ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473");
        resume.setContact(ContactType.HOMEPAGE, "http://gkislin.ru/");


        TextSection objectiveSection = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");

        TextSection personalSection = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");

        List<String> achievementsList = new ArrayList<>();
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievementsSection = new ListSection(achievementsList);

        List<String> qualificationsList = new ArrayList<>();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualificationsList.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualificationsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualificationsList.add("Python: Django.");
        qualificationsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualificationsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualificationsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualificationsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualificationsList.add("Родной русский, английский \"upper intermediate\"");
        ListSection qualificationsSection = new ListSection(qualificationsList);


        List<Occupation> experienceList = new ArrayList<>();
        experienceList.add(new Occupation("Автор проекта.",
                                          "Java Online Projects",
                                          LocalDate.of(2013,10,1),
                                          LocalDate.now(),
                                          "Создание, организация и проведение Java онлайн проектов и стажировок.",
                                          "http://javaops.ru/"));

        experienceList.add(new Occupation("Старший разработчик (backend)",
                                          "Wrike",
                                          LocalDate.of(2014,10,1),
                                          LocalDate.of(2016,1,1),
                                          "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                          "https://www.wrike.com/"));

        experienceList.add(new Occupation("Java архитектор",
                                          "RIT Center",
                                          LocalDate.of(2012,4,1),
                                          LocalDate.of(2014,10,1),
                                          "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                                          null));

        experienceList.add(new Occupation("Ведущий программист",
                                          "Luxoft (Deutsche Bank)",
                                          LocalDate.of(2010,12,1),
                                          LocalDate.of(2012,4,1),
                                          "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                                          "http://www.luxoft.ru/"));

        experienceList.add(new Occupation("Ведущий специалист",
                                          "Yota",
                                          LocalDate.of(2008,6,1),
                                          LocalDate.of(2010,12,1),
                                          "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                                          "https://www.yota.ru/"));

        experienceList.add(new Occupation("Разработчик ПО",
                                          "Enkata",
                                          LocalDate.of(2007,3,1),
                                          LocalDate.of(2008,6,1),
                                          "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)",
                                          "http://enkata.com/"));

        experienceList.add(new Occupation("Разработчик ПО",
                                          "Enkata",
                                          LocalDate.of(2007,3,1),
                                          LocalDate.of(2008,6,1),
                                          "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).",
                                          "http://enkata.com/"));

        experienceList.add(new Occupation("Разработчик ПО",
                                          "Siemens AG",
                                          LocalDate.of(2005,1,1),
                                          LocalDate.of(2007,2,1),
                                          "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).",
                                          "https://www.siemens.com/ru/ru/home.html"));

        experienceList.add(new Occupation("Инженер по аппаратному и программному тестированию",
                                          "Alcatel",
                                          LocalDate.of(1997,9,1),
                                          LocalDate.of(2005,1,1),
                                          "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).",
                                          "http://www.alcatel.ru/"));
        OccupationSection experienceSection = new OccupationSection(experienceList);


        List<Occupation> educationList = new ArrayList<>();
        educationList.add(new Occupation("\"Functional Programming Principles in Scala\" by Martin Odersky",
                                         "Coursera",
                                         LocalDate.of(2013,3,1),
                                         LocalDate.of(2013,5,1),
                                         null,
                                         "https://www.coursera.org/course/progfun"));

        educationList.add(new Occupation("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                                         "Luxoft",
                                         LocalDate.of(2011,4,1),
                                         LocalDate.of(2011,2,1),
                                         null,
                                         "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366"));

        educationList.add(new Occupation("3 месяца обучения мобильным IN сетям (Берлин)",
                                         "Siemens AG",
                                         LocalDate.of(2005,1,1),
                                         LocalDate.of(2005,4,1),
                                         null,
                                         "http://www.siemens.ru/"));

        educationList.add(new Occupation("6 месяцев обучения цифровым телефонным сетям (Москва)",
                                         "Alcatel",
                                         LocalDate.of(1997,9,1),
                                         LocalDate.of(2008,3,1),
                                         null,
                                         "http://www.alcatel.ru/"));

        educationList.add(new Occupation("Аспирантура (программист С, С++)",
                                         "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                                         LocalDate.of(1993,9,1),
                                         LocalDate.of(1996,7,1),
                                         null,
                                         "http://www.ifmo.ru/"));

        educationList.add(new Occupation("Инженер (программист Fortran, C)",
                                         "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                                         LocalDate.of(1987,9,1),
                                         LocalDate.of(1993,7,1),
                                         null,
                                         "http://www.ifmo.ru/"));

        educationList.add(new Occupation("Закончил с отличием",
                                         "Заочная физико-техническая школа при МФТИ",
                                         LocalDate.of(1984,9,1),
                                         LocalDate.of(1987,6,1),
                                         null,
                                         "http://www.school.mipt.ru/"));
        OccupationSection educationSection = new OccupationSection(educationList);

        resume.setSection(SectionType.PERSONAL, personalSection);
        resume.setSection(SectionType.OBJECTIVE, objectiveSection);
        resume.setSection(SectionType.ACHIEVEMENTS, achievementsSection);
        resume.setSection(SectionType.QUALIFICATIONS, qualificationsSection);
        resume.setSection(SectionType.EXPERIENCE, experienceSection);
        resume.setSection(SectionType.EDUCATION, educationSection);

        System.out.println(resume);
    }
}
