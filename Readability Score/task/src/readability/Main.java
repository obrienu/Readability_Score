package readability;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            String input = getFileText(args[0]);
            int characters = input.replaceAll("\\s+", "").length();
            int totalWords = input.split("\\s+").length;
            int sentences = getNumberOfSentences(input);
            int[] arrOfSyllableCounts = getNumberOfSyllables(input);
            int syllables = arrOfSyllableCounts[0];
            int polySyllables = arrOfSyllableCounts[1];
            System.out.println("Words: " + totalWords);
            System.out.println("Sentences: " + sentences);
            System.out.println("Characters: " + characters);
            System.out.println("Syllables: " + syllables);
            System.out.println("Polysyllables: " + polySyllables);

            while(true){
                var scanner = new java.util.Scanner(System.in);
                System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
                String answer = scanner.next();
                System.out.println();
                if("ARI".equals(answer)) {
                    autoReadabiltyIndex(characters, sentences, totalWords);
                }
                if ("FK".equals(answer)) {
                    fleschKincaidTests(totalWords, sentences, syllables);
                }
                if ("SMOG".equals(answer)) {
                    SMOGIndex(sentences, polySyllables);
                }
                if("CL".equals(answer)) {
                    colemanLiauIndex(characters, totalWords, sentences);
                }
                if("all".equals(answer)) {
                    int a = autoReadabiltyIndex(characters, sentences, totalWords);
                    int b = fleschKincaidTests(totalWords, sentences, syllables);
                    int c = SMOGIndex(sentences, polySyllables);
                    int d = colemanLiauIndex(characters, totalWords, sentences);
                    double average = Math.round(((a + b + c + d) / 4.0) * 100.0) / 100.0 ;
                    System.out.println("This text should be understood in average by "+ average + " year olds.");
                }
                break;
            }



        } catch (IOException e) {
        System.out.println("Failed to read file" + e.getMessage());
        }
    }

    public static int buildString(String test, double  score) {
        score = Math.round(score * 100.0) / 100.0;
        int understoodBy = getAgeGroup((int)Math.round(score));
        String testName;
        switch(test) {
            case "ARI":
                testName = "Automated Readability Index: ";
                break;
            case "FK":
                testName = "Flesch–Kincaid readability tests: ";
                break;
            case "SMOG":
                testName = "Simple Measure of Gobbledygook: ";
                break;
            case "CL":
                testName = "Coleman–Liau index: ";
                break;
            default: testName = "";
        }
        System.out.println(testName + score + " " + "(about "+ understoodBy + " year olds)");
        return understoodBy;
    }

    public static int colemanLiauIndex(double characters, double word, double sentences) {
        double score = 0.0588 * (characters / word * 100.0) - 0.296 * (sentences / word * 100.0) - 15.8;
        return buildString("CL", score);
    }

    public  static int fleschKincaidTests(double words, double sentences,double syllables) {
        double score = 0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
        return buildString("FK", score);
    }

    public static int SMOGIndex(double sentences, double polySyllables) {
        double score = 1.043 * Math.sqrt((polySyllables * (30.0 / sentences))) + 3.1291;
        return buildString("SMOG", score);
    }

    public static int[] getNumberOfSyllables(String text) {
        String[] words = text.split("\\s+");
        int syllables = 0;
        int polySyllables = 0;
        for(String word: words) {
            String[] vowels = word.split("([B-DF-HJ-NP-TV-XZ][b-df-hj-np-tv-xz]*)|([b-df-hj-np-tv-xz]+)" );
            //System.out.println(Arrays.toString(vowels) + " " + word);
            int counter = 0;
            for(int i = 0; i < vowels.length; i++) {
                if(vowels[i].matches("[aeiouyAEIOUY]{1,3}\\W*")) {
                    syllables++;
                    counter ++;
                }
            }
            if(word.matches(".*(e|e\\W)$") && !word.matches("\\b[B-DF-HJ-NP-TV-XZ]?[b-df-hj-np-tv-xz]*e+\\W?$")){
                syllables -= 1;
                counter -= 1;
            }
            if(word.matches("\\d+(,\\d{3})*")) {
                syllables++;
            }
            if (counter > 2) {
                polySyllables++;
            }
        }

        int[] arrSyllables = {syllables, polySyllables};
        return arrSyllables;
    }

    public static int getNumberOfSentences(String text) {
        String[] words = text.split(" ");
        int sentence = 0;
        for (String word: words) {
            if (word.matches(".*([!?.])")) {
                sentence++;
            }
        }
        if (!words[words.length - 1].matches(".*([!?.])")) {
            sentence++;
        }
        return sentence;
    }
    public static String getFileText(String path) throws IOException {
            return  new String(Files.readAllBytes(Paths.get(path)));
    }

    public static int autoReadabiltyIndex(double character, double sentences, double words) {
        double score =  4.71 * (character / words) + 0.5 * (words / sentences) - 21.43;
        return buildString("ARI", score);
    }

    public static int getAgeGroup(int score) {
        switch (score) {
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 9;
            case 4:
                return 10;
            case 5:
                return 11;
            case 6:
                return 12;
            case 7:
                return 13;
            case 8:
                return 14;
            case 9:
                return 15;
            case 10:
                return 16;
            case 11:
                return 17;
            case 12:
                return 18;
            case 13:
                return 24;
            case 14:
                return 25;
            default: return 0;
        }
    }
}
