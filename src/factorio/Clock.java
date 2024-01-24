package factorio;

public class Clock {
    private int hours;
    private int minutes;
    private int seconds;

    public Clock(int hours, int minutes, int seconds) {
        this.hours = hours % 24;
        this.minutes = minutes % 60;
        this.seconds = seconds % 60;
    }

    public String getTime() {
        return String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
    }

    public String getFutureTime(int seconds) {
        int tempSeconds = this.seconds;
        int tempMinutes = this.minutes;
        int tempHours = this.hours;

        while (seconds > 0) {
            tempSeconds++;
            seconds--;
            if (tempSeconds == 60) {
                tempMinutes++;
                tempSeconds = 0;
                if (tempMinutes == 60) {
                    tempHours++;
                    tempMinutes = 0;
                    if (tempHours == 24) {
                        tempHours = 0;
                    }
                }
            }
        }

        return String.format("%02d:%02d:%02d", tempHours, tempMinutes, tempSeconds);

    }

    public void tick() {
        this.seconds++;

        if (this.seconds == 60) {
            this.minutes++;
            this.seconds = 0;
            if (this.minutes == 60) {
                this.hours++;
                this.minutes = 0;
                if (this.hours == 24) {
                    this.hours = 0;
                }
            }
        }

    }
}
