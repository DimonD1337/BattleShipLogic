package org.example;

import java.util.ArrayList;
import java.util.List;

public class Ship {
    private int size;
    private List<Integer> positions;
    private List<Boolean> cellStatuses; //true - не повреждена, false - повреждена

    public Ship(List<Integer> positions) {
        this.positions = positions;
        this.size = positions.size();
        List<Boolean> cellStatuses = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            cellStatuses.add(true);
        }
        this.cellStatuses = cellStatuses;
    }

    //метод получение позиций корабля

    public List<Integer> getPositions() {
        return positions;
    }

    //методы определения статуса ячейки и обозначения попадания
    public boolean isCellHit(int position) {
        if (this.positions.contains(position)) {
            int index = this.positions.indexOf(position);
            return !(this.cellStatuses.get(index));
        } else {
            return false;
        }
    }
    public void setAsHit(int position) {
        if (this.positions.contains(position)) {
            int index = this.positions.indexOf(position);
            this.cellStatuses.set(index, false);
        }
        return;
    }
    //метод проверки состояния корабля
    public boolean isShipDamagedBeyondRepair() {
        for(boolean status : this.cellStatuses) {
            if (status) {
                return false;
            }
        }
        return true;
    }
}