package ecnic.service.services;


import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;

public interface DocumentSvc {
    ListPaging<Document> getDocuments(Integer page, Integer size);

    Document getDocumentById(Integer id);

    Document saveDocument(String originalFilename, String contentType, byte[] bytes);

    void deleteDocumentById(Integer id);

    Document changeFileName(Integer id, String newFileName);
}
