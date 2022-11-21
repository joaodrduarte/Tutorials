package tutorials.greenlearner.FileOperations.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;
import tutorials.greenlearner.FileOperations.dto.FileDocument;

@Repository
public interface DocFileDao extends CrudRepository<FileDocument, Long> {
    FileDocument findByFileName(String filename);
}
