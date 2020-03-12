package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamSerializer implements StreamSerializer{
    private XmlParser xmlParser;

    public XMLStreamSerializer() {
        xmlParser = new XmlParser(Resume.class,
                                  Organization.class,
                                  Organization.PositionDetails.class,
                                  OrganizationSection.class,
                                  ContactType.class,
                                  ListSection.class,
                                  SectionType.class,
                                  TextSection.class);
    }

    @Override
    public void write(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)){
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException{
        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)){
            return xmlParser.unmarshall(reader);
        }
    }
}
