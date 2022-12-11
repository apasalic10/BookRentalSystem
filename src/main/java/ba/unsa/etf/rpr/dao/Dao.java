package ba.unsa.etf.rpr.dao;

import java.util.List;

/**
 * Root interface for all Dao classes
 *
 * @author Almedin Pasalic
 */
public interface Dao<Type> {
    /**
     * get entity from database with the given id
     * @param id primary key of entity in database
     * @return entity from database with the given id
     */
    public Type getById(int id);

    /**
     * add entity into database
     * @param item which will be saved to the database
     * @return saved item
     */
    public Type add(Type item);

    /**
     * update entity in database
     * @param item been to be updated. id must be populated.
     * @return updated version item
     */
    public Type update(Type item);

    /**
     * delete item with the given id
     * @param id primary key of entity which is deleted
     */
    public void delete(int id);

    /**
     * return list with all entities from database. WARNING: Very slow operation because it reads all records.
     * @return list of all entities
     */
    public List<Type> getAll();
}
