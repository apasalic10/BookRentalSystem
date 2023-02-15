package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.MemberManager;
import ba.unsa.etf.rpr.controllers.SignUpController;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
     * Mocking test.
     * Checks email method by mocking user in database.
     * @throws Exception
     */
    @Test
    void checkEmailTest() throws Exception{
        MemberManager mockMem = Mockito.mock(MemberManager.class);

        Mockito.when(mockMem.getById(0)).thenReturn(new Member("Member","Member","member123","member456","m","060316949"));
        Assertions.assertFalse(SignUpController.checkEmail(mockMem.getById(0).getEmail()));
        Mockito.when(mockMem.getById(0)).thenReturn(new Member("Member","Member","member123","member456","member@gmail.com","060316949"));
        Assertions.assertTrue(SignUpController.checkEmail(mockMem.getById(0).getEmail()));
    }

    /**
     * Mocking test.
     * Checks phone number method by mocking user in database.
     * @throws Exception
     */
    @Test
    void checkPhoneNumberTest() throws Exception{
        MemberManager mockMem = Mockito.mock(MemberManager.class);

        Mockito.when(mockMem.getById(0)).thenReturn(new Member("Member","Member","member123","member456","member@gmail.com","adafaffa"));
        Assertions.assertFalse(SignUpController.checkPhoneNumber(mockMem.getById(0).getPhoneNumber()));
        Mockito.when(mockMem.getById(0)).thenReturn(new Member("Member","Member","member123","member456","member@gmail.com","062128421"));
        Assertions.assertTrue(SignUpController.checkPhoneNumber(mockMem.getById(0).getPhoneNumber()));
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


    /**
     * Delete member after test
     * @throws BookException
     */
    private void deleteMember() throws BookException {
        memberManager.delete(memberManager.getByUsername("niko123").getId());
    }

}
