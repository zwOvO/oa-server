package com.oa.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordQuery extends baseListQuery {
    private String username;
    private String openId;
}
