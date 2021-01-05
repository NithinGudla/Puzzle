import processing.core.PApplet;
import java.awt.Color;

public class Bar {

    int x, y, len, indexX, indexY;
    String orientation;
    Color color;
    boolean solBar;


    public Bar(Bar original){

        this.x = original.x;
        this.y = original.y;
        this.len = original.len;
        this.orientation = original.orientation;
        this.color = original.color;
        this.solBar = original.solBar;
        this.indexY = original.indexY;
        this.indexX = original.indexX;
    }
    public Bar(int x, int y, int len, String orientation, Color color, boolean solBar) {
        this.x = x;
        this.y = y;
        this.len = len;
        this.orientation = orientation;
        this.color = color;
        this.solBar = solBar;
    }

    public Bar(int x, int y, int len, int indexX, int indexY, String orientation, Color color, boolean solBar) {

        this.x = x;
        this.y = y;
        this.len = len;
        this.indexX = indexX;
        this.indexY = indexY;
        this.orientation = orientation;
        this.color = color;
        this.solBar = solBar;
    }

    void drawBar(PApplet canvas){


        canvas.stroke(255);
        canvas.fill(color.getRed(), color.getGreen(), color.getBlue());
        if(orientation.equals("horizontal"))
            canvas.rect(x, y, len * Application.barSize, Application.barSize);
        else
            canvas.rect(x, y, Application.barSize, len * Application.barSize);
    }

    @Override
    public String toString() {
        return "Bar{" +
                "x=" + x +
                ", y=" + y +
                ", len=" + len +
                ", indexX=" + indexX +
                ", indexY=" + indexY +
                ", orientation='" + orientation + '\'' +
                ", color=" + color +
                ", solBar=" + solBar +
                '}';
    }
}
