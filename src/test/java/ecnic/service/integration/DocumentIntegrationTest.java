package ecnic.service.integration;

import ecnic.service.Helper;
import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@Tags(value = {
        @Tag(value = "services"),
        @Tag(value = "controller"),
        @Tag(value = "integration")
})
public class DocumentIntegrationTest {

    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        this.restTemplate = new TestRestTemplate();
    }

    @Test
    void testEndpointDocuments() throws URISyntaxException {
        log.info("INTEGRATION TEST DOCUMENT START");
        String baseUrl = "http://localhost:9091/api/v1";
        log.info("BASE URL: " + baseUrl);

        log.info("1. Upload 2 document");
        for (int i = 0; i < 2; i++) {
            Document document1 = Helper.documentDummy();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("data", new ByteArrayResource(document1.getData()) {
                @Override
                public String getFilename() {
                    return document1.getFileName();
                }
            });
            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<Document> uploadResponse = restTemplate.exchange(
                    baseUrl + "/document",
                    HttpMethod.POST,
                    requestEntity,
                    Document.class);
            assertEquals(HttpStatus.CREATED, HttpStatus.valueOf(uploadResponse.getStatusCode().value()));
            assertNotNull(Objects.requireNonNull(uploadResponse.getBody()));
            Document docUploaded = uploadResponse.getBody();
            assertEquals(docUploaded.getContentType(), document1.getContentType());
            assertEquals(docUploaded.getFileName(), document1.getFileName());
        }

        log.info("2. Get All Document");
        ResponseEntity<ListPaging<Document>> rspDocuments = restTemplate.exchange(
                baseUrl + "/documents?page=0&size=10",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, rspDocuments.getStatusCode());
        assertNotNull(Objects.requireNonNull(rspDocuments.getBody()));
        ListPaging<Document> documents = rspDocuments.getBody();
        assertFalse(documents.getData().isEmpty());
        assertTrue(documents.getTotalPage() != 0);
        assertTrue(documents.getTotalData() != 0);

        log.info("3. Get Document By ID");
        ResponseEntity<Document> rspDocument = restTemplate.getForEntity(
                baseUrl + "/document/" + documents.getData().getFirst().getId(),
                Document.class);
        assertEquals(HttpStatus.OK, rspDocument.getStatusCode());
        assertNotNull(Objects.requireNonNull(rspDocument.getBody()));
        Document document = rspDocument.getBody();
        assertNotNull(document.getId());
        assertNotNull(document.getContentType());
        assertNotNull(document.getFileName());

        log.info("4. Change Name File");
        String newDocumentName = "New_Document_Name";
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity2 = new HttpEntity<>(null, headers2);

        ResponseEntity<Document> respChangeName = restTemplate.exchange(
                baseUrl + "/document/" + document.getId() + "/" + newDocumentName,
                HttpMethod.PUT,
                requestEntity2,
                Document.class);
        assertEquals(HttpStatus.OK, respChangeName.getStatusCode());
        Document updatedDocument = respChangeName.getBody();
        assertNotNull(Objects.requireNonNull(updatedDocument));
        assertEquals(newDocumentName, updatedDocument.getFileName());

        log.info("5. Delete Document");
        restTemplate.delete(new URI(baseUrl + "/document/" + document.getId()));

        log.info("6. Check document deleted");
        ResponseEntity<String> exchange = restTemplate.exchange(
                baseUrl + "/document/" + document.getId(), HttpMethod.GET,
                null, String.class);

        assertEquals(HttpStatus.NOT_FOUND, HttpStatus.valueOf(exchange.getStatusCode().value()));
        log.info("INTEGRATION TEST DOCUMENT FINISHED");
    }


}
