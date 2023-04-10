package com.example.POC.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 * 
 * @author Nehal Srivastava
 * 
 */

@Configuration
public class TemplateEngineConfig {

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();

    /*
     * By default currently set to create/access only Type:String Template. Can be tweak for
     * html/js/txt etc
     */
    templateEngine.addTemplateResolver(stringTemplateResolver());

    return templateEngine;
  }

  private ITemplateResolver htmlTemplateResolver() {
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("/templates/");
    resolver.setSuffix(".html");
    resolver.setOrder(1);
    resolver.setCacheable(true);
    return resolver;
  }

  // private ITemplateResolver textTemplateResolver() {
  // ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
  // resolver.setResolvablePatterns(Collections.singleton("text/*"));
  // resolver.setTemplateMode("text");
  // resolver.setOrder(3);
  // resolver.setCharacterEncoding("UTF-8");
  // resolver.setCacheable(false);
  // return resolver;
  // }

  private ITemplateResolver stringTemplateResolver() {

    StringTemplateResolver resolver = new StringTemplateResolver();
    resolver.setTemplateMode("TEXT");
    resolver.setCacheable(false);
    return (resolver);

  }

}
