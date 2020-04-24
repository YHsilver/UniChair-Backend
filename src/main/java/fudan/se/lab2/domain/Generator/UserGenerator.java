package fudan.se.lab2.domain.Generator;

import fudan.se.lab2.domain.User;

/*
* This is a class to generate random users.
* All username will start with "G_".
* */
public class UserGenerator {
    private final static String PERFIX = "G";
    private final static String DEFAULT_PASS = "pass";
    private static int randomUserNum = 0;


    /*    public User(String username, String password, String fullName, String unit, String area, String email) {*/
    public static User getRandomUser(){
        //String username = PERFIX + randomUserNum + "_" + StringGenerator.getRandomString();
        String username = PERFIX + randomUserNum + "_" + "user";
        String password = DEFAULT_PASS;
        //String password = StringGenerator.getRandomString(12, 20, true, true, true);
        String fullName = StringGenerator.getRandomString();
        String unit = StringGenerator.getRandomString() + " Unit";
        String area = StringGenerator.getRandomString() + " Area";
        String email = StringGenerator.getRandomString(6, 10, true, false, false) + "@Generator.com";
        randomUserNum++;
        return new User(username, password, fullName, unit, area, email);
    }


}
