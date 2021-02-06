/** My warehouse Project
*/
public class Main {

 
    public static void main(String[] args) {
        for (int no = 0; no < Data.getNoOfSeries(); no++) {
            int[][] order = Data.getOrderSeries(no);
            Analyze.resetToOrigState();
            System.out.println("***************************************");
            System.out.println("Warehouses (initial):");
            Analyze.printCurrentState();
            System.out.println("Orders of series " + no + ":");
            Analyze.transportOrdersOfOneSeries(order);
            Analyze.printCurrentState();
        }
    }

}
