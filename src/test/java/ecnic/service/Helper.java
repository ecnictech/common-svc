package ecnic.service;


import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;

import java.time.ZonedDateTime;
import java.util.List;

public final class Helper {

    private final static String USER = "USER_TEST";

    public static Document documentDummy() {
        Document document = new Document();
        document.setId(1);
        document.setCreatedBy(USER);
        document.setFileName("file_test.txt");
        document.setContentType("text/plain");
        document.setData("Test".getBytes());
        document.setCreatedDtm(ZonedDateTime.now());
        document.setModifiedDtm(ZonedDateTime.now());
        return document;
    }

    public static ListPaging<Document> listDocumentDummy() {
        ListPaging<Document> expectedListPaging = new ListPaging<>();
        expectedListPaging.setTotalPage(1);
        expectedListPaging.setTotalData(2L);
        expectedListPaging.setData(List.of(documentDummy()));
        return expectedListPaging;
    }

}
