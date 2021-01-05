import sun.util.locale.StringTokenIterator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleSolution {

    List<Bar> puzzleBars;
    int[][] barMatrix;

    StringBuffer solution = null;
    Map<String, Integer> myMap = new HashMap<>();
    Integer minCount = null;


    public PuzzleSolution(int[][] barMatrix, List<Bar> puzzleBars) {

        this.puzzleBars = puzzleBars;
        this.barMatrix = barMatrix;

    }

    public PuzzleSolution() {

    }

    public String stringifyMatrix(int[][] matrix){

        StringBuffer sb = new StringBuffer(50);

        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++)
                sb.append(matrix[i][j]);

            return sb.toString();
    }

    public void findSolution(){

        int[][] sampleMatrix = new int[6][6];

        for(int i = 0; i < 6; i++)
            for(int j = 0; j < 6; j++)
                sampleMatrix[i][j] = barMatrix[i][j];
        solve(0, sampleMatrix, new StringBuffer());

        System.out.println(minCount);
        System.out.println(solution);
    }


    private void solve(int count, int[][] matrix, StringBuffer sb) {


        if(count > 30)
            return;

        if(matrix[2][4] == 1 && matrix[2][5] == 1)
        {
            if(minCount == null) {
                minCount = count;
                solution = new StringBuffer(sb);
            }
            else {

                if(count < minCount){

                    minCount = count;
                    solution = new StringBuffer(sb);
                }
            }

        }

        String matrixString = stringifyMatrix(matrix);
        if(myMap.containsKey(matrixString) && count > myMap.get(matrixString))
            return;

        myMap.put(matrixString, count);

        for(int i = 0; i < puzzleBars.size(); i++){

            checkWithBar(puzzleBars.get(i), count, matrix, sb);
        }
    }

    private void checkWithBar(Bar bar, int count, int[][] matrix, StringBuffer sb) {

        int x = bar.indexY;
        int y = bar.indexX;
        int len = bar.len;

        if(bar.orientation.equals("horizontal")){

            if(y != 0 && matrix[x][y - 1] == 0) {

                sb.append("" + bar.indexX + bar.indexY + "l");
                bar.indexX = bar.indexX - 1;
                matrix[x][y - 1] = matrix[x][y];
                matrix[x][y + len - 1] = 0;


                solve(count + 1, matrix, sb);

                sb.delete(sb.length() - 3, sb.length());
                bar.indexX = bar.indexX + 1;
                matrix[x][y - 1] = 0;
                matrix[x][y + len - 1] = matrix[x][y];

            }

            if(y + len - 1 != 5 && matrix[x][y + len] == 0){

                sb.append("" + bar.indexX + bar.indexY + "r");
                bar.indexX = bar.indexX + 1;
                matrix[x][y + len] = matrix[x][y];
                matrix[x][y] = 0;


                solve(count + 1, matrix, sb);
                sb.delete(sb.length() - 3, sb.length());

                bar.indexX = bar.indexX - 1;
                matrix[x][y + len] = 0;
                matrix[x][y] = matrix[x][y + 1];
            }


        }
        else
        {
            if(x != 0 && matrix[x - 1][y] == 0){


                sb.append("" + bar.indexX + bar.indexY + "t");
                bar.indexY = bar.indexY - 1;
                matrix[x - 1][y] = matrix[x][y];
                matrix[x + len - 1][y] = 0;


                solve(count + 1, matrix, sb);
                sb.delete(sb.length() - 3, sb.length());

                bar.indexY = bar.indexY + 1;
                matrix[x - 1][y] = 0;
                matrix[x + len - 1][y] = matrix[x][y];

            }

            if(x + len - 1 != 5 && matrix[x + len][y] == 0){

                sb.append("" + bar.indexX + bar.indexY + "d");
                bar.indexY = bar.indexY + 1;
                matrix[x + len][y] = matrix[x][y];
                matrix[x][y] = 0;


                solve(count + 1, matrix, sb);
                sb.delete(sb.length() - 3, sb.length());

                bar.indexY = bar.indexY - 1;
                matrix[x + len][y] = 0;
                matrix[x][y] = matrix[x + 1][y];
            }


        }
    }
}
