package softuni.exam.util;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface XmlParser {

    <T> T unmarchalFromFile(String filePath, Class<T>tClass) throws JAXBException, FileNotFoundException;

    public <T> void marshalToFile(String filePath, T rootDto) throws JAXBException;
}
