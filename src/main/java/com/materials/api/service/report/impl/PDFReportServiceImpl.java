package com.materials.api.service.report.impl;

    import com.materials.api.service.report.ReportService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.thymeleaf.TemplateEngine;
    import org.thymeleaf.context.Context;
    import org.xhtmlrenderer.pdf.ITextRenderer;

    import java.io.ByteArrayOutputStream;
    import java.util.Map;

    @Service
    @RequiredArgsConstructor
    public class PDFReportServiceImpl implements ReportService {

      private final TemplateEngine templateEngine;

      @Override
      public byte[] generateReport(String templateName, Map<String, Object> data) {
        try {
          // Render template
          Context context = new Context();
          context.setVariables(data);
          String htmlContent = templateEngine.process(templateName, context);

          // Convert HTML to PDF
          ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
          ITextRenderer renderer = new ITextRenderer();
          renderer.setDocumentFromString(htmlContent);
          renderer.layout();
          renderer.createPDF(outputStream);

          return outputStream.toByteArray();
        } catch (Exception e) {
          throw new RuntimeException("Error generating report PDF.", e);
        }
      }
    }