package ueb;


public class ProjectData {

//    map as shown on system.out
//    private static final int[][][] MAP_ROTATED = {
//        {{1,1,3,3,4,4,4,4}, {}, {}, {}, {} , {}, {}   , {}, {}, {}},
//        {{}               , {}, {}, {}, {} , {}, {}   , {}, {}, {}},
//        {{}               , {}, {}, {}, {} , {}, {}   , {}, {}, {}},
//        {{2,2}            , {}, {}, {}, {} , {}, {3,4}, {}, {}, {}},
//        {{}               , {}, {}, {}, {} , {}, {}   , {}, {}, {}},
//        {{}               , {}, {}, {}, {} , {}, {}   , {}, {}, {}},
//        {{}               , {}, {}, {}, {0}, {}, {}   , {}, {}, {}}
//    };
    // 10 columns, 7 rows, addressed by (x,y) or (col, row)
    // a row in the declaration is a column when accessed/printed
    private static final int[][][] MAP = {
            {{1, 1, 3, 3, 4, 4, 4, 4}, {}, {}, {2, 2}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {0}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {3, 4}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}},
            {{}, {}, {}, {}, {}, {}, {}}
    };

    /* ORDER_CONSTANTS: classifying the sequence of every order */
    public static final int X = 0; //x-coordinate of the deliverylocation
    public static final int Y = 1; //y-coordinate of the deliverylocation
    public static final int ID = 2; //ID of the product as part of ones Order
    public static final int CT = 3; //Count of the specific product to order

   
    private static final int[][][] ORDER_SERIES = {
            { //series 0
                    {1, 2, 0, 1}, //customer at (1,2) orders product 0, 1 times
            },
            { //series 1
                    {1, 2, 0, 1}, //customer at (1,2) orders product 0, 1 times
                    {1, 2, 1, 1}, //customer at (1,2) orders product 1, 1 times
                    {8, 5, 3, 3} //customer at (8,5) orders product 3, 3 times
            },
            { //series 2
                    {7, 4, 4, 5}, //customer at (7,4) orders product 4, 5 times
                    {0, 6, 0, 1} //customer at (0,6) orders product 0, 1 times
            },
            { //series 3
                    {7, 4, 0, 5} //customer at (7,4) orders product 0, 5 times
            }
    };

    /**
     * Get the dimensions of the map. First value contains the number of columns, second the number of rows.
     *
     * @return the dimensions of the map {noOfColumns, noOfRows}
     */
    public static int[] getMapDimensions() {
        return new int[]{MAP[0].length, MAP.length};
    }

    
    public static int getNoOfSeries() {
        return ORDER_SERIES.length;
    }

    public static boolean isWarehouse(int posX, int posY) {
        return MAP[posX][posY].length > 0;
    }

    
    public static int[][] getOrderSeries(int idx) {
        if (idx < 0 || idx > ORDER_SERIES.length - 1) {
            String msg = String.format("idx must be greater than %d and less than %d", 0, ORDER_SERIES.length);
            throw new IllegalArgumentException(msg);
        }
        int[][] productList = ORDER_SERIES[idx].clone();
        for (int i = 0; i < ORDER_SERIES[idx].length; i++) {
            productList[i] = ORDER_SERIES[idx][i].clone();
        }
        return productList;
    }

    
    public static int[][][] getMap() {
        int[][][] copy = new int[MAP.length][MAP[0].length][];
        for (int row = 0; row < MAP.length; row++) {
            for (int col = 0; col < MAP[0].length; col++) {
                copy[row][col] = MAP[row][col].clone();
            }
        }
        return copy;
    }
}