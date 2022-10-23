package kate.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Item {
     private long id;
     private String name;
     private BigDecimal quantity;
}

