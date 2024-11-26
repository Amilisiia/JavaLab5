import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Lab5 {
    // Базовий клас Gemstone
    public static abstract class Gemstone {
        private final String name;
        private final double weight; // у каратах
        private final double price;  // за карат
        private final double transparency; // від 0 до 100 (%)

        public Gemstone(String name, double weight, double price, double transparency) {
            if (weight <= 0 || price <= 0 || transparency < 0 || transparency > 100) {
                throw new IllegalArgumentException("Некоректні параметри для каменя!");
            }
            this.name = name;
            this.weight = weight;
            this.price = price;
            this.transparency = transparency;
        }

        public double getWeight() {
            return weight;
        }

        public double getTransparency() {
            return transparency;
        }

        public double calculateValue() {
            return weight * price;
        }

        @Override
        public String toString() {
            return String.format("Камінь: %s, Вага: %.2f карат, Ціна: %.2f, Прозорість: %.2f%%",
                    name, weight, price, transparency);
        }
    }

    // Клас-нащадок Diamond
    public static class Diamond extends Gemstone {
        public Diamond(double weight, double price, double transparency) {
            super("Diamond", weight, price, transparency);
        }
    }

    // Клас-нащадок Ruby
    public static class Ruby extends Gemstone {
        public Ruby(double weight, double price, double transparency) {
            super("Ruby", weight, price, transparency);
        }
    }

    // Клас-нащадок Amber
    public static class Amber extends Gemstone {
        public Amber(double weight, double price, double transparency) {
            super("Amber", weight, price, transparency);
        }
    }

    // Клас Necklace для роботи з намистом
    public static class Necklace {
        private final List<Gemstone> gemstones;

        public Necklace() {
            this.gemstones = new ArrayList<>();
        }

        public void addGemstone(Gemstone gemstone) {
            if (gemstone == null) {
                throw new IllegalArgumentException("Камінь не може бути null!");
            }
            gemstones.add(gemstone);
        }

        public double calculateTotalWeight() {
            return gemstones.stream().mapToDouble(Gemstone::getWeight).sum();
        }

        public double calculateTotalValue() {
            return gemstones.stream().mapToDouble(Gemstone::calculateValue).sum();
        }

        public void sortByValue() {
            gemstones.sort(Comparator.comparingDouble(Gemstone::calculateValue).reversed());
        }

        public List<Gemstone> findByTransparencyRange(double min, double max) {
            return gemstones.stream()
                    .filter(g -> g.getTransparency() >= min && g.getTransparency() <= max)
                    .collect(Collectors.toList());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("Намисто містить:\n");
            for (Gemstone gem : gemstones) {
                sb.append(gem.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    // Метод main для запуску програми
    public static void main(String[] args) {
        try {
            Necklace necklace = new Necklace();

            // Додавання каменів
            necklace.addGemstone(new Diamond(2.5, 5000, 95));
            necklace.addGemstone(new Ruby(3.0, 3000, 85));
            necklace.addGemstone(new Amber(5.0, 200, 70));

            System.out.println("Намисто до сортування:");
            System.out.println(necklace);

            // Розрахунок ваги та вартості
            System.out.printf("Загальна вага: %.2f карат\n", necklace.calculateTotalWeight());
            System.out.printf("Загальна вартість: %.2f\n", necklace.calculateTotalValue());

            // Сортування каменів за цінністю
            necklace.sortByValue();
            System.out.println("Намисто після сортування:");
            System.out.println(necklace);

            // Пошук каменів за прозорістю
            double minTransparency = 80;
            double maxTransparency = 100;
            System.out.printf("Камені з прозорістю від %.2f%% до %.2f%%:\n", minTransparency, maxTransparency);
            List<Gemstone> filtered = necklace.findByTransparencyRange(minTransparency, maxTransparency);
            filtered.forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}

