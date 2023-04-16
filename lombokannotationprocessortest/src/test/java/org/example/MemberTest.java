package lombokannotationprocessortest.src.test.java.org.example;

import org.junit.Assert;
import org.junit.Test;

public class MemberTest {
    @Test
    public void gettersetter() {
        Member member = new Member();
        member.setName("min");
        Assert.assertEquals(member.getName(), "min");
    }
}