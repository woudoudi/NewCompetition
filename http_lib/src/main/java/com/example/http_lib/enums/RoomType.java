package com.example.http_lib.enums;

public class RoomType {


    /**
     * {"OWNER":"房主","ZHU":"主观","PANG":"旁观"}
     */
    public enum RoomRoleType{
        OWNER("OWNER","房主"),
        ZHU("ZHU","主观"),
        PANG("PANG","旁观"),;

        public String key1;
        public String key2;

        RoomRoleType(String key1, String key2) {
            this.key1=key1;
            this.key2=key2;

        }
    }


    /**
     *  {"NUMBER":"数字","COMMON":"常识","UNKNOWN":"未知","FIGHT":"对抗","LIVE":"直播"}
     */
    public enum BetKindType{

        NUMBER("NUMBER","数字"),
        COMMON("COMMON","常识"),
        UNKNOWN("UNKNOWN","未知"),
        FIGHT("FIGHT","对抗"),
        LIVE("LIVE","直播"),;


        public String key1;
        public String key2;

        BetKindType(String key1, String key2) {
            this.key1=key1;
            this.key2=key2;

        }
    }

    /**
     * {"VAL":"具体数字","EVEN_ODD":"单双","KING":"王者","UNKNOWN":"未知","TAIL_DIGIT":"尾数","SEL":"选择题"}
     */
    public enum BetNumberType{

        VAL("VAL","具体数字"),
        EVEN_ODD("EVEN_ODD","单双"),
        KING("KING","王者"),
        UNKNOWN("UNKNOWN","未知"),
        TAIL_DIGIT("TAIL_DIGIT","尾数"),
        SEL("SEL","选择题"),;

        public String key1;
        public String key2;

        BetNumberType(String key1, String key2) {
            this.key1=key1;
            this.key2=key2;

        }
    }

    /**
     * {"PRI":"初级","MIDDLE":"中级","SENIOR":"高级","UNKNOWN":"未知"}
     */
    public enum RoomGradeType{

        PRI("PRI","初级"),
        MIDDLE("MIDDLE","中级"),
        SENIOR("SENIOR","高级");

        public String key1;
        public String key2;

        RoomGradeType(String key1, String key2) {
            this.key1=key1;
            this.key2=key2;

        }
    }
}
