import java.util.*;  
public class Main {

	 public static void main(String[] args) {
			Scanner scan = new Scanner(System.in);
			int Ciag, PamiecLogiczna, PamiecFizyczna;
			System.out.println("Podaj liczbê stron, ktorych beda odwolania: ");
			Ciag = scan.nextInt();
			System.out.println("Podaj wielkosc pamieci logicznej(strony): ");
			PamiecLogiczna = scan.nextInt();
			System.out.println("Podaj wielkosc pamieci fizycznej(ramki): ");
			PamiecFizyczna = scan.nextInt();
			Generator generator = new Generator(Ciag,PamiecLogiczna);
			Algorytmy system = new Algorytmy(PamiecFizyczna, PamiecLogiczna,generator);
			try {
			     System.out.println("FIFO: " + system.FIFO() );
			     System.out.println("OPTYMALNY: " + system.OPTYMALNY() );
			     System.out.println("LRU: " + system.LRU() );
			     System.out.println("ALRU: " + system.ALRU());
			     System.out.println("RAND: " + system.RAND() );
			}catch(PustaRamkaException e)
			{
			     System.out.println("Usuwanie strony z pustej ramki");
			}catch(ZajetaRamkaException e)
			{
				System.out.println("Próba zapisania do zajêtej ramki");
			}
		   	 scan.close();
	 }
}
