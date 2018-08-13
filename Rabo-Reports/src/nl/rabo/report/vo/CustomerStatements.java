package nl.rabo.report.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="records")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerStatements implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XmlElement(name="record")
	private List<CustomerStatement> records;

	public List<CustomerStatement> getRecords() {
		return records;
	}

	public void setRecords(List<CustomerStatement> records) {
		this.records = records;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerStatements other = (CustomerStatements) obj;
		if (records == null) {
			if (other.records != null)
				return false;
		} else if (!records.equals(other.records))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerStatementsVO [records=" + records + "]";
	}
	
}
