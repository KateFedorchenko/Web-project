package kate.db.model;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Country {
    private String code;
    private String name;
    private String continent;
    private long population;
    private String localName;
    private String indepYear;
}
