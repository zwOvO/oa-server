package com.oa.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeaveQuery extends baseListQuery {
    private String openId;
    private String username;
    private int leaveType;
    private int status;
}
