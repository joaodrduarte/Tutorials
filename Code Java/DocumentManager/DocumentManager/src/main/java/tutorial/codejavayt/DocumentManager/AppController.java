package tutorial.codejavayt.DocumentManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

   // public String welcomeMsgKey ="Does this work??";

    @Autowired
    private DocumentRepository repo;

    @GetMapping("/")
    public String viewHomepage(Model model){
        List<Document> listDocs = repo.findAll();
        long sum = 0;
        for(int i = 0; i < listDocs.size();i++){

            sum+= listDocs.get(i).getSize();
        }
        model.addAttribute("listDocs", listDocs);
        model.addAttribute("sizeSum",sum);
        return "home";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("document")MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setName(fileName);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUploadTime(new Date());
        repo.save(document);
        ra.addFlashAttribute("message","The file has been uploaded successfully!");
        return "redirect:/";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id") Long id, HttpServletResponse response) throws Exception {
        Optional<Document> result = repo.findById(id);
        if(!result.isPresent()){
            throw new Exception("Could not find document with the ID " + id);
        }
        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=" + document.getName();
        response.setHeader(headerKey,headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(document.getContent());
        outputStream.close();
    }


}
