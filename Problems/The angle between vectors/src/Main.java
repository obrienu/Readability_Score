import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] input = new double[4];
        for(int i =0; i < 4; i++) {
            input[i] = scanner.nextDouble();
        }

        double vectorProduct = getProductVector(input[0], input[1])

                * getProductVector(input[2], input[3]);
        double product = getVector(input[0], input[1], input[2], input[3]);
        int result = (int)getAngle(product, vectorProduct);
        System.out.println(result);
    }

    public static double getProductVector(double a, double b) {
        return Math.sqrt((a * a) + (b * b));
    }

    public static double getVector(double a1, double a2, double b1, double b2) {
        return a1 * b1 + a2 * b2 ;
    }

    public static double getAngle(double product, double vectorProduct){
        double div = Math.acos(product / vectorProduct) ;
        return div*180.0d/Math.PI;
    }
}