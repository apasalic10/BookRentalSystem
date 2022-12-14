package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Member;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    }

    @Override
    public List<Member> getAll() {
        return null;
    }

    @Override
    public List<Member> searchByFirstName(String firstName) {
        return null;
    }

    @Override
    public List<Member> searchByLastName(String lastName) {
        return null;
    }
}
