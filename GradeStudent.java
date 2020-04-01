import java.util.Scanner;

class GradeStudent {

    // keep track total weight
    static int totalWeight = 0;

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        // introduction
        begin();

        final double midtermWeightScore = midterm(scanner);
        final double finaltermWeightScore = finalterm(scanner);
        final double homeworkWeightScore = homework(scanner);

        // report
        report(midtermWeightScore + finaltermWeightScore + homeworkWeightScore);
    }

    private static void begin() {
        System.out.println("This program reads exam/homework scores and reports your overall course grade.\n");
    }

    private static double midterm(final Scanner scanner) {
        return getInputOf(scanner, "Midterm:");
    }

    private static double finalterm(final Scanner scanner) {
        return getInputOf(scanner, "Final:");
    }

    private static double homework(final Scanner scanner) {
        System.out.println("Homework:");

        // retry again util valid weight
        int weight;
        do {
            weight = getWeight(scanner);
        } while (!isValidWeight(weight));

        // get assignments
        System.out.print("Number of assignments? ");
        final int totalAssignments = scanner.nextInt();

        // get score and max
        int tempTotalPoint = 0, tempMaxTotalPoint = 0;
        for (int i = 1; i <= totalAssignments; i += 1) {
            System.out.print("Assignment " + i + " score and max? ");
            tempTotalPoint += scanner.nextInt();
            tempMaxTotalPoint += scanner.nextInt();
        }

        // get attendance and calculate sections point
        System.out.print("How many sections did you attend? ");
        final int tempSectionPoint = scanner.nextInt() * 5;
        final int sectionPoint = tempSectionPoint > 30 ? 30 : tempSectionPoint;
        System.out.println("Section points = " + sectionPoint + " / 30");

        // calculate score & weight
        final int totalPoint = (tempTotalPoint > 150 ? 150 : tempTotalPoint) + sectionPoint;
        final int maxTotalPoint = (tempMaxTotalPoint > 150 ? 150 : tempMaxTotalPoint) + 30;
        final double weightScore = (double) totalPoint / maxTotalPoint * weight;

        // print report
        printScore(totalPoint, maxTotalPoint, weightScore, weight);

        return weightScore;
    }

    // check if valid weight
    private static boolean isValidWeight(final int weight) {
        final boolean isValid = weight + totalWeight == 100;
        if (!isValid) {
            // show message inform user weight is invalid
            System.out.println("Total weight must be equal to 100");
        }
        return isValid;
    }

    // print report
    private static void report(final double weightScore) {
        System.out.println("Overall percentage = " + String.format("%.1f", weightScore));
        final double minGPA = weightScore < 60 ? 0 : weightScore < 75 ? 0.7 : weightScore < 85 ? 2 : 3;
        System.out.println("Your grade will be at least = " + String.format("%.1f", minGPA));
    }

    // utility help get input from user
    private static double getInputOf(final Scanner scanner, final String term) {
        // print message
        System.out.println(term);

        // get weight
        final int weight = getWeight(scanner);

        // calculate weight
        totalWeight += weight;

        // get scores
        System.out.print("Score earned? ");
        final int score = scanner.nextInt();

        // get shifted points
        System.out.print("Were scores shifted (1=yes, 2=no)? ");
        int shiftAmount = 0;
        if (scanner.nextInt() == 1) {
            System.out.print("Shift amount? ");
            shiftAmount = scanner.nextInt();
        }

        // calculate score & weight
        final int tempTotalPoint = score + shiftAmount;
        final int totalPoint = tempTotalPoint > 100 ? 100 : tempTotalPoint;
        final double weightScore = (double) totalPoint / 100 * weight;

        printScore(totalPoint, 100, weightScore, weight);

        return weightScore;
    }

    // utility print score helper
    private static void printScore(
            final int totalPoint,
            final int maxTotalPoint,
            final double weightScore,
            final double weight) {
        System.out.println("Total points = " + totalPoint + " / " + maxTotalPoint);
        System.out.println("Weighted score = " + String.format("%.1f", weightScore) + " / " + weight);
        System.out.println();
    }

    // utility get weight data
    private static int getWeight(final Scanner scanner) {
        System.out.print("Weight (0-100)? ");
        final int weight = scanner.nextInt();
        return weight < 0 || weight > 100 ? getWeight(scanner) : weight;
    }
}
