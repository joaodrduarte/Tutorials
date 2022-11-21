package tutorials.greenlearner.FileOperations.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tutorials.greenlearner.FileOperations.dto.FileDocument;
import tutorials.greenlearner.FileOperations.dto.FileUploadResponse;
import tutorials.greenlearner.FileOperations.service.DocFileDao;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadDownloadWithDatabaseController {
    private DocFileDao docFileDao;

    public UploadDownloadWithDatabaseController(DocFileDao docFileDao) {
        this.docFileDao = docFileDao;
    }

    @PostMapping("/single/uploadDb")
    FileUploadResponse singleFileUploadDb(@RequestParam("file")MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        FileDocument fileDocument = new FileDocument();
        fileDocument.setFileName(name);
        fileDocument.setDocFile(file.getBytes());
        docFileDao.save(fileDocument);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(name).toUriString();
        String contentType = file.getContentType();
        FileUploadResponse response = new FileUploadResponse(name, contentType, url);
        return response;
    }

    @GetMapping("/downloadFromDB/{fileName}")
    ResponseEntity<byte[]> downloadSingleFileFromDB (@PathVariable("fileName") String filename, HttpServletRequest request){
        FileDocument doc = docFileDao.findByFileName(filename);
        String mimeType = request.getServletContext().getMimeType(doc.getFileName());
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline?fiop_" + doc.getFileName())
                .body(doc.getDocFile());
    }

}
