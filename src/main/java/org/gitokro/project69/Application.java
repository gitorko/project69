package org.gitokro.project69;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

@RestController
@Slf4j
class HomeController {

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> getReport() throws Exception {
        log.info("Generating report!");
        String htmlReport = this.generateHtmlReport();
        HttpHeaders headers = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename("myreport.htm", StandardCharsets.UTF_8)
                .build();
        headers.setContentDisposition(contentDisposition);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(htmlReport.getBytes())) {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_XHTML_XML)
                    .body(new InputStreamResource(bis));
        }
    }

    private String generateHtmlReport() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(this.getClass(), "/");
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("templates/my-report.ftl");
        List<Employee> employees = getEmployeeData();
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("reportTitle", "Company Employee Report");
        templateData.put("employees", employees);
        StringWriter out = new StringWriter();
        template.process(templateData, out);
        return out.toString();
    }

    private List<Employee> getEmployeeData() {
        //Sample Data
        return IntStream.range(0, 2)
                .mapToObj(i -> Employee.builder().name("Name " + i).age(i + 30).dob(new Date()).build())
                .collect(Collectors.toList());
    }
}

@Builder
@Data
class Employee {
    public String name;
    public Integer age;
    public Date dob;
}
