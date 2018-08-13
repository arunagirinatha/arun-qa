package nl.rabo.report.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="record")
@XmlAccessorType(XmlAccessType.FIELD)

public class CustomerStatement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="reference")
	private int transacationReference;
	@XmlElement(name="accountNumber")
	private String accountNumber;
	@XmlElement(name="startBalance")
	private float startBalance;
	@XmlElement(name="mutation")
	private float mutation;
	@XmlElement(name="description")
	private String description;
	@XmlElement(name="endBalance")
	private float endBalance;
	
	public int getTransacationReference() {
		return transacationReference;
	}
	public void setTransacationReference(int transacationReference) {
		this.transacationReference = transacationReference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public float getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(float startBalance) {
		this.startBalance = startBalance;
	}
	public float getMutation() {
		return mutation;
	}
	public void setMutation(float mutation) {
		this.mutation = mutation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(float endBalance) {
		this.endBalance = endBalance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + transacationReference;
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
		CustomerStatement other = (CustomerStatement) obj;
		if (transacationReference != other.transacationReference)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CustomerStatementVO [transacationReference=" + transacationReference + ", accountNumber="
				+ accountNumber + ", startBalance=" + startBalance + ", mutation=" + mutation + ", description="
				+ description + ", endBalance=" + endBalance + "]";
	}
	
}
