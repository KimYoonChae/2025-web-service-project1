package dao;

import model.MenuItem;

public interface ICRUD {
    public int add(MenuItem item);
    public int update(MenuItem item);
    public int delete(MenuItem item);
}
