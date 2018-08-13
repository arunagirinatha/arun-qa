package nl.rabo.report.service;

import java.util.Set;

import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

public interface ReportService {
	
	public Set<CustomerStatement> getFailedReports(CustomerStatements statements);
	
}
