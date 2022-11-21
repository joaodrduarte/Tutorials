package tutorials.greenlearner.FileOperations.dto;

import javax.persistence.*;

@Entity
@Table(name = "file_document")
public class FileDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "filename")
    private String fileName;

    @Column(name = "docfile")
    @Lob
    private byte[] docFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getDocFile() {
        return docFile;
    }

    public void setDocFile(byte[] docFile) {
        this.docFile = docFile;
    }
}
