import processing.core.PApplet;

public class Ball {

    int x;
    int y;
    int dir1;
    int dir2;
    int diameter;
    int r;
    int g;
    int b;

    public Ball(int speed, int diameter) {

        this.dir1 = ((int) (Math.random() * 10)) < 6 ? speed : - speed;
        this.dir2 = ((int) (Math.random() * 10)) < 6 ? speed : - speed;
        this.diameter = diameter;

        x = (int)(Math.random() * Application.screenWidth);
        y = (int) (Math.random() * Application.screenHeight);

        r = (int)(Math.random() * 255);
        g = (int)(Math.random() * 255);
        b = (int)(Math.random() * 255);
    }



    public void draw(PApplet canvas){

        canvas.stroke(r, g, b);
        canvas.fill(r, g, b);
        canvas.ellipse(x, y, diameter, diameter);
        update();
    }

    private void update() {

        if(x >= Application.screenWidth || x <= 0)
            dir1 = -dir1;
        if(y >= Application.screenHeight || y <= 0)
            dir2 = -dir2;

        x += ((int) (Math.random() * 10)) < 6 ? dir1 * 2 : dir1;
        y += ((int) (Math.random() * 10)) < 6 ? dir2 * 2 : dir2;


    }
}
