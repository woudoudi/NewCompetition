package com.example.http_lib.response;

import java.util.List;

public class BetNumberGradeBean {


    /**
     * scoresStr : 10,20,50
     * level : PRI
     * scores : ["10","20","50"]
     * updateAt : 2020-03-17 17:51:13
     * rules : 初级规则
     * id : 90615530755738701
     * enterScore : 100.00
     * createAt : 2020-03-17 17:51:13
     * status : 0
     */

    private String scoresStr;
    private String level;
    private String updateAt;
    private String rules;
    private String id;
    private String enterScore;
    private String createAt;
    private int status;
    private List<String> scores;

    public String getScoresStr() {
        return scoresStr;
    }

    public void setScoresStr(String scoresStr) {
        this.scoresStr = scoresStr;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnterScore() {
        return enterScore;
    }

    public void setEnterScore(String enterScore) {
        this.enterScore = enterScore;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }
}
