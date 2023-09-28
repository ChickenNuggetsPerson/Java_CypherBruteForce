package Utils;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressBar {
    private Timer timer;

    public double printInterval = 0.1;

    private double maxVal;
    private double minVal;
    public double storedVal;

    private String header;

    public static void main(String args[]) {
        ProgressBar test = new ProgressBar(0.00, 1.00, 0.00, "Test Bar");
        test.start();

        double testVal = 0.00;
        while(true) {
            testVal += 0.01;
            test.updateVal(testVal);
            try {
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (test.storedVal >= 1) {
                test.stop();
                break;
            }
        }

    }

    public ProgressBar(double minVal, double maxVal, double startVal, String header) {
        this.minVal = minVal;
        this.maxVal = maxVal;
        this.storedVal = startVal;
        this.header = header;
    }

    class PrintTask extends TimerTask {
        public void run() {
            print();
            timer.schedule(new PrintTask(), (long)(printInterval * 1000));
        }
    }

    public void start() {
        System.out.println("");
        System.out.println(this.header);
        timer = new Timer();
        timer.schedule(new PrintTask(), (long)(this.printInterval * 1000));
    }
    public void stop() {
        timer.cancel();
        System.out.println("");
    }
    public void updateVal(double newVal) { this.storedVal = newVal; }


    public void print() {
        StringBuilder printStr = new StringBuilder();

        printStr.append("[");

        double stepSize = (this.maxVal - this.minVal) / 50.00;
        boolean lastVal = true;
        for (int i = 0; i < 50; i++) {
            if ( i * stepSize < this.storedVal) {
                printStr.append("=");
            } else {
                if (lastVal) {
                    printStr.append(">");
                    lastVal = false;
                } else {
                    printStr.append(" ");
                }
            }
        }

        printStr.append("] ");
        printStr.append(Math.floor(( this.storedVal - this.minVal ) / ( this.maxVal - this.minVal ) * 1000) / 10);
        printStr.append("% \r");

        System.out.print(printStr.toString());
    }
}
