package kate.db.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private BigDecimal weight;
    private BigDecimal height;
}
