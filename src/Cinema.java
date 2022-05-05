import java.util.Scanner;

public class Cinema {

    static final int ZERO = 0;
    static final int ONE = 1;
    static final int TWO = 2;
    static final int THREE = 3;
    static final int TEN = 10;
    static final int EIGHT = 8;
    static final int SIXTY = 60;
    static final int HUNDRED = 100;
    static final Scanner scanner = new Scanner(System.in);
    static boolean exit = false;
    static int rowsNum = 0;
    static int seatInRow = 0;
    static int capacity = 0;
    static final String[] MENU = {"1. Show the seats",
            "2. Buy a ticket","3. Statistics", "0. Exit"};
    static String[][] seatArrgment;

    public static void main(String[] args) {

        runCinema();
    }

    static void runCinema() {

        getSeatArrangement();

        do {
            menuChoice();
        } while (!exit);

    }

    static String[][] getSeatArrangement() {

        System.out.println("Enter the number of rows:");
        int inputRows = scanner.nextInt();

        System.out.println("Enter the number of seats in each row:");
        int inputSeatsPerRow = scanner.nextInt();

        // get total sitting capacity for later use
        capacity = inputRows * inputSeatsPerRow;

        // add 1 to row and seat per row to account for the printout
        int rows = inputRows + ONE;
        int  seatsPerRow= inputSeatsPerRow + ONE;
        seatArrgment = new String[rows][seatsPerRow];

        // fill the unused seats with "S"
        for (int i = ZERO; i < seatArrgment.length; i++) {
            for (int j = ZERO; j < seatArrgment[i].length; j++) {

                seatArrgment[i][j] = "S";
            }
        }
        return seatArrgment;
    }

    static void menuChoice() {
        for (String option : MENU) {
            System.out.println(option);
        }
        int option = scanner.nextInt();

        switch(option) {
            case ONE:
                displaySeats();
                break;
            case TWO:
                buyTicket();
                break;
            case THREE:
                Statistics();
                break;
            case ZERO:
                exit = true;
                break;

        }
    }

    static void buyTicket() {

        boolean purchased = false;
        boolean broke = false;

        do {
            System.out.println("Enter a row number:");
            rowsNum = scanner.nextInt();

            System.out.println("Enter a seat number in that row:");
            seatInRow = scanner.nextInt();
            // for numbers out of array range try again
            if (rowsNum < ONE || rowsNum >
                    seatArrgment.length - 1 && seatInRow < ONE ||
                    seatInRow > seatArrgment[0].length - 1) {
                System.out.println("Wrong input!");
                continue;
            } else {

                for (int i = ZERO; i < seatArrgment.length; i++) {
                    // used to break out of outer for loop
                    if (broke) {
                        broke = false;
                        break;
                    }
                    for (int j = ZERO; j < seatArrgment[i].length; j++) {
                        // if seat is booked and so "B" break
                        // out from internal loop
                        if (i == rowsNum && j == seatInRow) {
                            if (seatArrgment[i][j] == "B") {
                                System.out.println("That ticket has already " +
                                        "been purchased!");
                                broke = true;
                                break;
                                // book seat by changing value to "B"
                                // and print ticket price
                            } else {
                                seatArrgment[i][j] = "B";
                                System.out.format("Ticket price: $%d \n",
                                        ticketPrice(rowsNum));
                                // doesn't get used because return
                                // exits the method call
                                purchased = true;
                                // exit the method call
                                return;
                            }
                        }
                    }
                }
            }
        }while(!purchased);
    }

    static int ticketPrice(int row) {

        int ticketPrice = ZERO;
        // conditions given for the prices of each seat
        if (capacity <= SIXTY) {
            ticketPrice = TEN;

        } else if ((seatArrgment.length - ONE) % TWO == ZERO) {
            if (row <= (seatArrgment.length - ONE)/TWO) {
                ticketPrice = TEN;

            } else {
                ticketPrice = EIGHT;
            }
        } else {
            if (row <= (seatArrgment.length - ONE)/TWO) {
                ticketPrice = TEN;
            } else {
                ticketPrice = EIGHT;
            }
        }

        return ticketPrice;
    }

    static void  Statistics(){

        int numPurchasedTickets = 0;
        for (int i = ZERO; i < seatArrgment.length; i++) {
            for (int j = ZERO; j < seatArrgment[i].length; j++) {
                // "B" represents purchased tickets
                if (seatArrgment[i][j] == "B") {
                    numPurchasedTickets++;
                }
            }
        }
        double percentage = (numPurchasedTickets / (double) capacity)
                * HUNDRED;

        System.out.printf("Number of purchased tickets: %d \n",
                numPurchasedTickets);
        System.out.printf("Percentage: %.2f%% \n", percentage);
        System.out.printf("Current income: $%d \n", currentIncome());
        System.out.printf("Total income: $%d \n", totTicketPrice());
    }

    static void displaySeats() {
        System.out.println("Cinema:");
        for (int i = ZERO; i < seatArrgment.length; i++) {
            for (int j = ZERO; j < seatArrgment[i].length; j++) {

                if (i == ZERO && j == ZERO) {
                    System.out.print("  ");
                    continue;
                    // display 1 - number of seatsPerRow horizontally
                } else if (i == ZERO){
                    System.out.print(j + " ");
                    continue;
                }// display 1 - number of rows vertically
                if (i > ZERO && j == ZERO) {
                    System.out.print(i + " ");
                    // display "S" for unbooked seats
                } else if (i > ZERO) {
                    System.out.print(seatArrgment[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    static int currentIncome(){

        int currentIncome = 0;
        for (int i = ZERO; i < seatArrgment.length; i++) {
            for (int j = ZERO; j < seatArrgment[i].length; j++) {
                // add the ticket prices for all the booked seats
                if (seatArrgment[i][j] == "B") {
                    currentIncome += ticketPrice(i);
                }
            }
        }
        return currentIncome;
    }

    static int totTicketPrice() {

        int row = seatArrgment.length - 1;
        int seatPerRow = seatArrgment[0].length - 1;
        int halfCapacity = capacity / TWO;
        int totTicketPrice = ZERO;

        if (capacity <= SIXTY) {
            totTicketPrice = TEN * capacity;
        } else if (row % TWO == ZERO) {
            totTicketPrice = (TEN * halfCapacity) + (EIGHT * halfCapacity);
        } else {
            int frontRows = row / TWO;
            int backRows = row - frontRows;
            totTicketPrice = (TEN * frontRows * seatPerRow) + (EIGHT * backRows * seatPerRow);
        }
        return totTicketPrice;
    }
}