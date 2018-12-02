package rest;


import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.io.IOException;

@RestController
public class Controller {

    @RequestMapping(value = "/textFile/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    HttpEntity<byte[]> getConvertedScreenshot(@PathVariable("fileName") String fileName) throws IOException {
        String resoultText = Matcher.matcher(fileName);
        byte[] document = resoultText.getBytes();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "txt"));
        header.set("Content-Disposition", "inline; filename=result.txt");
        header.setContentLength(document.length);
        return new HttpEntity<>(document, header);
    }
}
