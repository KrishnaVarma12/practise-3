package ueb;

import static ueb.ArrayTools.*;
import static ueb.Data.*;


public class Analyze {

    public static final String SIGN_WAREHOUSE = "W";
    public static final String SIGN_CUSTOMER = "C";
    public static final String SIGN_EMPTY = "E";

 
    public static final int[] POS_SERVICE = {0, 0};

   
    public static int units = 0;

    public static int[][][] map = getMap();
 
    public static int[] posDrone = POS_SERVICE;


    public static void resetToOrigState() {
        map = getMap();
        units = 0;
        posDrone = POS_SERVICE;
    }

    public static void printCurrentState() {
        System.out.println("---------------------------------------");
        for (int col = 0; col < map[0].length; col++) {
            for (int row = 0; row < map.length; row++) {
                System.out.format("%s", isWarehouse(row, col) ? SIGN_WAREHOUSE : map[row][col].length > 0 ? SIGN_CUSTOMER : SIGN_EMPTY);
                for (int i : map[row][col]) {
                    System.out.printf("%d", i);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.printf("Drone now at %d/%d flew %d units%n", posDrone[X], posDrone[Y], units);
        System.out.println("---------------------------------------");
    }


    
    static int getPrintWidthPerColumn(int column) {
        int[][][] map = getMap();
        int width = 0;
        for (int[] cell : map[column]) {
            if (cell != null && width < cell.length) {
                width = cell.length;
            }
        }
        return width + 1;
    }
    //</editor-fold>

    static int calcDistanceBetween(int[] pos1, int[] pos2) {
        int euclideanDistance = Integer.MAX_VALUE;
       
        if (pos1 != null && pos2 != null && pos1.length == 2 && pos2.length == 2) {
            euclideanDistance = (int) Math.ceil(Math.sqrt(Math.pow(pos2[Y] - pos1[Y], 2) + Math.pow(pos2[X] - pos1[X], 2)));
        }
        return euclideanDistance;
    }

    
    static boolean isValidPosition(int[] pos) {
        boolean isValid = false;
        // position could not be a null
        // position must be represented [col, row] hence length is 2
        // Each cell is identified by an integer tuple
        // positions/indexes/[col, row] are always whole numbers (0,1,2,3,4.....n)
        if (pos != null && pos.length == 2 && pos[X] >= 0 && pos[Y] >= 0) {
            int[] dimension = getMapDimensions();
            if (pos[X] <= dimension[Y] && pos[Y] <= dimension[X]) {
                isValid = true;
            }
        }
        return isValid;
    }

    private static void flyDroneTo(int[] pos) {
        if (isValidPosition(pos)) {
            int distance = calcDistanceBetween(posDrone, pos);
            System.out.println("fly drone to " + pos[X] + "/" + pos[Y] + " distance " + distance);
            units += distance;
            posDrone = pos;
        } else {
            System.err.println("position is not valid");
        }
    }


    private static int transportSameProducts(int[] from, int[] to, int product, int count) {
        int[] warehouse = findNearestWarehouse(from, product);
        if (warehouse != null) {
            flyDroneTo(warehouse);
            while (count > 0 && containsAt(map[warehouse[X]][warehouse[Y]], product) > -1) {
                map[warehouse[X]][warehouse[Y]] = deleteElementAt(map[warehouse[X]][warehouse[Y]],
                        containsAt(map[warehouse[X]][warehouse[Y]], product)).clone();
                map[to[X]][to[Y]] = insertElementAt(map[to[X]][to[Y]], 0, product).clone();
                count--;
            }
            flyDroneTo(new int[]{to[X], to[Y]});
        } else {
            System.err.println("Error: " + count + " of product " + product + " missing in warehouses");
            count = 0; // to exit
        }
        return count;
    }

    private static int[] findNearestWarehouse(int[] pos, int product) {
        int[] nearestWareHouse = null;
        if (pos != null && isValidPosition(pos)) {
            int distance = -1;
            for (int row = 0; row < map.length; row++) {
                for (int col = 0; col < map[0].length; col++) {
                    if (isWarehouse(row, col) && containsAt(map[row][col], product) > -1) {
                        int[] warehouse = new int[]{row, col};
                        int d = calcDistanceBetween(warehouse, pos);
                        if (distance == -1 || d < distance) {
                            distance = d;
                            nearestWareHouse = warehouse;
                        }
                    }
                }
            }
        }
        return nearestWareHouse;
    }

  
    public static void transportOrdersOfOneSeries(int[][] orders) {
        if (orders == null) {
            throw new IllegalArgumentException("INVALID argument");
        } else {
            for (int[] order : orders) {
                System.out.printf("deliver %d of product %d to (%d/%d)%n", order[CT], order[ID], order[X], order[Y]);
                int remaining = 0;
                do {
                    remaining = transportSameProducts(posDrone, new int[]{order[X], order[Y]}, order[ID], remaining > 0 ? remaining : order[CT]);
                } while (remaining > 0);
            }
            flyDroneTo(POS_SERVICE);
        }
    }
}

