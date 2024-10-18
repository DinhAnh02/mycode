package vn.eledevo.vksbe.dto.request;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationSearch {
    String name;
    String code;
    String address;
    LocalDate fromDate;
    LocalDate toDate;
}
