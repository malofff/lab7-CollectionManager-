package general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Coordinates implements Serializable {
    @Serial
    private static final long serialVersionUID = -23406L;
    private long x;
    private long y; //Максимальное значение поля: 74
}
