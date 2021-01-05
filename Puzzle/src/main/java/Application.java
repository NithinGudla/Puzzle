import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Application extends PApplet {

    Ball ball;
    Dimension dimension;
    public static int screenHeight, screenWidth;
    Bar[] pickBars;
    List<Bar> puzzleBars;
    PuzzleSolution puzzleSolution;
    Bar floatingBar = null;

    public static int puzzleSize;
    public static int barSize;
    Color backgroundColor;
    Color pinkColor, skyBlueColor, redColor;
    int puzzleX, puzzleY;

    int[][] barMatrix;

    public static void main(String[] args) {

        PApplet.main("Application", args);
    }

    private void createPickBars() {

        pickBars = new Bar[5];

        pickBars[0] = new Bar(barSize, 50, 3, "horizontal", skyBlueColor, false);
        pickBars[1] = new Bar(barSize, 50 + barSize + 10, 2, "horizontal", pinkColor, false);
        pickBars[2] = new Bar(barSize, 50 + 2 * barSize + 20, 3, "vertical", pinkColor, false);
        pickBars[3] = new Bar(barSize + barSize + 10, 50 + 2 * barSize + 20, 2, "vertical", skyBlueColor, false);
        pickBars[4] = new Bar(3 * barSize + 10, 50 + barSize + 10, 2, "horizontal", redColor, true);

    }

    @Override
    public void settings() {

        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight = dimension.height / 2 - 200;
        screenWidth = dimension.width / 2 - 100;
        size(screenWidth, screenHeight);
    }

    @Override
    public void setup() {

        puzzleSize = Integer.min(screenWidth / 2, height - 100);
        puzzleSize = puzzleSize - puzzleSize % 6;
        barSize = puzzleSize / 6;
        puzzleX = screenWidth / 2;
        puzzleY = 50;
        backgroundColor = new Color(255, 204, 150);
        pinkColor = new Color(255, 135, 180);
        skyBlueColor = new Color(135, 206, 235);
        redColor = new Color(250, 80, 80);

        barMatrix = new int[6][6];
        createPickBars();
        puzzleBars = new ArrayList<>();
        puzzleSolution = new PuzzleSolution();
        ball = new Ball(2, 10);
    }


    @Override
    public void mouseDragged() {

        if(floatingBar != null)
            return;

        int x = mouseX, y = mouseY;

        if(y <= pickBars[0].y + barSize && x <= pickBars[0].x + pickBars[0].len * barSize)
            floatingBar = new Bar(pickBars[0]);
        else if(y <= pickBars[1].y + barSize && x <= pickBars[1].x + pickBars[1].len * barSize)
            floatingBar = new Bar(pickBars[1]);
        else if(y < pickBars[2].y + pickBars[2].len * barSize && x < pickBars[2].x + barSize)
            floatingBar = new Bar(pickBars[2]);
        else if(y < pickBars[3].y + pickBars[3].y * barSize && x < pickBars[3].x + barSize)
            floatingBar = new Bar(pickBars[3]);
        else if(y < pickBars[4].y + barSize && x < pickBars[4].x + pickBars[4].len * barSize)
            floatingBar = new Bar(pickBars[4]);
        if(floatingBar != null){

            floatingBar.x = mouseX;
            floatingBar.y = mouseY;
        }
    }

    @Override
    public void mouseReleased() {

        if(floatingBar != null)
        addBarToPuzzle();
        floatingBar = null;
        displayBarMatrix();


    }

    @Override
    public void keyTyped() {

        if(key == 'r')
            setup();
        else if(key == 's')
        {
            puzzleSolution = new PuzzleSolution(barMatrix, puzzleBars);
            puzzleSolution.findSolution();

            if(puzzleSolution.solution == null)
                return;

            String finalSolution = puzzleSolution.solution.toString().trim();

            for(Bar b : puzzleBars)
            {
                System.out.println(b);
            }

            for(int i = 0; i < finalSolution.length(); i += 3){

                int indexX = Character.digit(finalSolution.charAt(i), 10);
                int indexY = Character.digit(finalSolution.charAt(i + 1), 10);
                char dir = finalSolution.charAt(i + 2);

                for(Bar b : puzzleBars){

                    if(b.indexX == indexX && b.indexY == indexY){

                        if(dir == 'l')
                            b.indexX = b.indexX - 1;
                        else if(dir == 'r')
                            b.indexX = b.indexX + 1;
                        else if(dir == 't')
                            b.indexY = b.indexY - 1;
                        else if(dir == 'd')
                            b.indexY = b.indexY + 1;

                        b.x = puzzleX + b.indexX * barSize;
                        b.y = puzzleY + b.indexY * barSize;

                        break;
                    }
                }

            }

            for(Bar b : puzzleBars)
            {
                System.out.println(b);
            }
        }
    }

    private void displayBarMatrix() {

        System.out.println("\n Displaying Bar Matrix \n");
        for(int i = 0; i < 6; i++){

            for(int j = 0; j < 6; j++){

                System.out.print(barMatrix[i][j] + " ");
            }

            System.out.println();
        }
    }

    private void addBarToPuzzle() {

        if( !((floatingBar.x >= puzzleX && floatingBar.y >= puzzleY) && (floatingBar.x <= puzzleX + puzzleSize && floatingBar.y <= puzzleY + puzzleSize)))
            return;

        if(floatingBar.solBar && isSolutionBarAdded())
            return;

        if(mouseX != floatingBar.x && mouseY != floatingBar.y){

            int barNumber = puzzleBars.size() + 2;

            if(floatingBar.solBar)
                barNumber = 1;

            if(floatingBar.orientation.equals("horizontal")){

                if(barMatrix[floatingBar.indexY][floatingBar.indexX] == 0 && barMatrix[floatingBar.indexY][floatingBar.indexX + 1] == 0){

                    if(floatingBar.len == 3 && barMatrix[floatingBar.indexY][floatingBar.indexX + 2] == 0){

                            puzzleBars.add(new Bar(floatingBar));
                            barMatrix[floatingBar.indexY][floatingBar.indexX] = barNumber;
                            barMatrix[floatingBar.indexY][floatingBar.indexX + 1] = barNumber;
                            barMatrix[floatingBar.indexY][floatingBar.indexX + 2] = barNumber;

                    }
                    else
                    {
                        puzzleBars.add(new Bar(floatingBar));
                        barMatrix[floatingBar.indexY][floatingBar.indexX] = barNumber;
                        barMatrix[floatingBar.indexY][floatingBar.indexX + 1] = barNumber;

                    }
                }
            }
            else{

                if(barMatrix[floatingBar.indexY][floatingBar.indexX] == 0 && barMatrix[floatingBar.indexY + 1][floatingBar.indexX] == 0){

                    if(floatingBar.len == 3 && barMatrix[floatingBar.indexY + 2][floatingBar.indexX] == 0){

                            puzzleBars.add(new Bar(floatingBar));
                            barMatrix[floatingBar.indexY][floatingBar.indexX] = barNumber;
                            barMatrix[floatingBar.indexY + 1][floatingBar.indexX] = barNumber;
                            barMatrix[floatingBar.indexY + 2][floatingBar.indexX] = barNumber;
                    }
                    else
                    {
                        puzzleBars.add(new Bar(floatingBar));
                        barMatrix[floatingBar.indexY][floatingBar.indexX] = barNumber;
                        barMatrix[floatingBar.indexY + 1][floatingBar.indexX] = barNumber;

                    }
                }
            }



        }

    }

    private boolean isSolutionBarAdded() {

        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++)
                if(barMatrix[i][j] == 1)
                    return true;

        return false;
    }

    @Override
    public void draw() {

        drawBackground();
        drawPuzzleLayout();
        drawPickBars();
        drawPuzzleBars();
        setAndDrawFloatingBar();
        ball.draw(this);
    }

    private void drawPuzzleBars() {

        for(Bar bar : puzzleBars)
            bar.drawBar(this);
    }

    private void setAndDrawFloatingBar() {

       // System.out.println(floatingBar);
        if (floatingBar != null) {

            floatingBar.x = mouseX;
            floatingBar.y = mouseY;
            if(floatingBar.x >= puzzleX && floatingBar.y >= puzzleY)
            {
                if (floatingBar.orientation.equals("horizontal")) {

                    if(floatingBar.x <= puzzleX + puzzleSize && floatingBar.y <= puzzleY + puzzleSize)
                    {

                        int temp1 = ((mouseX - puzzleX) / barSize) ;
                        int temp2 = ((mouseY - puzzleY) / barSize);

                        if(temp1 >= 6 - floatingBar.len)
                            temp1 = 6 - floatingBar.len;

                        floatingBar.indexX = temp1;
                        floatingBar.indexY = temp2;
                        floatingBar.x = puzzleX + temp1 * barSize;
                        floatingBar.y = puzzleY + temp2 * barSize;
                    }
                }
                else
                {
                    if(floatingBar.y <= puzzleY + puzzleSize && floatingBar.x <= puzzleX + puzzleSize)
                    {

                        int temp1 = ((mouseX - puzzleX) / barSize);
                        int temp2 = ((mouseY - puzzleY) / barSize);

                        if(temp2 >= 6 - floatingBar.len)
                            temp2 = 6 - floatingBar.len;

                        floatingBar.indexX = temp1;
                        floatingBar.indexY = temp2;
                        floatingBar.x = puzzleX + temp1 * barSize;
                        floatingBar.y = puzzleY + temp2 * barSize;
                    }
                }
            }

            floatingBar.drawBar(this);
        }



    }


    private void drawBackground() {

        background(backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue());
    }

    private void drawPuzzleLayout() {

        fill(255);
        rect(puzzleX, puzzleY, puzzleSize, puzzleSize);
    }

    private void drawPickBars() {

        for (Bar pickBar : pickBars)
            pickBar.drawBar(this);
    }
}
