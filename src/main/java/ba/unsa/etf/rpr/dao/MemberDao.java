package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

public interface MemberDao extends Dao<Member> {
    /**
     * return list of members with the given first name
     * @param firstName - the first name by which members are searched
     * @return list of members with the given first name
     */
    public List<Member> searchByFirstName(String firstName) throws BookException;

    /**
     * return list of members with the given last name
     * @param lastName - the last name by which members are searched
     * @return list of members with the given last name
     */
    public List<Member> searchByLastName(String lastName) throws BookException;
}
