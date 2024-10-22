package ru.job4j.chess.firuges.black;

import ru.job4j.chess.ImpossibleMoveException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

public class BishopBlack implements Figure {
    private final Cell position;

    public BishopBlack(final Cell ps) {
        position = ps;
    }

    @Override
    public Cell position() {
        return position;
    }

    @Override
    public Cell[] way(Cell dest) {
        if (!isDiagonal(this.position(), dest)) {
            throw new ImpossibleMoveException(
                    String.format("could not move by diagonal from %s to %s", position(), dest)
            );
        }
        int size = Math.abs(dest.getX() - position.getX());
        Cell[] steps = new Cell[size];
        int deltaX = (dest.getX() - position().getX() > 0) ? 1 : -1;
        int deltaY = (dest.getY() - position().getY() > 0) ? 1 : -1;
        for (int index = 0; index < size; index++) {
            int x = position.getX() + (index + 1) * deltaX;
            int y = position.getY() + (index + 1) * deltaY;
            steps[index] = Cell.findBy(x, y);
        }
        return steps;
    }

    public boolean isDiagonal(Cell source, Cell dest) {
        return Math.abs(source.getX() - dest.getX()) == Math.abs(source.getY() - dest.getY());
    }

    @Override
    public Figure copy(Cell dest) {
        return new BishopBlack(dest);
    }
}
