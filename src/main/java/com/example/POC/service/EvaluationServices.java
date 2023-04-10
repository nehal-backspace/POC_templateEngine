package com.example.POC.service;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.jtwig.environment.DefaultEnvironmentConfiguration;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentFactory;
import org.jtwig.resource.reference.ResourceReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import com.example.POC.thymeleaf.TemplateEngineConfig;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.StringLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

@Service
public class EvaluationServices {

  @Autowired
  DataProvider dataProvider;

  @PostConstruct
  public Configuration freemarkerOneTimeConfig() {
    Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
    DefaultObjectWrapper wrapper = new DefaultObjectWrapper(Configuration.VERSION_2_3_29);
    // without setting true freemarker can not access public variable or methods of a class
    wrapper.setExposeFields(true);
    /*
     * NOTE : Freemarker does Not read or access Public variables/methods of a Private class and
     * Private variables/methods of a public class
     */
    cfg.setObjectWrapper(wrapper);
    cfg.setDefaultEncoding("UTF-8");
    // Sets how errors will appear.
    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
    cfg.setWrapUncheckedExceptions(true);
    // Do not fall back to higher scopes when reading a null loop variable:
    cfg.setFallbackOnNullLoopVariable(false);
    return cfg;
  }

  @PostConstruct
  public Environment jtwigOneTimeConfigs() {
    EnvironmentConfiguration configuration = new DefaultEnvironmentConfiguration();
    EnvironmentFactory environmentFactory = new EnvironmentFactory();
    return environmentFactory.create(configuration);
  }

  @PostConstruct
  public PebbleEngine pebbleOneTimeConfigs() {
    return new PebbleEngine.Builder().autoEscaping(false).loader(new StringLoader()).build();
  }

  public String evaluateThymeleaf() {
    String data = dataProvider.getThymeleafTemplate();

    Context myContext = new Context();

    myContext.setVariable("title", "This is title");
    myContext.setVariable("bg_color", "#FF00FF");
    myContext.setVariable("adType", "this is adType");
    myContext.setVariable("ad", dataProvider.getAdArrayForThymeleaf());
    myContext.setVariable("recruiter_company_service_group", dataProvider.getRecruiterArray());
    myContext.setVariable("recruiter_company_service_branding", dataProvider.getRecruiterArray());

    TemplateEngineConfig obj = new TemplateEngineConfig();
    SpringTemplateEngine templateEngine = obj.templateEngine();

    return templateEngine.process(data, myContext);
  }

  public String evaluateJtwig() {
    String data = dataProvider.getJtwigTemplate();

    // Resource
    ResourceReference resource = new ResourceReference(ResourceReference.STRING, data);

    // Template
    JtwigTemplate jtwigTemplate = new JtwigTemplate(jtwigOneTimeConfigs(), resource);

    // Model
    JtwigModel model =
        JtwigModel.newModel().with("bg_color", "#F0F0F0").with("adType", "this is adType")
            .with("recruiter_company_service_group", dataProvider.getRecruiterArray())
            .with("recruiter_company_service_branding", dataProvider.getRecruiterArray())
            .with("adCollection", dataProvider.getAdArrayForJtwig());

    // Output
    return jtwigTemplate.render(model);
  }


  public String evaluateFreemarker() {

    String data = dataProvider.getFreemrkerTemplate();
    Writer out = new StringWriter();

    try {
      Template t = new Template("templateName", new StringReader(data), freemarkerOneTimeConfig());

      Map<String, Object> root = new HashMap<>();

      root.put("bg_color", "F0ee00");
      root.put("adCollection", dataProvider.getAdArrayForJtwig());
      root.put("adType", "this is adType");
      root.put("recruiter_company_service_group", dataProvider.getRecruiterArray());
      root.put("recruiter_company_service_branding", dataProvider.getRecruiterArray());

      try {
        t.process(root, out);
      } catch (TemplateException | IOException e) {
        e.printStackTrace();
        throw (new RuntimeException());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw (new RuntimeException());
    }

    return out.toString();
  }

  public String evaluatePebble() {
    String data = dataProvider.getPebbleTemplate();

    Map<String, Object> root = new HashMap<>();
    root.put("bg_color", "F0ee00");
    root.put("adCollection", dataProvider.getAdArrayForJtwig());
    root.put("adType", "this is adType");
    root.put("recruiter_company_service_group", dataProvider.getRecruiterArray());
    root.put("recruiter_company_service_branding", dataProvider.getRecruiterArray());

    PebbleEngine engine = pebbleOneTimeConfigs();
    Writer writer = new StringWriter();

    try {
      engine.getTemplate(data).evaluate(writer, root);
    } catch (IOException e) {
      e.printStackTrace();
      throw (new RuntimeException());
    }
    return writer.toString();
  }
}


