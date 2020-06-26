import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();
        int[] numbers = new int[limit];
        int number;
        for(int i = 0; i < limit ; i++) {
            number = scanner.nextInt();
            numbers[i] = number;
        }
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        for(int i = 0; i < limit - 1 ; i++) {
            if(numbers[i] == n && numbers[i + 1] == m || numbers[i] == m && numbers[i + 1] == n) {
                System.out.println("true");
                return;
            }
        }
        System.out.println(false);

    }
}