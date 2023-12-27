package by.teachmeskills.lesson37.file;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Data
@AllArgsConstructor
@Scope("prototype")

public class SimpleFile {
    private String fileName;
    private String path;
    private byte[] content;
    private String extension;
}
