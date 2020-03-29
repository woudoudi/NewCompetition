package jiguang.chat.entity;

public class JPushBean {

    public static int CustomMessage = 0x100;
    public static int Notification = 0x101;
    public static int OpenNotification = 0x102;

    public int type;

    public JPushBean(int type) {
        this.type = type;
    }
}
