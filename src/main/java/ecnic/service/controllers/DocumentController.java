package ecnic.service.controllers;

import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;
import ecnic.service.services.DocumentSvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1")
public class DocumentController {

    private final DocumentSvc documentSvc;

    public DocumentController(DocumentSvc documentSvc) {
        this.documentSvc = documentSvc;
    }

    @GetMapping("/documents")
    public ResponseEntity<ListPaging<Document>> getDocuments(
            @RequestParam Integer page,
            @RequestParam Integer size) {
        ListPaging<Document> documents = documentSvc.getDocuments(page, size);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<Document> getDocument(@PathVariable Integer id) {
        Document documentById = documentSvc.getDocumentById(id);
        return new ResponseEntity<>(documentById, HttpStatus.OK);

    }

    @PutMapping("/document/{id}/{newFileName}")
    public ResponseEntity<Document> changeFileName(@PathVariable Integer id, @PathVariable String newFileName) {
        Document document = documentSvc.changeFileName(id, newFileName);
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    @PostMapping("/document")
    public ResponseEntity<Document> uploadDocument(@RequestPart("data") MultipartFile file) throws IOException {
        Document document = documentSvc.saveDocument(file.getOriginalFilename(), file.getContentType(), file.getBytes());
        return new ResponseEntity<>(document, HttpStatus.CREATED);
    }

    @DeleteMapping("/document/{id}")
    public void deleteDocument(@PathVariable Integer id) {
        documentSvc.deleteDocumentById(id);
    }

}
