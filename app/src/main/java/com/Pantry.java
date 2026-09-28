package com;

public class Pantry {

        private int id;
        private String name;
        private int quantity;
        private String category;

        public Pantry(int id, String name, int quantity, String category) {
            this.id = id;
            this.name = name;
            this.quantity = quantity;
            this.category = category;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getCategory() {
            return category;
        }
    }
}
