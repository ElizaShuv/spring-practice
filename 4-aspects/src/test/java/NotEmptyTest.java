import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.config.ProjectConfig;
import ru.sber.service.ArgsMethodService;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProjectConfig.class })
public class NotEmptyTest {

    private static final String EMPTY_STRING = "";
    private static final String NOT_EMPTY_STRING = "String";
    private static final String NULL_STRING = null;
    private static final Set<Integer> EMPTY_SET = Set.of();
    private static final Set<Integer> NOT_EMPTY_SET = Set.of(1,2,3);
    private static final List<Integer> EMPTY_LIST = List.of();
    private static final List<Integer> NOT_EMPTY_LIST = List.of(1,2,3);

    @Autowired
    private ArgsMethodService argsMethodService;


    @Test
    public void testStringArgsMethod_NullArguments() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.stringArgsMethod(NULL_STRING, NOT_EMPTY_STRING);
        });

        assertThat(exception.getMessage(), containsString("Аргумент не может быть null"));
    }


    @Test
    public void testStringArgsMethod_EmptyStringArgument() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.stringArgsMethod(NOT_EMPTY_STRING, EMPTY_STRING);
        });
        assertThat(exception.getMessage(), containsString("Аргумент не может быть пустым"));
    }

    @Test
    public void testCollectionArgsMethod_EmptyCollectionArgument() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.collectionArgsMethod(EMPTY_SET);
        });
        assertThat(exception.getMessage(), containsString("Аргумент не может быть пустым"));
    }

    @Test
    public void testBothArgsMethod_NullArguments() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.bothArgsMethod(NOT_EMPTY_LIST, NULL_STRING);
        });
        assertThat(exception.getMessage(), containsString("Аргумент не может быть null"));
    }

    @Test
    public void testBothArgsMethod_EmptyCollectionArgument() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.bothArgsMethod(EMPTY_LIST, NOT_EMPTY_STRING);
        });
        assertThat(exception.getMessage(), containsString("Аргумент не может быть пустым"));
    }

    @Test
    public void testBothArgsMethod_EmptyStringArgument() {
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            argsMethodService.bothArgsMethod(NOT_EMPTY_LIST, EMPTY_STRING);
        });
        assertThat(exception.getMessage(), containsString("Аргумент не может быть пустым"));
    }

    @Mock
    private Logger logger;
    @BeforeEach
    public void setup() {
        this.logger = mock(Logger.class);
        argsMethodService.setLogger(logger);

    }

    @Test
    public void testStringArgsMethod_ValidArguments() {
        Assertions.assertDoesNotThrow(() -> {
            argsMethodService.stringArgsMethod(NOT_EMPTY_STRING, NOT_EMPTY_STRING);
        });
        verify(logger).info("Заданы строки");
    }

    @Test
    public void testCollectionArgsMethod_ValidArguments() {
        Assertions.assertDoesNotThrow(() -> {
            argsMethodService.collectionArgsMethod(NOT_EMPTY_SET);
        });
        verify(logger).info("Задана коллекция");
    }

    @Test
    public void testBothArgsMethod_ValidArguments() {
        Assertions.assertDoesNotThrow(() -> {
            argsMethodService.bothArgsMethod(NOT_EMPTY_LIST, NOT_EMPTY_STRING);
        });
        verify(logger).info("Задана коллекция и строка");
    }

}
