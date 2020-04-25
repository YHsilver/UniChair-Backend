package fudan.se.lab2.domain.Generator;

import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;


class GeneratorTest {

    Set<User> userSet = new HashSet<>();
    Set<Conference> conferenceSet = new HashSet<>();

    @Test
    void testGetRandomUser(){
        for(int i = 0; i < 100; i++){
            User randomUser = UserGenerator.getRandomUser();
            userSet.add(randomUser);
            System.out.println(randomUser.toString());
        }
    }

    @Test
    void testGetRandomConference(){
        testGetRandomUser();
        System.out.println("-------------------------------------------");
        User[] users = new User[userSet.size()];
        userSet.toArray(users);
        for(int i = 0; i < 100; i++){
            Conference randomConference = ConferenceGenerator.getRandomConference(users[(int)(Math.random() * users.length)]);
            conferenceSet.add(randomConference);
            System.out.println(randomConference.toString());
        }
    }



}
