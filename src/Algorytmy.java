import java.util.*;
public class Algorytmy {
    private ArrayList<Ramka>ramki;
    private ArrayList<Strona> strony;
    private Generator generator;
    private int rozmiarPamieciFizycznej;
    private int rozmiarPamieciLogicznej;
    private int moment;

    public Algorytmy(int rozmiarPamieciFizycznej, int rozmiarPamieciLogicznej, Generator generator)
    {
        ramki = new ArrayList<Ramka>();
        strony  = new ArrayList<Strona>();
        moment=0;
        this.generator = generator;
        this.rozmiarPamieciFizycznej = rozmiarPamieciFizycznej;
        this.rozmiarPamieciLogicznej = rozmiarPamieciLogicznej;
        Stronicowanie();
    }


    public int FIFO() throws ZajetaRamkaException, PustaRamkaException
    {
        reset();
        int BledyStrony=0;
        Iterator<Strona> it = generator.iterator();
        Strona strona;

        while(it.hasNext())
        {
        	//Wyswietl();
        	strona = strony.get(it.next().getId());
            if(strona.isCzyZaladowana())
            {
                moment++;
                continue;
            }

            if(!zaladujDoPamieci(strona))
            {
            	BledyStrony++;
                zwrocStroneNajdluzejBezOdwolaniaDoTegoMomentu().usunZPamieci();
                zaladujDoPamieci(strona);
            }
            moment++;
        }

        return BledyStrony;
    }

    

    public int OPTYMALNY() throws ZajetaRamkaException, PustaRamkaException
    {
        reset();
        int BledyStrony=0;
        Iterator<Strona> it = generator.iterator();
        Strona strona;

        while(it.hasNext())
        {
           // Wyswietl();
        	strona = strony.get(it.next().getId());

            if(strona.isCzyZaladowana())
            {
                moment++;
                continue;
            }

            if(!zaladujDoPamieci(strona))
            {
            	BledyStrony++;
                zwrocStroneNajdluzejBezPrzyszlychOdwolan().usunZPamieci();
                zaladujDoPamieci(strona);
            }
            moment++;
        }

        return BledyStrony;
    }

    public int LRU() throws ZajetaRamkaException, PustaRamkaException
    {
        reset();
        int BledyStrony=0;
        Iterator<Strona> it = generator.iterator();
        Strona strona;

        while(it.hasNext())
        {
           // Wyswietl();
        	strona = strony.get(it.next().getId());
           // System.out.println("Obecna strona: "+odwolanie.getId());

            if(strona.isCzyZaladowana())
            {
            	strona.setMomentOstatniegoOdwolania(moment);
                moment++;
                continue;
            }

            if(!zaladujDoPamieci(strona))
            {
            	BledyStrony++;
                zwrocStroneNajdluzejBezOdwolaniaDoTegoMomentu().usunZPamieci();
                zaladujDoPamieci(strona);
            }
            moment++;
        }

        return BledyStrony;
    }

    public int ALRU() throws ZajetaRamkaException, PustaRamkaException
    {
        reset();
        int BledyStrony=0;
        Iterator<Strona> it = generator.iterator();
        Strona strona;

        while(it.hasNext())
        {
            //Wyswietl();
        	strona = strony.get(it.next().getId());

            if(strona.isCzyZaladowana())
            {
            	strona.setBit(1);
                moment++;
                continue;
            }

            if(!zaladujDoPamieci(strona))
            {
            	BledyStrony++;
            	zwrocStroneZBitemOdniesieniaRównymZero().usunZPamieci();
                WyzerujBityOdniesienia();
                zaladujDoPamieci(strona);
            }
            moment++;
        }

        return BledyStrony;
    }
    public int RAND() throws ZajetaRamkaException, PustaRamkaException
    {
        reset();
        int BledyStrony=0;
        Iterator<Strona> it = generator.iterator();
        Strona odwolanie;

        while(it.hasNext())
        {
            // Wyswietl();
            odwolanie = strony.get(it.next().getId());
            // System.out.println("Obecna strona: "+odwolanie.getId());

            if(odwolanie.isCzyZaladowana())
            {
                moment++;
                continue;
            }

            if(!zaladujDoPamieci(odwolanie))
            {
            	BledyStrony++;
                LosowaStrona().usunZPamieci();
                zaladujDoPamieci(odwolanie);
            }
            moment++;
        }

        return BledyStrony;



    }
    
    public void reset()
    {
        ramki = new ArrayList<Ramka>();
        strony  = new ArrayList<Strona>();
        moment=0;
        Stronicowanie();

    }

    public void Stronicowanie()
    {
        for(int i=0;i<rozmiarPamieciLogicznej;i++)
        {
            strony.add(new Strona(i));
        }

        for(int i=0;i<rozmiarPamieciFizycznej;i++)
        {
            ramki.add(new Ramka(i));
        }
    }

    public int pelneRamki()
    {
        int wynik=0;
        for(int i=0;i<strony.size();i++)
        {
            if(strony.get(i).isCzyZaladowana())
            {
                wynik++;
            }
        }

        return wynik;
    }

