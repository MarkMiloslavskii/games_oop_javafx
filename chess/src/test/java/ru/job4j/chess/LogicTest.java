package ru.job4j.chess;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.black.BishopBlack;
import ru.job4j.chess.firuges.white.KingWhite;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Disabled("Тесты отключены. Удалить аннотацию после реализации всех методов по заданию.")
public class LogicTest {

    @Test
    public void whenMoveThenFigureNotFoundException()
            throws FigureNotFoundException, OccupiedCellException, ImpossibleMoveException {
        Logic logic = new Logic();
        FigureNotFoundException exception = assertThrows(FigureNotFoundException.class, () -> {
            logic.move(Cell.C1, Cell.H6);
        });
        assertThat(exception.getMessage()).isEqualTo("Figure not found on the board.");
    }

    @Test
    public void whenPositionIsC1() {
        BishopBlack bishopBlack = new BishopBlack(Cell.C1);
        assertEquals(bishopBlack.position(), Cell.C1);
    }

    @Test
    public void whenCopyBishop() {
        BishopBlack bishopBlack = new BishopBlack(Cell.C8);
        Figure copyBishopBlack = bishopBlack.copy(Cell.F5);
        assertEquals(copyBishopBlack.position(), Cell.F5);
    }

    @Test
    public void whenBishopMoveToG5() {
        BishopBlack bishopBlack = new BishopBlack(Cell.C1);
        Cell[] result = bishopBlack.way(Cell.G5);
        Cell[] expected = {Cell.D2, Cell.E3, Cell.F4, Cell.G5};
        assertArrayEquals(result, expected);
    }

    @Test
    public void whenBishopMoveToC8() {
        BishopBlack bishopBlack = new BishopBlack(Cell.F5);
        Cell[] result = bishopBlack.way(Cell.C8);
        Cell[] expected = {Cell.E6, Cell.D7, Cell.C8};
        assertArrayEquals(result, expected);
    }

    @Test
    public void whenBishopMoveWrong() {
        BishopBlack bishopBlack = new BishopBlack(Cell.E4);
        ImpossibleMoveException exception = assertThrows(
                ImpossibleMoveException.class,
                () -> new BishopBlack(Cell.E4).way(Cell.B6));
        assertThat(exception.getMessage()).isEqualTo("Could not move by diagonal from E4 to B6");
    }

    @Test
    public void whenBishopHasFigureOnTheWay() {
        Logic logic = new Logic();
        BishopBlack bishopBlack = new BishopBlack(Cell.G7);
        KingWhite kingWhite = new KingWhite(Cell.E5);
        logic.add(bishopBlack);
        logic.add(kingWhite);
        OccupiedCellException exception = assertThrows(
                OccupiedCellException.class,
                () -> logic.move(Cell.G7, Cell.C3));
        assertThat(exception.getMessage()).isEqualTo("There is a figure on the way");
    }

    public void whenBishopHasNotOnTheBoard() {
        Logic logic = new Logic();
        BishopBlack bishopBlack = new BishopBlack(Cell.G7);
        FigureNotFoundException exception = assertThrows(
                FigureNotFoundException.class,
                () -> logic.move(Cell.G7, Cell.C3));
        assertThat(exception.getMessage()).isEqualTo("Figure not found on the board.");
    }
}