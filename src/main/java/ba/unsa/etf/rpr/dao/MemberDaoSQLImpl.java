package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MemberDaoSQLImpl extends AbstractDao<Member> implements MemberDao{
    private static MemberDaoSQLImpl instance = null;

    MemberDaoSQLImpl() {
        super("Members");
    }



    public static MemberDaoSQLImpl getInstance(){
        if(instance==null)
            instance = new MemberDaoSQLImpl();
        return instance;
    }

    public static void removeInstance(){
        if(instance!=null)
            instance=null;
    }


    @Override
    public Member row2object(ResultSet rs) throws BookException {
        try{
            Member mem = new Member();
            mem.setId(rs.getInt(1));
            mem.setFirstName(rs.getString(2));
            mem.setLastName(rs.getString(3));
            mem.setUsername(rs.getString(4));
            mem.setPassword(rs.getString(5));
            mem.setEmail(rs.getString(6));
            mem.setPhoneNumber(rs.getString(7));

            return mem;
        }catch (SQLException e){
            throw new BookException(e.getMessage(),e);
        }

    }

    @Override
    public Map<String, Object> object2row(Member object) {
        Map<String, Object> row = new TreeMap<>();

        row.put("id", object.getId());
        row.put("first_name", object.getFirstName());
        row.put("last_name",object.getLastName());
        row.put("username",object.getUsername());
        row.put("password",object.getPassword());
        row.put("email",object.getEmail());
        row.put("phone_number",object.getPhoneNumber());

        return row;
    }


    @Override
    public List<Member> searchByFirstName(String firstName) throws BookException {
        return this.executeQuery("SELECT * FROM Members WHERE first_name = ?",firstName);
    }

    @Override
    public List<Member> searchByLastName(String lastName) throws BookException {
        return this.executeQuery("SELECT * FROM Members WHERE last_name = ?",lastName);
    }

    @Override
    public Member getByUsername(String username) throws BookException {
        return this.executeQueryUnique("SELECT * FROM Members WHERE username = ?",username);
    }
}
