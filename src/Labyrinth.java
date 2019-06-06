import java.util.*;

public class Labyrinth {

    private final static int labyrinthLength = 8;         //labirinto ilgis (pagal x)
    private final static int labyrinthWidth = 7;          //labirinto plotis (pagal y)
    private final static int startingCoordinateX = 1;     //pradžios koordinačių x
    private final static int startingCoordinateY = 1;     //pradžios koordinačių y
    private final static int exitCoordinateX = 5;         //pabaigos koordinačių x
    private final static int exitCoordinateY = 6;         //pabaigos koordinačių y
    private int points = 0;                               //kiek surinkta taškų

    private List<List<Cell>> listOfPaths = new ArrayList<>();       //Listas saugantis visus kelius iki išėjimo
    private List<Integer> pathPoints = new ArrayList<>();           //Listas saugantis kelių taškus

    public static void main(String[] args) {
        Labyrinth Labyrinth = new Labyrinth();
        int[] entrance = new int[]{startingCoordinateX, startingCoordinateY};
        Cell[][] map = setUpLabyrinth();
        List<Cell> path = Labyrinth.findPath(map, entrance);
        printPath(path);
    }

    //Metodas nustatantis labirintą
    private static Cell[][] setUpLabyrinth() {
        Cell[][] labyrinth = new Cell[labyrinthLength][labyrinthWidth];
        for (int x = 0; x < labyrinthLength; x++) {
            for (int y = 0; y < labyrinthWidth; y++) {
                if (x == 0 || y == 0 || x == labyrinthLength - 1 || y == labyrinthWidth - 1) {
                    labyrinth[x][y] = new Cell(false, 0, x, y);
                } else {
                    labyrinth[x][y] = new Cell(true, 5, x, y);
                }
            }
        }
        labyrinth[2][2].status = false;
        labyrinth[2][4].status = false;
        labyrinth[3][2].status = false;
        labyrinth[3][4].status = false;
        labyrinth[4][2].status = false;
        labyrinth[4][4].status = false;
        labyrinth[5][2].status = false;
        labyrinth[5][3].status = false;
        labyrinth[5][4].status = false;
        labyrinth[1][2].setValue(15);
        labyrinth[exitCoordinateX][exitCoordinateY].setStatus(true);
        labyrinth[exitCoordinateX][exitCoordinateY].exit(true);
        return labyrinth;
    }

    private List findPath(Cell[][] labyrinth, int[] entrance) {
        List<Cell> path = new ArrayList<>();

        exploring(labyrinth, entrance[0], entrance[1], path);       //ieško išėjimo

        if (listOfPaths.isEmpty()) {
            return Collections.emptyList();
        }

        int indexOfBestPath = bestPath();                           //geriausio kelio numeris
        return listOfPaths.get(indexOfBestPath);
    }

    //metodas gražinantis numerį to path, kuris yra trumpesnis (jei yra du vienodai taškų verti keliai)
    private int bestPath() {
        int largestAmountOfPoints1 = Integer.MIN_VALUE, largestAmountOfPoints2 = Integer.MIN_VALUE;
        int index1 = 0, index2 = 0, count = 0;
        for (int points : pathPoints) {
            if (points > largestAmountOfPoints1) {
                largestAmountOfPoints2 = largestAmountOfPoints1;
                largestAmountOfPoints1 = points;
                index1 = count;
            } else if (points > largestAmountOfPoints2) {
                largestAmountOfPoints2 = points;
                index2 = count;
            }
            count++;
        }

        if (largestAmountOfPoints1 == largestAmountOfPoints2) {
            if ((listOfPaths.get(index1).size()) <= (listOfPaths.get(index2).size())) {
                return index1;
            } else {
                return index2;
            }
        } else {
            return index1;
        }
    }

    //Metodas ieškantis kelio
    private boolean exploring(Cell[][] labyrinth, int x, int y, List<Cell> path) {
        int[][] directions = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};            //visos kryptis kuriomis galima judėti

        //patikrina ar langelis yra galimas, atviras ir ar buvo jau juo eita
        if (!isValidCell(x, y) || !labyrinth[x][y].isOpen() || labyrinth[x][y].isVisited()) {
            return false;
        }

        points += labyrinth[x][y].Value();
        path.add(new Cell(x, y));

        //Jeigu jau stovi ant išėjimo, tada išsaugo tą kelią ir ieško toliau gražindamas false
        if (labyrinth[x][y].isExit()) {
            takesPath(path, points);
            path.remove(path.size() - 1);
            points -= labyrinth[x][y].Value();
            return false;
        }

        labyrinth[x][y].setVisited(true);

        //sukamas for-each ciklas per visus galimus ėjimo variantus
        for (int[] direcion : directions) {
            Cell cell = getNextCoordinate(x, y, direcion[0], direcion[1]);
            if (exploring(labyrinth, cell.coordinateX, cell.coordinateY, path)) {
                return true;
            }
        }

        //Jei nėra kur eiti, išmetamas langelis iš path listo ir atimami taškai
        path.remove(path.size() - 1);
        labyrinth[x][y].setVisited(false);
        points -= labyrinth[x][y].Value();
        return false;
    }

    //metodas išsaugantis kelią, surinktus taškus bei žingsnių kiekį
    private void takesPath(List<Cell> path, int points) {
        listOfPaths.add(new ArrayList<>(path));
        pathPoints.add(points);
    }

    //metodas patikrinantis ar langelis yra galimas
    private boolean isValidCell(int x, int y) {
        if (x < 0 || x >= labyrinthWidth || y < 0 || y >= labyrinthLength) {
            return false;
        }
        return true;
    }

    //metodas gražinantis koordinatę
    private Cell getNextCoordinate(int x, int y, int i, int j) {
        return new Cell(x + i, y + j);
    }

    //Metodas atspausdina kelią iki išėjimo
    private static void printPath(List<Cell> path) {
        int count = 0;
        for (Cell cell : path) {
            count++;
            System.out.println(cell.coordinateX + " " + cell.coordinateY);
        }
        System.out.println("Žingsnių kiekis: " + (count - 1));
    }
}
