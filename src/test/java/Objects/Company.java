package Objects;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Company {
    private String name;
    private int numberOfEmployees;
    private int numberOfCustomers;
    private String country;
}
