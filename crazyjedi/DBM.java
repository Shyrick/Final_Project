package crazyjedi;

import java.util.List;

/**
 * Created by Vlad on 03.05.2017.
 */
public interface DBM {
    void dumpDB(List<?> elts);
    List<?> readDB();
}
