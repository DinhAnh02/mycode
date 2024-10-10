package vn.eledevo.vksbe.dto.request;

import lombok.*;

import java.time.LocalDate;

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
