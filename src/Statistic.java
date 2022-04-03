import java.util.Date;

public class Statistic {
    User user;
    Date date;

    public Statistic(User user,Date date) {
        this.user = user;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
