package gb.hw_8;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AspectRunner {

    private final List<Animal> animals;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        for (Animal animal : animals) {
            try {
                animal.getSleep(5);
                animal.voice();
                animal.run("home");
            } catch (Throwable e) {
                log.error("throwable exception - {}", e.getMessage());
            }
        }
    }
}
