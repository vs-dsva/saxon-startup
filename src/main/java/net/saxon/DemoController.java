package net.saxon;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import net.sf.saxon.s9api.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Controller("/demo")
@ExecuteOn(TaskExecutors.IO)
public class DemoController {
    private final DemoService ds;

    public DemoController(DemoService ds) {
        this.ds = ds;
    }

    @Get("/")
    public String doIt() throws SaxonApiException {
        return ds.doIt();
    }
}
