package com.weathertracking.sky_sage.dao.forecast;

import java.util.List;

public class ForecastData {
    private City city;
    private long cnt;
    private String cod;
    private long message;
    private List<ListEntry> list;

    public City getCity() { return city; }
    public void setCity(City value) { this.city = value; }

    public long getCnt() { return cnt; }
    public void setCnt(long value) { this.cnt = value; }

    public String getCod() { return cod; }
    public void setCod(String value) { this.cod = value; }

    public long getMessage() { return message; }
    public void setMessage(long value) { this.message = value; }

    public List<ListEntry> getList() {
        return list;
    }

    public void setList(List<ListEntry> list) {
        this.list = list;
    }
}
