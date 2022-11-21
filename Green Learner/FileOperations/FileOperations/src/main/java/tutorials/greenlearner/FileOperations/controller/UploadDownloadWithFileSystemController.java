package tutorials.greenlearner.FileOperations.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tutorials.greenlearner.FileOperations.dto.FileUploadResponse;
import tutorials.greenlearner.FileOperations.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class UploadDownloadWithFileSystemController {

    private FileStorageService fileStorageService;

    public UploadDownloadWithFileSystemController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    /**
     * Single file Upload
     * @param file
     * @return
     */
    @PostMapping("/single/upload")
    FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file){
        String fileName = fileStorageService.storeFile(file);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download2/").path(fileName).toUriString();
        String contentType = file.getContentType();
        FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
        return response;
    }

    /**
     * Download tied to a file type
     * @param fileName
     * @return
     */
    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downloadSingleFile(@PathVariable String fileName){
        Resource resource = fileStorageService.downloadFile(fileName);
        MediaType contentType = MediaType.IMAGE_PNG;
        return ResponseEntity
                .ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+resource.getFilename())
                .body(resource);
    }

    /**
     * Browser render tied to a file type
     * @param fileName
     * @return
     */
    @GetMapping("/render/{fileName}")
    ResponseEntity<Resource> renderFileOnBrowser(@PathVariable String fileName){
        Resource resource = fileStorageService.downloadFile(fileName);
        MediaType contentType = MediaType.IMAGE_PNG;
        return ResponseEntity
                .ok()
                .contentType(contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+resource.getFilename())
                .body(resource);
    }

    /**
     * Download of any kind of file
     * @param fileName
     * @return
     */
    @GetMapping("/download2/{fileName}")
    ResponseEntity<Resource> downloadSingleFile2(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileStorageService.downloadFile(fileName);
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+resource.getFilename())
                .body(resource);
    }

    /**
     * Download of any kind of file
     * @param fileName
     * @return
     */
    @GetMapping("/render2/{fileName}")
    ResponseEntity<Resource> renderFileOnBrowser2(@PathVariable String fileName, HttpServletRequest request){
        Resource resource = fileStorageService.downloadFile(fileName);
        String mimeType;
        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename="+resource.getFilename())
                .body(resource);
    }

    /**
     * Multiple files upload
     * @param files
     * @return
     */
    @PostMapping("/multiple/upload")
    List<FileUploadResponse> multipleUpload(@RequestParam("files") MultipartFile[] files){
        if(files.length > 7){
            throw new RuntimeException("Too many files ...");
        }
        List<FileUploadResponse> uploadResponseList = new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .forEach(file ->{
                    String fileName = fileStorageService.storeFile(file);
                    String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
                    String contentType = file.getContentType();
                    FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
                    uploadResponseList.add(response);
                });
        return uploadResponseList;
    }

    @GetMapping("/zipDownload")
    void zipDownload(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
        try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())){
            Arrays.asList(files).stream()
                    .forEach(file ->{
                        Resource resource = fileStorageService.downloadFile(file);
                        ZipEntry zipEntry = new ZipEntry(resource.getFilename());
                        try {
                            zipEntry.setSize(resource.contentLength());
                            zos.putNextEntry(zipEntry);
                            StreamUtils.copy(resource.getInputStream(), zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            System.out.println("Exception while Zipping !!!");
                        }
                    });
            zos.finish();
        }
    }
}
