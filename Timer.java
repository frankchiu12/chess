package chess;

public class Timer {

    private String minutes;
    private String seconds;

    public Timer() {
        this.minutes = "15";
        this.seconds = "00";
    }

    public String getMinutes() {
        return this.minutes;
    }

    public String getSeconds() {
        return this.seconds;
    }

    public void setMinutes(int min) {
        if (min < 10) {
            this.minutes = "0" + min;
        } else {
            this.minutes = min + "";
        }
    }

    public void setSeconds(int sec) {
        if (sec < 10) {
            this.seconds = "0" + sec;
        } else {
            this.seconds = sec + "";
        }
    }

    public String toString() {
        return this.minutes + ":" + this.seconds;
    }
}
