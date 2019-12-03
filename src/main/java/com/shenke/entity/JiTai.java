package com.shenke.entity;

import javax.persistence.*;

/**
 * 机台实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "t_jitai")
public class JiTai {

    @Id
    @GeneratedValue
    private Integer id;//id

    @Column(length = 50)
    private String name;//机台名称

    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;//班组

    @ManyToOne
    @JoinColumn(name = "clerkId")
    private Clerk clerk;//员工

    private Boolean weight;//重量

    private Boolean color;//颜色

    private Boolean dao;//剖刀

    private Boolean clientName;//客户

    private Boolean dateInProduced;//生产日期

    @Column(length = 500)
    private String remark;//备注

    public Boolean getDateInProduced() {
        return dateInProduced;
    }

    public void setDateInProduced(Boolean dateInProduced) {
        this.dateInProduced = dateInProduced;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Clerk getClerk() {
        return clerk;
    }

    public void setClerk(Clerk clerk) {
        this.clerk = clerk;
    }

    public Boolean getWeight() {
        return weight;
    }

    public void setWeight(Boolean weight) {
        this.weight = weight;
    }

    public Boolean getColor() {
        return color;
    }

    public void setColor(Boolean color) {
        this.color = color;
    }

    public Boolean getDao() {
        return dao;
    }

    public void setDao(Boolean dao) {
        this.dao = dao;
    }

    public Boolean getClientName() {
        return clientName;
    }

    public void setClientName(Boolean clientName) {
        this.clientName = clientName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "JiTai{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", group=" + group +
                ", clerk=" + clerk +
                ", weight=" + weight +
                ", color=" + color +
                ", dao=" + dao +
                ", clientName=" + clientName +
                ", dateInProduced=" + dateInProduced +
                ", remark='" + remark + '\'' +
                '}';
    }
}
