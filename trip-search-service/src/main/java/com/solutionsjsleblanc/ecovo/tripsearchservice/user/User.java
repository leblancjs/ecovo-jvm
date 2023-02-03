package com.solutionsjsleblanc.ecovo.tripsearchservice.user;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class User {
    private Long id;
    private String firstName;
    private String lastName;
}
