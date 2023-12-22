package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Ship> ships = new ArrayList<>();

    public Player() {
    }

    //методы управления набором кораблей
    public List<Ship> getShips() {
        return ships;
    }

    public void addShip(Ship ship) {
        this.ships.add(ship);
    }

    public void removeShip(Ship ship) {
        this.ships.remove(ship);
    }

    //метод проверки наличия кораблей
    public boolean isOutOfShips() {
        return (this.getShips().size() == 0);
    }
}