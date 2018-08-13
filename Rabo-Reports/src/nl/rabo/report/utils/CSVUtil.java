package nl.rabo.report.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import nl.rabo.report.exception.CSVException;
import nl.rabo.report.vo.CustomerStatement;
import nl.rabo.report.vo.CustomerStatements;

public class CSVUtil {
	private static final Logger LOGGER = Logger.getLogger(CSVUtil.class);

	public void writeCSV(Set<CustomerStatement> customerStatementVOs, String failedReportFilePath, String fileName,
			String[] csvHeaders) throws CSVException {
		LOGGER.info("CSVUtil writeCSV entry");
		if (failedReportFilePath == null || fileName == null) {
			throw new CSVException("Either file path or file name is null");
		}
		try {
			File reportFolder = new File(failedReportFilePath);
			String dateFormat = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
			File generatedReport = new File(reportFolder, fileName + dateFormat + ".csv");
			FileWriter writer = new FileWriter(generatedReport);
			// using custom delimiter and quote character
			CSVWriter csvWriter = new CSVWriter(writer, ',');
			csvWriter.writeAll(toStringArray(customerStatementVOs, csvHeaders));
			csvWriter.close();
		} catch (IOException csvWriteException) {
			LOGGER.error(csvWriteException);
			throw new CSVException("Unable to Generate Failed Reports", csvWriteException);
		} catch(Exception runTimeException) {
			LOGGER.error(runTimeException);
			throw new CSVException("Failed to generate failed reports", runTimeException);
		}
		LOGGER.info("CSVUtil writeCSV End");
	}

	private static List<String[]> toStringArray(Set<CustomerStatement> failedStatements, String[] csvHeaders) {
		List<String[]> records = new ArrayList<String[]>();

		// adding header record
		records.add(csvHeaders);
		if (failedStatements != null && !failedStatements.isEmpty()) {

			Iterator<CustomerStatement> it = failedStatements.iterator();
			while (it.hasNext()) {
				CustomerStatement emp = it.next();
				records.add(new String[] { String.valueOf(emp.getTransacationReference()), emp.getDescription() });
			}
		}
		return records;
	}

	public CustomerStatements readCSV(File csvFile) throws CSVException {
		LOGGER.info("CSVUtil readCSV method entry");
		CSVReader csvReader;
		CustomerStatements customerStatementsVO = null;
		if(csvFile != null) {
			try {
				csvReader = new CSVReader(new FileReader(csvFile), ',');
				List<CustomerStatement> statements = new ArrayList<CustomerStatement>();
				String[] customerStatements = null;
				
				//read the header
				csvReader.readNext();
				// read line by line
				while ((customerStatements = csvReader.readNext()) != null) {
					CustomerStatement customerStatementVO = new CustomerStatement();
					customerStatementVO.setTransacationReference(Integer.valueOf(customerStatements[0]));
					customerStatementVO.setAccountNumber(customerStatements[1]);
					customerStatementVO.setDescription(customerStatements[2]);
					customerStatementVO.setStartBalance(Float.valueOf(customerStatements[3]));
					customerStatementVO.setMutation(Float.valueOf(customerStatements[4]));
					customerStatementVO.setEndBalance(Float.valueOf(customerStatements[5]));
					statements.add(customerStatementVO);
				}
				csvReader.close();
				customerStatementsVO = new CustomerStatements();
				customerStatementsVO.setRecords(statements);
			} catch (IOException csvReadException) {
				LOGGER.error(csvReadException);
				throw new CSVException("Error while Read CSV", csvReadException);
			} catch (Exception runTimeException) {
				LOGGER.error(runTimeException.getMessage(), runTimeException);
				throw new CSVException("Unable to Read CSV", runTimeException);
			}
		}
		LOGGER.info("CSVUtil readCSV method end");
		return customerStatementsVO;
	}
}
