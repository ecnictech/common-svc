package ecnic.service.service.impl;

import ecnic.service.Helper;
import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;
import ecnic.service.repositories.DocumentRepository;
import ecnic.service.services.impl.DocumentSvcImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@Tags(value = {
        @Tag(value = "services"),
        @Tag(value = "controller"),
        @Tag(value = "integration")
})
public class DocumentSvcImplTest {
    @Mock
    private DocumentRepository repository;
    @InjectMocks
    private DocumentSvcImpl underTest;

    @Test
    public void testGetDocumentsFound() {
        int pageNumber = 0;
        int pageSize = 10;
        List<Document> expectedList = Helper.listDocumentDummy().getData();
        Page<Document> expectedPage = new PageImpl<>(expectedList);
        when(repository.findAll(any(PageRequest.class))).thenReturn(expectedPage);
        ListPaging<Document> actualList = underTest.getDocuments(pageNumber, pageSize);
        assertEquals(expectedPage.getTotalPages(), actualList.getData().size());
        assertEquals(expectedPage.getTotalPages(), actualList.getTotalPage());
        assertEquals(expectedPage.getTotalElements(), actualList.getTotalData());
    }

    @Test
    public void testGetDocumentsNotFound() {
        int pageNumber = 0;
        int pageSize = 10;
        when(repository.findAll(any(PageRequest.class))).thenReturn(null);
        assertThrows(ResponseStatusException.class, () -> underTest.getDocuments(pageNumber, pageSize));
    }

    @Test
    void testGetDocumentByIdFound() {
        Document expectedDocument = Helper.documentDummy();
        Optional<Document> optionalDocument = Optional.of(expectedDocument);
        when(repository.findById(anyInt())).thenReturn(optionalDocument);

        Document actualDocument = underTest.getDocumentById(1);
        assertEquals(expectedDocument.getId(), actualDocument.getId());
        assertEquals(expectedDocument.getFileName(), actualDocument.getFileName());
        assertEquals(expectedDocument.getContentType(), actualDocument.getContentType());
        assertEquals(expectedDocument.getCreatedBy(), actualDocument.getCreatedBy());
        assertEquals(expectedDocument.getCreatedDtm(), actualDocument.getCreatedDtm());
        assertEquals(expectedDocument.getModifiedBy(), actualDocument.getModifiedBy());
        assertEquals(expectedDocument.getModifiedDtm(), actualDocument.getModifiedDtm());
    }

    @Test
    void testGetDocumentByIdNotFound() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> underTest.getDocumentById(anyInt()));
    }


    @Test
    void testSaveDocument() {
        Document savedDocument = Helper.documentDummy();
        when(repository.saveAndFlush(any(Document.class))).thenReturn(savedDocument);
        Document actualDocument = underTest.saveDocument(savedDocument.getFileName(), savedDocument.getContentType(), savedDocument.getData());
        assertEquals(savedDocument.getFileName(), actualDocument.getFileName());
        assertEquals(savedDocument.getContentType(), actualDocument.getContentType());
        verify(repository, times(1)).saveAndFlush(any(Document.class));
    }

    @Test
    void testChangeFileNameWhenIdFound() {
        final String newFileName = "new_file_name.txt";

        Document document = Helper.documentDummy();
        when(repository.findById(anyInt())).thenReturn(Optional.of(document));
        document.setFileName(newFileName);
        when(repository.saveAndFlush(document)).thenReturn(document);
        Document actualDocument = underTest.changeFileName(anyInt(), newFileName);
        assertEquals(newFileName, actualDocument.getFileName());
    }

    @Test
    void testChangeFileNameWhenIdNotFound() {
        final String newFileName = "new_file_name.txt";
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> underTest.changeFileName(anyInt(), newFileName));
        verify(repository, times(1)).findById(anyInt());
        verify(repository, never()).saveAndFlush(any(Document.class));
    }

    @Test
    void testDeleteDocumentByIdWhenIdFound() {
        Document document = Helper.documentDummy();
        when(repository.findById(anyInt())).thenReturn(Optional.of(document));
        doNothing().when(repository).deleteById(1);
        underTest.deleteDocumentById(1);
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteDocumentByIdWhenIdNotFound() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> underTest.deleteDocumentById(1));
        verify(repository, times(1)).findById(1);
        verify(repository, never()).deleteById(1);
    }


}
