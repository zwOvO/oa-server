package com.oa.entity.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class baseListQuery {
    private String openId;
    private String username;
    private String month;
    private String sorter;
    private int currentPage = 1;
    private int pageSize = 10;
}
