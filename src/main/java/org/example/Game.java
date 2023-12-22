package org.example;

import java.util.*;

public class Game {
    private Player player1;
    private Player player2;
    private Graph gameField;
    private GameStatus status;

    public Game() {
        this.player1 = new Player();
        this.player2 = new Player();
        this.gameField = Graph.createAsGameField();
        this.status = GameStatus.NOT_STARTED;
    }
    //методы получения данных об игре (Get-ры)

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Graph getGameField() {
        return gameField;
    }

    public GameStatus getStatus() {
        return status;
    }

    //методы смены статуса игры
    public void initiatePlacingPhase() {
        this.status = GameStatus.SHIP_PLACEMENT;
    }

    public void initiateFightingPhase() {
        this.status = GameStatus.BATTLE_PHASE;
    }

    public void endGame() {
        this.status = GameStatus.NOT_STARTED;
    }

    //методы постановки кораблей
    public Set<Integer> takenPositions = new HashSet<>();
    public int[] shipSizes = {4,3,3,2,2,2,1,1,1,1};

    public boolean placeShip(int row, int col, int size, Player player, int orientationIndex) {
        int cell = row*10 + (col + 1);
        List<Integer> shipCoords = new ArrayList<>();
        shipCoords.add(cell);

        if(orientationIndex == 0) {
            if ((10 - cell % 10 > size) && (cell % 10 != 0)) {
                for (int k = 1; k < size; k++) {
                    shipCoords.add(cell + k);
                }
            } else {
                for (int k = 1; k < size; k++) {
                    shipCoords.add(cell - k);
                }
            }
        } else {
            if (10 - cell / 10 > size) {
                for (int k = 1; k < size; k++) {
                    shipCoords.add(cell + k*10);
                }
            } else {
                for (int k = 1; k < size; k++) {
                    shipCoords.add(cell - k*10);
                }
            }
        }

        if (checkIfPositionValid(shipCoords, takenPositions)) {
            for (Integer shipCoord : shipCoords) {
                takenPositions.add(shipCoord);
                takenPositions.addAll(gameField.getNeighbors(shipCoord));
            }
            player.addShip(new Ship(shipCoords));
        } else {
            return false;
        }
        return true;
    }

    private static boolean checkIfPositionValid(List<Integer> shipCoords, Set<Integer> forbiddenPositions) {
        for (Integer shipCoord : shipCoords) {
            if (forbiddenPositions.contains(shipCoord)) {
                return false;
            }
        }
        return true;
    }

    public void automaticShipPlacement() {
        this.takenPositions = new HashSet<>();
        Random random = new Random();
        for (int shipSize : shipSizes) {
            boolean placed = false;
            while (!placed) {
                int row = random.nextInt(10);
                int col = random.nextInt(10);
                int orientationIndex = random.nextInt(2);
                if (placeShip(row, col, shipSize, this.player2, orientationIndex)) {
                    placed = true;
                }
            }
        }
    }

    //методы боя
    public Set<Integer> neighbourPositions = new HashSet<>();

    public Set<Integer> getNeighbourPositions() {
        return neighbourPositions;
    }

    public boolean makeShotAt(int row, int col, Player player) { //true - попал, false - промахнулся
        neighbourPositions.clear();
        if(row > 9 || col > 9) {
            return false;
        }
        int cell = row*10 + (col + 1);
        for(Ship ship : player.getShips()) {
            if(ship.getPositions().contains(cell) && !(ship.isCellHit(cell))) {
                ship.setAsHit(cell);
                if(ship.isShipDamagedBeyondRepair()) {
                    neighbourPositions = gameField.getShipAdjectiveCells(ship.getPositions());
                    ship.getPositions().forEach(neighbourPositions::remove);
                    player.removeShip(ship);
                }
                return true;
            }
        }
        return false;
    }

    public int AIrow = 0;
    public int AIcol = 0;
    public boolean automaticMakeShotAt() {
        Random random = new Random();
        AIrow = random.nextInt(10);
        AIcol = random.nextInt(10);
        return makeShotAt(AIrow,AIcol, player1);
    }
}
