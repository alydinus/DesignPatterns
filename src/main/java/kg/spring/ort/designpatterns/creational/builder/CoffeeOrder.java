package kg.spring.ort.designpatterns.creational.builder;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoffeeOrder {

    private final String drinkType;
    private final String size;
    private final String milkType;
    private final int espressoShots;
    private final boolean whippedCream;
    private final String syrupFlavor;
    private final boolean iced;

    private CoffeeOrder(Builder builder) {
        this.drinkType    = builder.drinkType;
        this.size         = builder.size;
        this.milkType     = builder.milkType;
        this.espressoShots = builder.espressoShots;
        this.whippedCream = builder.whippedCream;
        this.syrupFlavor  = builder.syrupFlavor;
        this.iced         = builder.iced;
    }

    public static class Builder {
        private final String drinkType;
        private String size         = "MEDIUM";
        private String milkType     = "WHOLE";
        private int    espressoShots = 1;
        private boolean whippedCream = false;
        private String syrupFlavor  = "NONE";
        private boolean iced        = false;

        public Builder(String drinkType) {
            this.drinkType = drinkType;
        }

        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder milkType(String milkType) {
            this.milkType = milkType;
            return this;
        }

        public Builder espressoShots(int shots) {
            this.espressoShots = shots;
            return this;
        }

        public Builder withWhip() {
            this.whippedCream = true;
            return this;
        }

        public Builder syrupFlavor(String flavor) {
            this.syrupFlavor = flavor;
            return this;
        }

        public Builder iced() {
            this.iced = true;
            return this;
        }

        public CoffeeOrder build() {
            return new CoffeeOrder(this);
        }
    }
}
