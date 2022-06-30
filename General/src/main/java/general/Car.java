package general;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {
    @Serial
    private static final long serialVersionUID = 7404L;
    private String name; //Поле не может быть null
}
