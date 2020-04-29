package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = "uuid1";
    public static final String UUID_2 = "uuid2";
    public static final String UUID_3 = "uuid3";
    public static final String UUID_4 = "uuid4";
    public static final String NOT_EXISTED_UUID = UUID.randomUUID().toString();
    public static final String FULL_NAME_1 = "c";
    public static final String FULL_NAME_2 = "b";
    public static final String FULL_NAME_3 = "a";
    public static final String FULL_NAME_4 = "b";
    public static final String NOT_EXISTED_FULL_NAME = UUID.randomUUID().toString();
//    static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
//    static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
//    static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
//    static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
//    static final Resume RESUME_NOT_EXISTED = new Resume(NOT_EXISTED_UUID, NOT_EXISTED_FULL_NAME);

    public static final Resume RESUME_1 = fillWithData(new Resume(UUID_1, FULL_NAME_1));
    public static final Resume RESUME_2 = fillWithData(new Resume(UUID_2, FULL_NAME_2));
    public static final Resume RESUME_3 = fillWithData(new Resume(UUID_3, FULL_NAME_3));
    public static final Resume RESUME_4 = fillWithData(new Resume(UUID_4, FULL_NAME_4));
    public static final Resume RESUME_NOT_EXISTED = fillWithData(new Resume(NOT_EXISTED_UUID, NOT_EXISTED_FULL_NAME));

    public static void main(String[] args) {
        Resume resume = new Resume("test resume fullname");
        fillWithData(resume);
        System.out.println(resume);
    }

    private static Resume fillWithData(Resume resume) {
        resume.addContact(ContactType.PHONE_NUMBER, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LIKEDIN_PROFILE, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB_PROFILE, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW_PROFILE, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");


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
//
//
//        List<Organization> experienceList = new ArrayList<>();
//        experienceList.add(new Organization("Java Online Projects", "http://javaops.ru/")
//                                   .addPositionDetails("Автор проекта.",
//                                                       of(2013, Month.OCTOBER),
//                                                       LocalDate.now(),
//                                                       "Создание, организация и проведение Java онлайн проектов и стажировок."));
//
//        experienceList.add(new Organization("Wrike", "https://www.wrike.com/")
//                                   .addPositionDetails("Старший разработчик (backend)",
//                                                       of(2014, Month.OCTOBER),
//                                                       of(2016, Month.JANUARY),
//                                                       "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
//
//        experienceList.add(new Organization("RIT Center", null)
//                                   .addPositionDetails("Java архитектор",
//                                                       of(2012, Month.APRIL),
//                                                       of(2014, Month.OCTOBER),
//                                                       "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
//
//        experienceList.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/")
//                                   .addPositionDetails("Ведущий программист",
//                                                       of(2010, Month.DECEMBER),
//                                                       of(2012, Month.APRIL),
//                                                       "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
//
//        experienceList.add(new Organization("Yota", "https://www.yota.ru/")
//                                   .addPositionDetails("Ведущий специалист",
//                                                       of(2008, Month.JUNE),
//                                                       of(2010, Month.DECEMBER),
//                                                       "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
//
//        experienceList.add(new Organization("Enkata", "http://enkata.com/")
//                                   .addPositionDetails("Разработчик ПО",
//                                                       of(2007, Month.MARCH),
//                                                       of(2008, Month.JUNE),
//                                                       "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
//
//        experienceList.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html")
//                                   .addPositionDetails("Разработчик ПО",
//                                                       of(2005, Month.JANUARY),
//                                                       of(2007, Month.FEBRUARY),
//                                                       "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
//
//        experienceList.add(new Organization("Alcatel", "http://www.alcatel.ru/")
//                                   .addPositionDetails("Инженер по аппаратному и программному тестированию",
//                                                       of(2005, Month.JANUARY),
//                                                       of(2007, Month.FEBRUARY),
//                                                       "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
//
//        OrganizationSection experienceSection = new OrganizationSection(experienceList);
//
//
//        List<Organization> educationList = new ArrayList<>();
//        educationList.add(new Organization("Coursera", "https://www.coursera.org/course/progfun")
//                                  .addPositionDetails("\"Functional Programming Principles in Scala\" by Martin Odersky",
//                                                      of(2013, Month.MARCH),
//                                                      of(2013, Month.MAY),
//                                                      null));
//
//        educationList.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
//                                  .addPositionDetails("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
//                                                      of(2011, Month.APRIL),
//                                                      of(2011, Month.FEBRUARY),
//                                                      null));
//
//        educationList.add(new Organization("Siemens AG", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366")
//                                  .addPositionDetails("3 месяца обучения мобильным IN сетям (Берлин)",
//                                                      of(2005, Month.JANUARY),
//                                                      of(2005, Month.APRIL),
//                                                      null));
//
//        educationList.add(new Organization("Alcatel", "http://www.alcatel.ru/")
//                                  .addPositionDetails("6 месяцев обучения цифровым телефонным сетям (Москва)",
//                                                      of(1997, Month.SEPTEMBER),
//                                                      of(2008, Month.MARCH),
//                                                      null));
//
//        educationList.add(new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/")
//                                  .addPositionDetails("Аспирантура (программист С, С++)",
//                                                      of(1993, Month.SEPTEMBER),
//                                                      of(1996, Month.JULY),
//                                                      null)
//                                  .addPositionDetails("Инженер (программист Fortran, C)",
//                                                      of(1987, Month.SEPTEMBER),
//                                                      of(1993, Month.JULY),
//                                                      null));
//
//        educationList.add(new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/")
//                                  .addPositionDetails("Закончил с отличием",
//                                                      of(1984, Month.SEPTEMBER),
//                                                      of(1987, Month.JUNE),
//                                                      null));
//        OrganizationSection educationSection = new OrganizationSection(educationList);
//
        resume.setSection(SectionType.PERSONAL, personalSection);
        resume.setSection(SectionType.OBJECTIVE, objectiveSection);
        resume.setSection(SectionType.ACHIEVEMENTS, achievementsSection);
        resume.setSection(SectionType.QUALIFICATIONS, qualificationsSection);
//        resume.setSection(SectionType.EXPERIENCE, experienceSection);
//        resume.setSection(SectionType.EDUCATION, educationSection);
//
        return resume;
    }
}
