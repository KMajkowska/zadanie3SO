import java.util.*;
public class Generator {
	private int Ciag;
    private int PamiecLogiczna;
    private ArrayList<Strona> zlecenia;

    public Generator(int dlugoscCiagu,int wielkoscPamieciLogicznej)
    {
        this.Ciag=dlugoscCiagu;
        this.PamiecLogiczna = wielkoscPamieciLogicznej;
        zlecenia = new ArrayList<>(dlugoscCiagu);
        generuj();
    }


    public void generuj()
    {
        Random rand = new Random();
        int adres=-1;
        int Skok;
        for(int i=0;i<Ciag;i++)
        {
        	
            	Skok = rand.nextInt(2);
                if(i==0)
                {
                	adres =rand.nextInt(PamiecLogiczna);
                }
                else if(zlecenia.get(i-1).getId()<PamiecLogiczna-1 && Skok==0)
                {
                	adres = zlecenia.get(i-1).getId() +1;
                }
                else if(zlecenia.get(i-1).getId()>0 && Skok==1 )
                {
                	adres = zlecenia.get(i-1).getId() -1;
                }
                else if(zlecenia.get(i-1).getId()==0)
                {
                	adres = zlecenia.get(i-1).getId() +1;

                }
                else if(zlecenia.get(i-1).getId()==PamiecLogiczna-1)
                {
                	adres = zlecenia.get(i-1).getId() -1;

                }
                zlecenia.add(new Strona(adres));
            }

    }

    public Iterator<Strona> iterator()
    {
        return zlecenia.iterator();
    }

    public Iterator<Strona> iteratorMoment(int moment)
    {

        Iterator<Strona> iterator = zlecenia.iterator();
        int index=0;
        while(index<moment && iterator.hasNext())
        {
            iterator.next();
            index++;
        }
        //System.out.println("Iterator: "+i+" "+iterator.hasNext());
        return iterator;
    }

    public void wyswietlOdMomentu(int moment)
    {
        Iterator<Strona> iterator = iteratorMoment(moment);

        while(iterator.hasNext())
        {
            System.out.print(iterator.next().getId()+", ");
        }
        System.out.println();

    }

    public void Wyswietl() {
        for (int i = 0; i < zlecenia.size(); i++)
        {
            System.out.println(zlecenia.get(i));
        }
    }
}
