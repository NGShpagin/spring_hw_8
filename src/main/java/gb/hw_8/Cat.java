package gb.hw_8;

import gb.hw_8.aspect.RecoverException;
import gb.hw_8.aspect.Timer;
import org.springframework.stereotype.Component;

import java.lang.module.ResolutionException;
import java.util.NoSuchElementException;

@Timer
@Component
public class Cat implements Animal {

    @Override
    public void run(String place) {

    }

    @Override
    public String voice() {
        return "Meow";
    }

    @Override
    @RecoverException
    public void getSleep(Integer duration) {
        throw new ResolutionException("cat sleeping ....");
    }
}
