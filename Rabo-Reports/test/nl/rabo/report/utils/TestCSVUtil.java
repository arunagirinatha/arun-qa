package nl.rabo.report.utils;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import nl.rabo.report.exception.CSVException;
import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

public class TestCSVUtil {
	
	private static final Logger LOGGER = Logger.getLogger(TestCSVUtil.class);
	private CSVUtil csvUtil = new CSVUtil();

	@Test
	public void testWriteCSVWithoutFolderName() {
		try {
			csvUtil.writeCSV(new HashSet<>(), null, null, null);
			LOGGER.debug("Test Case Failed!!!");
			fail();
		} catch (CSVException csvException) {
			assertEquals("Either file path or file name is null",csvException.getMessage());
		}
	}
	
	@Test
	public void testWriteCSVwithEmptyRecords() {
		try {
			csvUtil.writeCSV(new HashSet<>(), "F:/", "failedReports", new String[] {"Transaction Reference", "Descrption"});
		} catch (CSVException csvException) {
			LOGGER.error(csvException);
			fail();
		}
	}
	
	@Test
	public void testWriteCSV() {
		Set<CustomerStatement> records = new HashSet<>();
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
		try {
			csvUtil.writeCSV(records, "F:/", "failedReports", new String[] {"Transaction Reference", "Description"});
		} catch (CSVException csvException) {
			LOGGER.error(csvException);
			fail();
		}
		catch(Exception runTimeException) {
			LOGGER.error(runTimeException);
			fail();
		}
	}
	
	@Test
	public void testReadCSVWithEmptyFile() {
		try {
			assertNull(csvUtil.readCSV(null));
		} catch (CSVException csvException) {
			LOGGER.error(csvException);
			fail();
		}
	}
	
	@Test
	public void testReadCSV() {
		File dummyReport = new File("test/nl/rabo/report/dummyreports/records.csv");
		try {
			CustomerStatement firstRecord = new CustomerStatement();
			firstRecord.setTransacationReference(194261);
			firstRecord.setDescription("Clothes from Jan Bakker");
			firstRecord.setAccountNumber("NL91RABO0315273637");
			firstRecord.setStartBalance(21.6f);
			firstRecord.setEndBalance(-20.23f);
			firstRecord.setMutation(-41.83f);
			
			CustomerStatements expectedCsutomerStatements = new CustomerStatements();
			List<CustomerStatement> records = new ArrayList<>();
			
			records.add(firstRecord);
			expectedCsutomerStatements.setRecords(records);
			
			CustomerStatements actualCustomerSTatements = csvUtil.readCSV(dummyReport);
			assertEquals(expectedCsutomerStatements, actualCustomerSTatements);
			
		} catch (CSVException csvException) {
			LOGGER.error(csvException, csvException);
			fail();
		}
	}
}
