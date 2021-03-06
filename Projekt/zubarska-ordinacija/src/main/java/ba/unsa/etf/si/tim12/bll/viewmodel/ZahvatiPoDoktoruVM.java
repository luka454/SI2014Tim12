package ba.unsa.etf.si.tim12.bll.viewmodel;

import java.util.ArrayList;
import java.util.Date;

public class ZahvatiPoDoktoruVM {
	private String doktor;
	private Date vrijemeOd, vrijemeDo;
	private double ukupnaCijena;
	private int ukupnoPosjeta;
	private ArrayList<ZahvatiPoDoktoruRowVM> zahvati;
	
	
	public ZahvatiPoDoktoruVM(long ukupnoPosjeta) {
		this.setUkupnoPosjeta((int) ukupnoPosjeta);
		zahvati = new ArrayList<ZahvatiPoDoktoruRowVM>();
	}


	public ZahvatiPoDoktoruVM(String doktor, Date vrijemeOd, Date vrijemeDo,
			double ukupnaCijena, int ukupnoPosjeta) {
		this.doktor = doktor;
		this.vrijemeOd = new Date(vrijemeOd.getTime());
		this.vrijemeDo = new Date(vrijemeDo.getTime());
		this.ukupnaCijena = ukupnaCijena;
		this.ukupnoPosjeta = ukupnoPosjeta;
		this.zahvati = new ArrayList<ZahvatiPoDoktoruRowVM>();
	}

	public ZahvatiPoDoktoruVM() {
		zahvati = new ArrayList<ZahvatiPoDoktoruRowVM>();
	}

	public String getDoktor() {
		return doktor;
	}

	public void setDoktor(String doktor) {
		this.doktor = doktor;
	}

	public Date getVrijemeOd() {
		return vrijemeOd;
	}

	public void setVrijemeOd(Date vrijemeOd) {
		this.vrijemeOd = new Date(vrijemeOd.getTime());
	}

	public Date getVrijemeDo() {
		return vrijemeDo;
	}

	public void setVrijemeDo(Date vrijemeDo) {
		this.vrijemeDo = new Date(vrijemeDo.getTime());
	}

	public double getUkupnaCijena() {
		return ukupnaCijena;
	}

	public void setUkupnaCijena(double ukupnaCijena) {
		this.ukupnaCijena = ukupnaCijena;
	}

	public int getUkupnoPosjeta() {
		return ukupnoPosjeta;
	}

	public void setUkupnoPosjeta(int ukupnoPosjeta) {
		this.ukupnoPosjeta = ukupnoPosjeta;
	}

	public ArrayList<ZahvatiPoDoktoruRowVM> getZahvati() {
		return zahvati;
	}

	public void setZahvati(ArrayList<ZahvatiPoDoktoruRowVM> zahvati) {
		this.zahvati = zahvati;
	}
}
