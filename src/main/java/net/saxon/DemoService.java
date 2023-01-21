package net.saxon;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import jakarta.inject.Singleton;
import net.sf.saxon.s9api.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Singleton
public class DemoService {
    private final ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();

    private final Processor processor = new Processor();
    private final XsltCompiler compiler = processor.newXsltCompiler();
    private final Source streamSource;
    private final XsltExecutable executable;

    public DemoService() throws SaxonApiException {
        InputStream is = loader.getResourceAsStream("classpath:net/saxon/stylesheet.xsl").get();
        streamSource = new StreamSource(is);
        executable = compiler.compile(streamSource);
    }

    public String doIt() throws SaxonApiException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream(4 * 1024 * 1024);
        final Serializer out = processor.newSerializer(os);
        out.setOutputProperty(Serializer.Property.METHOD, "html");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        Xslt30Transformer transformer = executable.load30();
        InputStream is = loader.getResourceAsStream("classpath:net/saxon/input.xml").get();
        transformer.transform(new StreamSource(is), out);
        return out.toString();
    }
}
