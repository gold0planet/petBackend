package com.dlnu.pet.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private int code;        // 状态码
    private String message;  // 消息
    private Object data;          // 返回的数据

    // 成功时的静态工厂方法
    public static Result success(Object data) {
        return new Result(200, "Success", data);
    }

    // 失败时的静态工厂方法
    public static Result error(int code, String message) {
        return new Result(code, message, null);
    }
}
