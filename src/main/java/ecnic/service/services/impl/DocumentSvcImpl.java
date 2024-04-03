package ecnic.service.services.impl;

import ecnic.service.exceptions.TemplateException;
import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;
import ecnic.service.repositories.DocumentRepository;
import ecnic.service.services.DocumentSvc;
import ecnic.service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@Slf4j
public class DocumentSvcImpl implements DocumentSvc {

    private final DocumentRepository documentRepository;

    public DocumentSvcImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public ListPaging<Document> getDocuments(Integer page, Integer size) {
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Document> pd = documentRepository.findAll(pageRequest);
        if(Objects.isNull(pd))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Documents is empty");
        ListPaging lp = new ListPaging<Document>();
        lp.setData(pd.getContent());
        lp.setTotalPage(pd.getTotalPages());
        lp.setTotalData(pd.getTotalElements());
        return lp;
    }


    @Override
    public Document getDocumentById(Integer id) {
        Document document = documentRepository.findById(id).orElse(null);
        if (Objects.isNull(document))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TemplateException.DOCUMENT_NOT_FOUND.formatted(id));
        return document;
    }

    @Override
    public Document saveDocument(String originalFilename, String contentType, byte[] bytes) {
        Document doc = new Document();
        doc.setData(bytes);
        doc.setFileName(originalFilename);
        doc.setContentType(contentType);
        doc.setCreatedBy(Constants.USER_TYPE.ADMIN.toString());
        doc.setModifiedBy(Constants.USER_TYPE.ADMIN.toString());
        documentRepository.saveAndFlush(doc);
        return doc;
    }

    @Override
    public void deleteDocumentById(Integer id) {
        Document document = documentRepository.findById(id).orElse(null);
        if (Objects.isNull(document))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TemplateException.DOCUMENT_NOT_FOUND.formatted(id));

        documentRepository.deleteById(id);
    }

    @Override
    public Document changeFileName(Integer id, String newFileName) {
        Document document = documentRepository.findById(id).orElse(null);
        if (Objects.isNull(document))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TemplateException.DOCUMENT_NOT_FOUND.formatted(id));

        document.setFileName(newFileName);
        document.setModifiedBy(Constants.USER_TYPE.ADMIN.toString());
        documentRepository.saveAndFlush(document);
        return document;
    }


}
