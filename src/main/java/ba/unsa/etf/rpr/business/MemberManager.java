package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

/**
 * Business Logic Layer for Member
 *
 * @author Pasalic Almedin
 */
public class MemberManager {

    public List<Member> getAll() throws BookException{
        return DaoFactory.memberDao().getAll();
    }

    public void delete(int id) throws BookException{
        DaoFactory.memberDao().delete(id);
    }

    public Member getById(int quoteId) throws BookException{
        return DaoFactory.memberDao().getById(quoteId);
    }

    public void update(Member q) throws BookException{
        DaoFactory.memberDao().update(q);
    }

    public Member add(Member q) throws BookException{
        return DaoFactory.memberDao().add(q);
    }

    public List<Member> searchByFirstName(String firstName) throws BookException{
        return DaoFactory.memberDao().searchByFirstName(firstName);
    }


    public List<Member> searchByLastName(String lastName) throws BookException{
        return DaoFactory.memberDao().searchByLastName(lastName);
    }


    public Member getByUsername(String username) throws BookException{
        return DaoFactory.memberDao().getByUsername(username);
    }
}
