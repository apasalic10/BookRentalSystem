package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class MemberDaoSQLImpl implements MemberDao{

    private Connection connection;

    public MemberDaoSQLImpl() throws IOException {
        FileReader reader = new FileReader("db.properties");
        Properties p = new Properties();
        p.load(reader);

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/" + p.getProperty("username") , p.getProperty("username"), p.getProperty("password"));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public Member getById(int id) {
        return null;
    }

    @Override
    public Member add(Member item) {
        return null;
    }

    @Override
    public Member update(Member item) {
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM Members WHERE id = ?";
        try{
            PreparedStatement stmt = this.connection.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
            stmt.setObject(1, id);
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Member> getAll() {
        return null;
    }

    @Override
    public List<Member> searchByFirstName(String firstName) {
        List<Member> members = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Members WHERE first_name = ?");
            statement.setString(1,firstName);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Member mem = new Member();
                mem.setMemberId(rs.getInt(1));
                mem.setFirstName(rs.getString(2));
                mem.setLastName(rs.getString(3));
                mem.setUsername(rs.getString(4));
                mem.setPassword(rs.getString(5));
                mem.setEmail(rs.getString(6));
                mem.setPhoneNumber(rs.getString(7));

                members.add(mem);
            }

            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public List<Member> searchByLastName(String lastName) {

        List<Member> members = new LinkedList<>();

        try{
            PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Members WHERE last_name = ?");
            statement.setString(1,lastName);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                Member mem = new Member();
                mem.setMemberId(rs.getInt(1));
                mem.setFirstName(rs.getString(2));
                mem.setLastName(rs.getString(3));
                mem.setUsername(rs.getString(4));
                mem.setPassword(rs.getString(5));
                mem.setEmail(rs.getString(6));
                mem.setPhoneNumber(rs.getString(7));

                members.add(mem);
            }

            rs.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return members;
    }
}
