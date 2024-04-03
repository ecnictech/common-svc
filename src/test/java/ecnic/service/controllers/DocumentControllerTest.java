package ecnic.service.controllers;

import ecnic.service.Helper;
import ecnic.service.models.ListPaging;
import ecnic.service.models.entities.Document;
import ecnic.service.services.DocumentSvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tags(value = {
        @Tag(value = "services"),
        @Tag(value = "controller"),
        @Tag(value = "integration")
})
public class DocumentControllerTest {
    @Mock
    private DocumentSvc service;
    @InjectMocks
    private DocumentController underTest;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
    }

    @Test
    void testGetDocuments() throws Exception {
        ListPaging<Document> expectedListPaging = Helper.listDocumentDummy();
        when(service.getDocuments(anyInt(), anyInt())).thenReturn(expectedListPaging);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/documents")
                        .param("page", "1")
                        .param("size", "10")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(expectedListPaging.getData().size())))
                .andExpect(jsonPath("$.data[0].id").value(expectedListPaging.getData().getFirst().getId()))
                .andExpect(jsonPath("$.totalData").value(expectedListPaging.getTotalData()))
                .andExpect(jsonPath("$.totalPage").value(expectedListPaging.getTotalPage()));
    }

    @Test
    void testGetDocumentById() throws Exception {
        Document expectedDocument = Helper.documentDummy();
        when(service.getDocumentById(anyInt())).thenReturn(expectedDocument);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/document/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDocument.getId()))
                .andExpect(jsonPath("$.fileName").value(expectedDocument.getFileName()))
                .andExpect(jsonPath("$.contentType").value(expectedDocument.getContentType()))
                .andExpect(jsonPath("$.createdBy").value(expectedDocument.getCreatedBy()));
    }

    @Test
    void testUploadDocument() throws Exception {
        Document expectedDocument = Helper.documentDummy();
        byte[] fileContent = expectedDocument.getData();
        // Create mock multipart file
        MockMultipartFile file = new MockMultipartFile(expectedDocument.getFileName(),
                expectedDocument.getFileName(),
                expectedDocument.getContentType(),
                fileContent);

        // Stub the service method to return the expected document
        when(service.saveDocument(expectedDocument.getFileName(),
                expectedDocument.getContentType(),
                fileContent)).thenReturn(expectedDocument);
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/document")
                        .file(new MockMultipartFile("data", expectedDocument.getFileName(), "text/plain", file.getBytes()))
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedDocument.getId()));
    }

    @Test
    void testChangeFileName() throws Exception {
        String newFileName = "new_file_name.txt";
        Document expectedDocument = Helper.documentDummy();
        expectedDocument.setFileName(newFileName);
        when(service.changeFileName(expectedDocument.getId(), newFileName)).thenReturn(expectedDocument);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/document/{id}/{newFileName}", expectedDocument.getId(), newFileName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileName").value(expectedDocument.getFileName()));
    }

    @Test
    void testDeleteDocument() throws Exception {
        Document expectedDocument = Helper.documentDummy();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/document/{id}", expectedDocument.getId()))
                .andExpect(status().isOk());

        verify(service).deleteDocumentById(expectedDocument.getId());
    }

}
