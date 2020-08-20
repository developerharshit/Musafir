package com.nitjsr.musafir;

public class Ticket {
    private String Src,Dst,timeStamp,TransactionId;
    private Integer TicketId,CustomerId,BusId,TransactionAmount;

    public Ticket(String src, String dst, String transactionId, int customerId, int busId, int transactionAmount) {
        Src = src;
        Dst = dst;
        TransactionId = transactionId;
        CustomerId = customerId;
        BusId = busId;
        TransactionAmount = transactionAmount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public int getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    public String getSrc() {
        return Src;
    }

    public void setSrc(String src) {
        Src = src;
    }

    public String getDst() {
        return Dst;
    }

    public void setDst(String dst) {
        Dst = dst;
    }

    public String gettimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTicketId() {
        return TicketId;
    }

    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

}
