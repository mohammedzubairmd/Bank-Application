import java.math.BigDecimal;
import java.sql.Date;

public class transaction {
    private int accno;
    private Date date;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;

    // Getters and Setters (standard boiler plate)
    public int getAccno() { return accno; }
    public void setAccno(int accno) { this.accno = accno; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}