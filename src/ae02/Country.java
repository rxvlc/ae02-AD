package ae02;

/**
 * Representa un país amb dades demogràfiques i geogràfiques.
 * Aquesta classe emmagatzema informació com el nom del país, 
 * la població, la densitat de població, la superfície, la taxa de fertilitat, 
 * l'edat mitjana, el tipus d'urbanització i el percentatge de població urbana.
 */
public class Country {

	// Atributs de la classe

	/** 
	 * Nom del país.
	 * Representa el nom oficial del país.
	 */
	private String country;

	/** 
	 * Població del país.
	 * Indica el nombre total d'habitants en el país.
	 */
	private int population;

	/** 
	 * Densitat de població.
	 * Mesura la quantitat d'habitants per cada quilòmetre quadrat (habitants/km²).
	 */
	private int density;

	/** 
	 * Superfície del país.
	 * Representa la mida total del país en quilòmetres quadrats (km²).
	 */
	private int area;

	/** 
	 * Taxa de fertilitat.
	 * Promig de fills per dona en el país.
	 */
	private double fertility;

	/** 
	 * Edat mitjana de la població.
	 * Indica l'edat mitjana de tots els habitants en el país.
	 */
	private int age;

	/** 
	 * Tipus d'urbanització.
	 * Descriu si la població es considera principalment urbana o rural.
	 */
	private String urban;

	/** 
	 * Percentatge de la població urbana.
	 * Indica el percentatge de persones que viuen en zones urbanes respecte al total de la població.
	 */
	private double share;


    /**
     * Constructor de la classe Country.
     * 
     * @param country El nom del país.
     * @param population La població del país.
     * @param density La densitat de població (habitants per km²).
     * @param area La superfície del país en km².
     * @param fertility La taxa de fertilitat del país.
     * @param age L'edat mitjana de la població del país.
     * @param urban Tipus d'urbanització (urbà o rural).
     * @param share Percentatge de la població urbana respecte a la població total.
     */
    public Country(String country, int population, int density, int area, double fertility, int age, String urban, double share) {
        this.country = country;    // Assigna el nom del país
        this.population = population; // Assigna la població
        this.density = density;     // Assigna la densitat
        this.area = area;           // Assigna la superfície
        this.fertility = fertility; // Assigna la taxa de fertilitat
        this.age = age;             // Assigna l'edat mitjana
        this.urban = urban;         // Assigna el tipus d'urbanització
        this.share = share;         // Assigna el percentatge de població urbana
    }

    /**
     * Obté el nom del país.
     * 
     * @return El nom del país.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Estableix el nom del país.
     * 
     * @param country El nom del país a establir.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Obté la població del país.
     * 
     * @return La població del país.
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Estableix la població del país.
     * 
     * @param population La població a establir.
     */
    public void setPopulation(int population) {
        this.population = population;
    }

    /**
     * Obté la densitat de població del país.
     * 
     * @return La densitat de població del país.
     */
    public int getDensity() {
        return density;
    }

    /**
     * Estableix la densitat de població del país.
     * 
     * @param density La densitat de població a establir.
     */
    public void setDensity(int density) {
        this.density = density;
    }

    /**
     * Obté la superfície del país.
     * 
     * @return La superfície del país en km².
     */
    public int getArea() {
        return area;
    }

    /**
     * Estableix la superfície del país.
     * 
     * @param area La superfície a establir en km².
     */
    public void setArea(int area) {
        this.area = area;
    }

    /**
     * Obté la taxa de fertilitat del país.
     * 
     * @return La taxa de fertilitat del país.
     */
    public double getFertility() {
        return fertility;
    }

    /**
     * Estableix la taxa de fertilitat del país.
     * 
     * @param fertility La taxa de fertilitat a establir.
     */
    public void setFertility(double fertility) {
        this.fertility = fertility;
    }

    /**
     * Obté l'edat mitjana de la població del país.
     * 
     * @return L'edat mitjana de la població.
     */
    public int getAge() {
        return age;
    }

    /**
     * Estableix l'edat mitjana de la població del país.
     * 
     * @param age L'edat mitjana a establir.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Obté el tipus d'urbanització del país.
     * 
     * @return El tipus d'urbanització (pot ser "urbà" o "rural").
     */
    public String getUrban() {
        return urban;
    }

    /**
     * Estableix el tipus d'urbanització del país.
     * 
     * @param urban El tipus d'urbanització a establir.
     */
    public void setUrban(String urban) {
        this.urban = urban;
    }

    /**
     * Obté el percentatge de població urbana del país.
     * 
     * @return El percentatge de població urbana respecte a la població total.
     */
    public double getShare() {
        return share;
    }

    /**
     * Estableix el percentatge de població urbana del país.
     * 
     * @param share El percentatge de població urbana a establir.
     */
    public void setShare(double share) {
        this.share = share;
    }

    /**
     * Mètode que retorna una representació en cadena de les dades del país.
     * 
     * @return Una cadena que conté la informació del país.
     */
    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +       // Mostra el nom del país
                ", population=" + population +       // Mostra la població
                ", density=" + density +             // Mostra la densitat
                ", area=" + area +                   // Mostra la superfície
                ", fertility=" + fertility +         // Mostra la taxa de fertilitat
                ", age=" + age +                     // Mostra l'edat mitjana
                ", urban='" + urban + '\'' +         // Mostra el tipus d'urbanització
                ", share=" + share + '}';            // Mostra el percentatge de població urbana
    }
}
