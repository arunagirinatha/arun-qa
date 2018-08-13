package nl.rabo.report.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import nl.rabo.report.service.ReportService;
import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

public class TestReportServiceImpl {
	
	private ReportService reportService = new ReportServiceImpl();
	private CustomerStatements statements = new CustomerStatements();
	private List<CustomerStatement> records = new ArrayList<>();
	
	@Test
	public void testGetFailedReportsWithoutRecords() {
		statements.setRecords(null);
		assertEquals(new HashSet<>(), reportService.getFailedReports(statements));
	}
	
	@Test
	public void testGetFailedReportWithEmptyRecords() {
		statements.setRecords(records);
		assertEquals(new HashSet<>(), reportService.getFailedReports(statements));
	}
	
	@Test
	public void testGetFailedReportForDuplicateReference() {
		CustomerStatement firstTestRecord = new CustomerStatement();
		firstTestRecord.setTransacationReference(501);
		firstTestRecord.setAccountNumber("A2123");
		records.add(firstTestRecord);
		CustomerStatement secondTestRecord = new CustomerStatement();
		secondTestRecord.setTransacationReference(502);
		secondTestRecord.setAccountNumber("A2123");
		records.add(secondTestRecord);
		CustomerStatement thirdTestRecord = new CustomerStatement();
		thirdTestRecord.setTransacationReference(501);
		thirdTestRecord.setAccountNumber("A2126");
		records.add(thirdTestRecord);
		CustomerStatement fourthTestRecord = new CustomerStatement();
		fourthTestRecord.setTransacationReference(508);
		fourthTestRecord.setAccountNumber("A2126");
		records.add(fourthTestRecord);
		
		statements.setRecords(records);
		Set<CustomerStatement> expectedRecordSet = new HashSet<>();
		expectedRecordSet.add(firstTestRecord);
		expectedRecordSet.add(thirdTestRecord);
		assertEquals(expectedRecordSet, reportService.getFailedReports(statements));
	}
	
	@Test
	public void testGetFailedTEstReportForNegativeBalance() {
		CustomerStatement firstTestRecord = new CustomerStatement();
		firstTestRecord.setTransacationReference(501);
		firstTestRecord.setAccountNumber("A2123");
		firstTestRecord.setEndBalance(-60);
		records.add(firstTestRecord);
		CustomerStatement secondTestRecord = new CustomerStatement();
		secondTestRecord.setTransacationReference(502);
		secondTestRecord.setAccountNumber("A2123");
		secondTestRecord.setEndBalance(6230);
		records.add(secondTestRecord);
		CustomerStatement thirdTestRecord = new CustomerStatement();
		thirdTestRecord.setTransacationReference(504);
		thirdTestRecord.setAccountNumber("A2126");
		thirdTestRecord.setEndBalance(902);
		records.add(thirdTestRecord);
		CustomerStatement fourthTestRecord = new CustomerStatement();
		fourthTestRecord.setTransacationReference(508);
		fourthTestRecord.setAccountNumber("A2126");
		fourthTestRecord.setEndBalance(-22);
		records.add(fourthTestRecord);
		
		statements.setRecords(records);
		Set<CustomerStatement> expectedRecordSet = new HashSet<>();
		expectedRecordSet.add(firstTestRecord);
		expectedRecordSet.add(fourthTestRecord);
		assertEquals(expectedRecordSet, reportService.getFailedReports(statements));
	}
	
}
