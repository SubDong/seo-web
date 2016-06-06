package com.groot.webmagic.seo.score.vo;

/**
 * 评分项对象
 *
 * Created by caoyi on 2014-12-25.
 */
public class ScoreItem {
    /**
     * 评分项名称
     */
    private String name;

    /**
     * 评分项代码，预留
     */
    private String code;

    /**
     * 评分值
     */
    private double score = 0.0;

    /**
     * 问题点，如果有问题，将问题记录在此域
     */
    private String problem;

    /**
     * 描述信息，如建议内容等
     */
    private String desc;

    private String total;

    private String Interpretation;

    public ScoreItem() {
    }

    public ScoreItem(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getInterpretation() {
        return Interpretation;
    }

    public void setInterpretation(String interpretation) {
        Interpretation = interpretation;
    }
}
