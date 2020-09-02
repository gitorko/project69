package org.gitokro.project69;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
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
        ByteArrayInputStream bis = new ByteArrayInputStream(htmlReport.getBytes());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"myreport.htm\"")
                .body(new InputStreamResource(bis));
    }

    private String generateHtmlReport() throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
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
        Random random = new Random();
        return IntStream.range(0, 150)
                .mapToObj(i -> Employee.builder()
                        .name("Name " + i)
                        .age(random.nextInt(65 - 20) + 20)
                        .dob(new Date())
                        .salary(40000 + (100000 - 40000) * random.nextDouble())
                        .build())
                .collect(Collectors.toList());
    }
}
