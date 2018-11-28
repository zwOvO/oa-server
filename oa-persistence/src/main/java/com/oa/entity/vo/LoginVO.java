package com.oa.entity.vo;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginVO {
    String status;
    String type;
    String currentAuthority;
//    {"status":"ok","type":"account","currentAuthority":"admin"}
//    {"status":"error","type":"account","currentAuthority":"guest"}
}
