package ba.unsa.etf.si.tim12.bll.viewmodel;

import java.util.Date;

public class NoviTermin {
	private long pacijentId;
	private String doktor;
	private Date vrijeme;

	public long getPacijentId() {
		return pacijentId;
	}

	public void setPacijentId(long pacijentId) {
		this.pacijentId = pacijentId;
	}

	public String getDoktor() {
		return doktor;
	}

	public void setDoktor(String doktor) {
		this.doktor = doktor;
	}

	public Date getVrijeme() {
		return vrijeme;
	}

	public void setVrijeme(Date vrijeme) {
		this.vrijeme = new Date(vrijeme.getTime());
	}
}
