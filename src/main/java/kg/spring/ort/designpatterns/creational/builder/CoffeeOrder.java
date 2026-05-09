package kg.spring.ort.designpatterns.creational.builder;

public class CoffeeOrder {

    private final String drinkType;
    private final String size;
    private final String milkType;
    private final int    espressoShots;
    private final boolean whippedCream;
    private final String syrupFlavor;
    private final boolean iced;

    private CoffeeOrder(Builder builder) {
        this.drinkType     = builder.drinkType;
        this.size          = builder.size;
        this.milkType      = builder.milkType;
        this.espressoShots = builder.espressoShots;
        this.whippedCream  = builder.whippedCream;
        this.syrupFlavor   = builder.syrupFlavor;
        this.iced          = builder.iced;
    }

    public String  getDrinkType()     { return drinkType; }
    public String  getSize()          { return size; }
    public String  getMilkType()      { return milkType; }
    public int     getEspressoShots() { return espressoShots; }
    public boolean isWhippedCream()   { return whippedCream; }
    public String  getSyrupFlavor()   { return syrupFlavor; }
    public boolean isIced()           { return iced; }

    @Override
    public String toString() {
        return "CoffeeOrder{" +
               "drinkType='" + drinkType + '\'' +
               ", size='" + size + '\'' +
               ", milkType='" + milkType + '\'' +
               ", espressoShots=" + espressoShots +
               ", whippedCream=" + whippedCream +
               ", syrupFlavor='" + syrupFlavor + '\'' +
               ", iced=" + iced +
               '}';
    }

    // ── Builder ──────────────────────────────────────────────────────────────

    public static class Builder {
        private final String drinkType;
        private String  size          = "MEDIUM";
        private String  milkType      = "WHOLE";
        private int     espressoShots = 1;
        private boolean whippedCream  = false;
        private String  syrupFlavor   = "NONE";
        private boolean iced          = false;

        public Builder(String drinkType) {
            this.drinkType = drinkType;
        }

        public Builder size(String size)           { this.size = size;               return this; }
        public Builder milkType(String milkType)   { this.milkType = milkType;       return this; }
        public Builder espressoShots(int shots)    { this.espressoShots = shots;     return this; }
        public Builder withWhip()                  { this.whippedCream = true;       return this; }
        public Builder syrupFlavor(String flavor)  { this.syrupFlavor = flavor;      return this; }
        public Builder iced()                      { this.iced = true;               return this; }

        public CoffeeOrder build() {
            return new CoffeeOrder(this);
        }
    }
}
