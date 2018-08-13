package nl.rabo.report.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.rabo.report.exception.CSVException;
import nl.rabo.report.service.ReportService;
import nl.rabo.report.utils.CSVUtil;
import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

@Controller
@RequestMapping("/reports")
public class ReportGenerationController {

	private static final Logger LOGGER = Logger.getLogger(ReportGenerationController.class); 
	
	@Autowired
	private ReportService reportService;
	@Autowired
	ServletContext servletContext;
	@Autowired
	private CSVUtil csvUtil;
	
	@Scheduled(cron="0 0 0 1 1/1 ")
	@RequestMapping(value="/failedStatementsReport",method=RequestMethod.GET)
	public String generateFailedReports() {
		LOGGER.info("ReportGenerationController generateFailedReports Entry");
		Set<CustomerStatement> failedReports = new HashSet<>();
		try {
			File reportsFolder = new File(new URI(this.getClass().getResource("/reports/").toString()));
			File[] reports = reportsFolder.listFiles();
			if(reports != null && reports.length > 0) {
				String extension = null;
				CustomerStatements customerStatementsVO = null;
				JAXBContext context = JAXBContext.newInstance(CustomerStatements.class);
				for(File report:reports) {
					int extensionIndex = report.getName().lastIndexOf('.');
					if (extensionIndex > 0) {
					    extension = report.getName().substring(extensionIndex+1);
					}
					if(extension.equalsIgnoreCase("xml")) {
						customerStatementsVO = (CustomerStatements) context.createUnmarshaller().unmarshal(report);
					} else if(extension.equalsIgnoreCase("csv")) {
						customerStatementsVO = csvUtil.readCSV(report);
					}
					Resource resource = new ClassPathResource("/rabo_reports.properties");
					Properties props = PropertiesLoaderUtils.loadProperties(resource);
					failedReports = reportService.getFailedReports(customerStatementsVO);
//					String failedReportFilePath = servletContext.getRealPath("/WEB-INF/");
//					failedReportFilePath = failedReportFilePath+"/resources";
					String failedReportFilePath = props.getProperty("rabo_reports.failedreports.location");
					csvUtil.writeCSV(failedReports, failedReportFilePath, "failedReports", new String[] { props.getProperty("rabo_reports.failedreports.transaction_reference.column.header"), props.getProperty("rabo_reports.failedreports.description.column.header") });
				}
			}
		} catch (JAXBException | URISyntaxException | IOException exception) {
			LOGGER.error("Exception Occured while Generating Failed Reports "+ exception.getMessage());
			LOGGER.error(exception);
		}
		LOGGER.info("ReportGenerationController generateFailedReports End");
		return "index";
		  
	}
}
  