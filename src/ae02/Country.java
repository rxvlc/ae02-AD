package ae02;

public class Country {

    private String country;
    private int population;
    private int density;
    private int area;
    private double fertility;
    private int age;
    private String urban;
    private double share;

    // Constructor
    public Country(String country, int population, int density, int area, double fertility, int age, String urban, double share) {
        this.country = country;
        this.population = population;
        this.density = density;
        this.area = area;
        this.fertility = fertility;
        this.age = age;
        this.urban = urban;
        this.share = share;
    }

    // Getters y Setters
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public double getFertility() {
        return fertility;
    }

    public void setFertility(double fertility) {
        this.fertility = fertility;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUrban() {
        return urban;
    }

    public void setUrban(String urban) {
        this.urban = urban;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }

    // Método toString para representar la información del país en forma de cadena
    @Override
    public String toString() {
        return "Country{" +
                "country='" + country + '\'' +
                ", population=" + population +
                ", density=" + density +
                ", area=" + area +
                ", fertility=" + fertility +
                ", age=" + age +
                ", urban='" + urban + '\'' +
                ", share=" + share +
                '}';
    }
}
