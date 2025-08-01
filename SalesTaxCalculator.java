import java.util.*;

class SalesTaxCalculator {

    static class Item {
        int quantity;
        String name;
        double price;
        boolean isImported;
        boolean isExempt;
        double tax;
        double finalPrice;

        public Item(int quantity, String name, double price) {
            this.quantity = quantity;
            this.name = name;
            this.price = price;
            this.isImported = name.contains("imported");
            this.isExempt = isExemptItem(name);
            this.tax = calculateTax();
            this.finalPrice = price + tax;
        }

        private boolean isExemptItem(String name) {
            String[] exemptItems = { "book", "chocolate", "pill" };
            for (String exempt : exemptItems) {
                if (name.toLowerCase().contains(exempt)) return true;
            }
            return false;
        }

        private double calculateTax() {
            double taxRate = 0;
            if (!isExempt) taxRate += 0.10;
            if (isImported) taxRate += 0.05;
            return roundTax(price * taxRate);
        }

        private double roundTax(double tax) {
            return Math.ceil(tax * 20.0) / 20.0;
        }

        @Override
        public String toString() {
            return quantity + " " + name + ": " + String.format("%.2f", finalPrice);
        }
    }

    public static void main(String[] args) {
        System.out.println("Output 1:");
        processReceipt(new String[]{
            "1 book at 12.49",
            "1 music CD at 14.99",
            "1 chocolate bar at 0.85"
        });

        System.out.println("\nOutput 2:");
        processReceipt(new String[]{
            "1 imported box of chocolates at 10.00",
            "1 imported bottle of perfume at 47.50"
        });

        System.out.println("\nOutput 3:");
        processReceipt(new String[]{
            "1 imported bottle of perfume at 27.99",
            "1 bottle of perfume at 18.99",
            "1 packet of headache pills at 9.75",
            "1 box of imported chocolates at 11.25"
        });
    }

    public static void processReceipt(String[] inputs) {
        List<Item> items = new ArrayList<>();
        double totalTax = 0, total = 0;

        for (String line : inputs) {
            String[] parts = line.split(" at ");
            double price = Double.parseDouble(parts[1]);
            String[] quantityAndName = parts[0].split(" ", 2);
            int quantity = Integer.parseInt(quantityAndName[0]);
            String name = quantityAndName[1];

            Item item = new Item(quantity, name, price);
            items.add(item);
            totalTax += item.tax;
            total += item.finalPrice;
        }

        for (Item item : items) {
            System.out.println(item);
        }

        System.out.printf("Sales Taxes: %.2f\n", totalTax);
        System.out.printf("Total: %.2f\n", total);
    }
}