    public boolean zaladujDoPamieci(Strona strona) throws ZajetaRamkaException
    {
        int i=0;
        while(i<ramki.size() && !ramki.get(i).isCzyWolna())
        {
            i++;
        }

        if(i<ramki.size())
        {
            strona.zaladujStrone(ramki.get(i), moment);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void Wyswietl()
    {
        System.out.printf("%15s | %15s | %15s| %15s| Moment: %10d | Zajête ramki: %15d %n", "Adres strony", "Adres Ramki", "Moment Zaladowania", "Ostatnie Odw.", moment , pelneRamki());

        for(Ramka ramka: ramki)
        {
            if(!ramka.isCzyWolna())
            {
                System.out.println(ramka.getStrona());
            }
        }
    }
    
    public Strona NajdluzejWPamieci()
    {
        return zwrocStroneNajdluzejWPamieci(strony);

    }
    

    public Strona zwrocStroneNajdluzejBezOdwolaniaDoTegoMomentu()
    {
        int max=-1;
        Strona StronaNajdluzejBezOdwolan=strony.get(0);

        for(int i=0;i<strony.size();i++)
        {
        	if(moment - strony.get(i).getMomentOstatniegoOdwolania() > max && strony.get(i).isCzyZaladowana())
            {
                max = moment - strony.get(i).getMomentOstatniegoOdwolania();
                StronaNajdluzejBezOdwolan = strony.get(i);
            }
        }

        return StronaNajdluzejBezOdwolan;

    }
    public Strona zwrocStroneZBitemOdniesieniaRównymZero()
    {
        ArrayList<Strona>stronyZBitemZero = new ArrayList<>();
        for(int i=0;i<strony.size();i++)
        {
            if(strony.get(i).getBit()==0 && strony.get(i).isCzyZaladowana())
            {
            	stronyZBitemZero.add(strony.get(i));
            }
        }

        if(stronyZBitemZero.size()>1)
        {
            return zwrocStroneNajdluzejWPamieci(stronyZBitemZero);
        }
        else if(stronyZBitemZero.size()==0)
        {
            return NajdluzejWPamieci();
        }
        else
        {
            return stronyZBitemZero.get(0);
        }
    }
    public Strona LosowaStrona()
    {
        Random rand = new Random();

        int ramka = rand.nextInt(ramki.size());

        return ramki.get(ramka).getStrona();

    }
    
    public Strona zwrocStroneNajdluzejWPamieci(ArrayList<Strona> Strona)
    {
        int max=-1;
        Strona StronaNajdluzejWPamieci=Strona.get(0);

        for(int i=0;i<Strona.size();i++)
        {
            if( Strona.get(i).isCzyZaladowana() &&  moment - Strona.get(i).getMomentZaladowania() > max)
            {
                max = moment - Strona.get(i).getMomentZaladowania();
                StronaNajdluzejWPamieci = Strona.get(i);
            }
        }

        return StronaNajdluzejWPamieci;

    }
    public void WyzerujBityOdniesienia()
    {
        for(int i=0;i<strony.size();i++)
        {
            if(strony.get(i).isCzyZaladowana())
            {
                strony.get(i).setBit(0);
            }
        }
    }
    public void wyzerujMape(HashMap<Strona, Integer>mapa)
    {
        Set<Strona> keySet = mapa.keySet();
        Iterator<Strona> iterator = keySet.iterator();
        
        while(iterator.hasNext())
        {
            mapa.put(iterator.next(), 0);
        }

    }

    public void wyswietlMape(HashMap<Strona, Integer> mapa)
    {
        Set<Strona> keySet = mapa.keySet();
        Iterator<Strona> iterator = keySet.iterator();
        Strona strona;
        while(iterator.hasNext())
        {
            strona = iterator.next();
            System.out.println(strona+" - Wartoœæ w mapie: "+mapa.get(strona));
        }
    }

    public Strona zwrocStroneNajdluzejBezPrzyszlychOdwolan()
    {
        HashMap<Strona, Integer> mapa = new HashMap<>();
        Iterator<Strona> iterator = generator.iteratorMoment(moment);
        Strona strona=null;
        int maxMoment=0;
        Strona stronaMax=null;
        int pozycja=0;

        for(Ramka ramka: ramki)
        {
            if(!ramka.isCzyWolna())
            {
            	mapa.put(ramka.getStrona(), -1);
            }
        }
        while(iterator.hasNext())
        {
            strona = strony.get(iterator.next().getId());
            if(strona.isCzyZaladowana())
            {
                if(mapa.get(strona)==-1) {
                    mapa.put(strona, pozycja);
                }
            }
            pozycja++;
        }
        Set<Strona> keySet = mapa.keySet();
        iterator = keySet.iterator();

        while(iterator.hasNext())
        {
            strona = iterator.next();
            if((mapa.get(strona)>maxMoment && maxMoment!=-1) || mapa.get(strona)==-1 )
            {
                maxMoment = mapa.get(strona);
                stronaMax = strona;
            }
        }

        return stronaMax;
    }
}
