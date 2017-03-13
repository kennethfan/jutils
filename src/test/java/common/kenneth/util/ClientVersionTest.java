package common.kenneth.util;

import org.junit.Test;

import java.text.ParseException;

/**
 * Created by fanchunyu02 on 17/3/13.
 */
public class ClientVersionTest {

    @Test
    public void testParses() {
        testParse(null);
        testParse("");
        testParse(" ");
        testParse("1");
        testParse("1.");
        testParse("1.2");
        testParse("1.2.");
        testParse("1.2.3");
        testParse("1.2.3.");
        testParse("1.2.3-");
        testParse("1.2.3-xxx");
    }

    public void testParse(String versionString){
        System.out.println("----------------------------");
        System.out.println("start parsing: [" + versionString + "]");
        //parse
        try {
            System.out.println("parsing result = " + new ClientVersion(versionString));
        } catch (ParseException e) {
            System.out.println("parsing failed: " + e.getMessage());
        }
        //parse with default
        System.out.println("parsing result with default = "
                + new ClientVersion(versionString, "9.8.7"));
    }

    @Test
    public void testCompares(){
        testCompare("1", "1");
        testCompare("1", "1.0");
        testCompare("1", "1.2");
        testCompare("1", "0.9");
        testCompare("1.2", "1.2");
        testCompare("1.2", "1.2.0");
        testCompare("1.2", "1.2.3");
        testCompare("1.2", "1.1.9");
        testCompare("1.2.3", "1.2.3");
        testCompare("1.2.3-", "1.2.3"); //空串“大于”null，符合设计预期
        testCompare("1.2.3-", "1.2.3-");
        testCompare("1.2.3-a", "1.2.3-");
        testCompare("1.2.3-a", "1.2.3-b");
    }

    public void testCompare(String s1, String s2){
        try {
            System.out.println("----------------------------");
            System.out.println("comparing [" + s1 + "] vs [" + s2 + "] ");
            ClientVersion v1 = new ClientVersion(s1);
            ClientVersion v2 = new ClientVersion(s2);
            System.out.println("compare result=" + v1.compareTo(v2));
            System.out.println("isLaterThan result=" + v1.isLaterThan(v2));
            System.out.println("isLaterThanOrEqual result=" + v1.isLaterThanOrEqual(v2));
            System.out.println("isEarlierThan result=" + v1.isEarlierThan(v2));
            System.out.println("isEarlierThanOrEqual result=" + v1.isEarlierThanOrEqual(v2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
