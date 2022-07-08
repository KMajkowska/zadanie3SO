
public class Strona {
	private boolean czyZaladowana;
    private int id;
    private Ramka adresRamki;
    private int momentZaladowania;
    private int momentOstatniegoOdwolania;

    private int bit;

    public Strona(int id)
    {
        this.id=id;
        czyZaladowana=false;
        momentZaladowania=-1;
        bit=0;
    }
    public void zaladujStrone(Ramka ramka, int moment) throws ZajetaRamkaException
    {
        if(ramka.isCzyWolna()) {
            ramka.zaladujStrone(this);
            adresRamki=ramka;
            momentZaladowania=moment;
            momentOstatniegoOdwolania=moment;
            bit=1;
            czyZaladowana=true;

        }
        else
        {
            throw new ZajetaRamkaException();
        }

    }
    public void usunZPamieci() throws PustaRamkaException
    {
        if(czyZaladowana)
        {
            adresRamki.zdejmijStrone();
            adresRamki=null;
            czyZaladowana=false;
            bit=0;
            momentZaladowania=-1;
        }
        else
        {
            throw new PustaRamkaException();
        }

    }

    public boolean isCzyZaladowana() {
        return czyZaladowana;
    }

    public void setCzyZaladowana(boolean czyZaladowana) {
        this.czyZaladowana = czyZaladowana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ramka getAdresRamki() {
        return adresRamki;
    }

    public void setAdresRamki(Ramka adresRamki) {
        this.adresRamki = adresRamki;
    }

    public int getMomentZaladowania() {
        return momentZaladowania;
    }

    public void setMomentZaladowania(int momentZaladowania) {
        this.momentZaladowania = momentZaladowania;
    }

    public String toString()
    {
        return String.format("%15d | %15d | %15d |%15d", id, adresRamki.getId(), momentZaladowania, momentOstatniegoOdwolania);
    }

    public int getMomentOstatniegoOdwolania() {
        return momentOstatniegoOdwolania;
    }

    public void setMomentOstatniegoOdwolania(int momentOstatniegoOdwolania) {
        this.momentOstatniegoOdwolania = momentOstatniegoOdwolania;
    }

    public int getBit() {
        return bit;
    }

    public void setBit(int bit) {
        this.bit = bit;
    }

}
