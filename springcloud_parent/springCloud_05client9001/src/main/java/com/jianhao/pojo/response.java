package com.jianhao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Accessors(chain = true)
public class response {
    private int code;
    private String msg;
    private Object data;
}
