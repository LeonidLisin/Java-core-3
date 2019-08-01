public class Spiral {
    public static void main(String[] args) {
        final int a = 4, b = 5; // a - число столбцов, b - число строк
        int x = 0, y = 0, xInc = 1, yInc = 0;
        int count = 0, dirCount = 1, limitA = a, limitB = b, limit = limitA;
        int[][]m = new int[a][b];

        for (int i = 1; i <= a*b ; i++) {
            m[x][y] = i;
            count++;
            if (count == limit) {
                dirCount++;
                switch (dirCount){
                    case 1:
                        xInc=1; yInc=0;
                        limit = --limitA;
                        break;
                    case 2:
                        xInc=0; yInc=1;
                        limit = --limitB;
                        break;
                    case 3:
                        xInc=-1; yInc=0;
                        limit = --limitA;
                        break;
                    case 4:
                        xInc=0; yInc=-1;
                        limit = --limitB;
                        break;
                    case 5:
                        dirCount=1;
                        xInc=1; yInc=0;
                        limit = --limitA;
                        break;
                }
                count = 0;
            }
            x+=xInc; y+=yInc;
        }

        for (int i = 0; i < b; i++) {
            System.out.println();
            for (int j = 0; j < a; j++) {
                System.out.print(m[j][i] + " ");
            }
        }
    }
}
