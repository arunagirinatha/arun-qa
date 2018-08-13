package nl.rabo.report.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import nl.rabo.report.service.ReportService;
import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

@Service
public class ReportServiceImpl implements ReportService {

	private static final Logger LOGGER = Logger.getLogger(ReportServiceImpl.class);

	// Getting Failed Reports
	@Override
	public Set<CustomerStatement> getFailedReports(CustomerStatements statements) {
		LOGGER.info("ReportServiveImpl getFailedReports Entry");
		Set<CustomerStatement> failedReportSet = new HashSet<CustomerStatement>();
		Set<CustomerStatement> reportSet = new HashSet<CustomerStatement>();
		if (statements != null) {
			if (statements.getRecords() != null && !statements.getRecords().isEmpty()) {
				for (CustomerStatement statement : statements.getRecords()) {
					// adding duplicate transaction reference in failed report
					if (!reportSet.add(statement)) {
						failedReportSet.add(statement);
					}
					// adding end balance which are in negative to the failed reports
					if (statement.getEndBalance() < 0) {
						failedReportSet.add(statement);
					}
				}
			}
		}
		LOGGER.info("ReportServiveImpl getFailedReports End");
		return failedReportSet;
	}

}
