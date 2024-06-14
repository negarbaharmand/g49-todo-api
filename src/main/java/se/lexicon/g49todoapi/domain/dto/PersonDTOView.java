package se.lexicon.g49todoapi.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PersonDTOView {

    private Long id;
    private String name;

}
