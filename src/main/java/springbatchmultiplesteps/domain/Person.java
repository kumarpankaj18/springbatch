package springbatchmultiplesteps.domain;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {
    private String lastName;
    private String firstName;

    private String firstLastName;

    private String lastFirstName;
}
