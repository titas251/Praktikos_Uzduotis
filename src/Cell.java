public class Cell {
    public boolean status;
    private int value;
    public int coordinateX;
    public int coordinateY;
    private boolean exit;
    private boolean visited;

    public Cell() {
    }

    public Cell(int x, int y) {
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public Cell(boolean status, int value, int x, int y) {
        this.status = status;
        this.value = value;
        coordinateX = x;
        coordinateY = y;
    }

    public void exit(boolean exit){
        this.exit = exit;
    }

    public boolean isExit(){
        return exit;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean isVisited(){
        return visited;
    }

    public boolean isOpen() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int Value(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }
}

