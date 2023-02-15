package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberManagerTest {
    MemberManager memberManager = new MemberManager();

    /**
     * Test getByUsername method from MemberManager class
     * @throws BookException
     */
    @Test
    void getByUsername() throws BookException{
        memberManager.add(createMember());

        Assertions.assertEquals("Niko",memberManager.getByUsername("niko123").getFirstName());

        deleteMember();
    }

    /**
     * Test delete method from MemberManager
     * @throws BookException
     */
    @Test
    void deleteTest() throws BookException{
        memberManager.add(createMember());

        memberManager.delete(memberManager.getByUsername("niko123").getId());

        List<Member> list = memberManager.getAll();
        boolean isValid = true;

        for(Member member : list){
            if(member.getUsername().equals("niko123")){
                isValid = false;
            }
        }

        Assertions.assertTrue(isValid);

    }

    /**
     * Test getById method from MemberManager class
     * @throws BookException
     */
    @Test
    void getById() throws BookException{
        Assertions.assertEquals("Almedin",memberManager.getById(1).getFirstName());
    }


    /**
     * Create member for tests
     * @throws BookException
     */
    private Member createMember() throws BookException {
        Member mem = new Member();

        mem.setFirstName("Niko");
        mem.setLastName("Nikic");
        mem.setUsername("niko123");
        mem.setPassword("niko1234");
        mem.setEmail("niko@gmail.com");
        mem.setPhoneNumber("060-319/588");

        return mem;
    }

    private void deleteMember() throws BookException {
        memberManager.delete(memberManager.getByUsername("niko123").getId());
    }

}
