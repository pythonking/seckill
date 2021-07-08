package com.karsa.vo.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 注：因为需要通过网络传输此model，所以需要序列化
 */
@Data
public class UserVo implements Serializable {

    private Long uuid;
    private Long phone;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
