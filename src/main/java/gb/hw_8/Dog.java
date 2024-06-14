package gb.hw_8;

import gb.hw_8.aspect.RecoverException;
import gb.hw_8.aspect.Timer;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;

@Component
public class Dog implements Animal {

    @Override
    @Timer
    public void run(String place) {

    }

    @Override
    public String voice() {
        return "WOW";
    }

    @Override
    @Timer
    @RecoverException
    public void getSleep(Integer duration) {
        throw new IndexOutOfBoundsException("dog sleeping ....");
    }
}
