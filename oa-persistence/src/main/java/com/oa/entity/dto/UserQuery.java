package com.oa.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserQuery extends baseListQuery {
    private String nickname;
    private int status;
    private int gender;
}
