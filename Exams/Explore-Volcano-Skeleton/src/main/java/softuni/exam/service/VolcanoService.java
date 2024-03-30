package softuni.exam.service;

import java.io.IOException;

// TODO: Implement all methods
public interface VolcanoService {

    boolean areImported();

    String readVolcanoesFileContent() throws IOException;

    String importVolcanoes() throws IOException;



    String exportVolcanoes();
}
