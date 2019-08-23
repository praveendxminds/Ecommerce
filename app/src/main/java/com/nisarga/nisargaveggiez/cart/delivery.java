package com.nisarga.nisargaveggiez.cart;


public class delivery
{
    private String week;
    private String days;

    public delivery(String week, String days)
    {
        week = week;
        days = days;
    }

    public String getweek()
    {
        return week;
    }

    public String getday()
    {
        return days;
    }

    public String toString()
    {
        return week;
    }
}
