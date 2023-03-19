package cn.com.pojo;

import java.io.Serializable;

public enum MessageType implements Serializable {
    JOIN,QUIT,NOTICE,MESSAGE,ERROR
    //加入,退出,通知,消息,错误
}
