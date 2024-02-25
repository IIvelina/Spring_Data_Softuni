package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


//RetentionPolicy.RUNTIME -> да е валидно по време на изпълнение

@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    //име на таблицата
    String name();
}
