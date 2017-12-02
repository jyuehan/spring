package com.defense.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 代理信息
 */

@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String sourceId;

    private String ip;

    private String port;

    private boolean isConnected;

    private boolean isAttacked;

    private boolean isHip;

    private String bind; // 与拓扑图的位置绑定

    private double cpuUsage;

    private double disUsage;

    private double memeryUsage;

    private double netUpRate; // 网络上传速率

    private double netDownRate; // 网络下载速率

    @CreatedDate
    private Date createdTime;

    private Date updatedTime;

    @OneToMany(mappedBy = "agent")
    private Set<Policy> policies = new HashSet<>();

    @PrePersist
    void createdAt() {
        this.createdTime = new Date();
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean getIsAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
    }

    public boolean getIsHip() {
        return isHip;
    }

    public void setHip(boolean hip) {
        isHip = hip;
    }

    public String getBind() {
        return bind;
    }

    public void setBind(String bind) {
        this.bind = bind;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getDisUsage() {
        return disUsage;
    }

    public void setDisUsage(double disUsage) {
        this.disUsage = disUsage;
    }

    public double getMemeryUsage() {
        return memeryUsage;
    }

    public void setMemeryUsage(double memeryUsage) {
        this.memeryUsage = memeryUsage;
    }

    public double getNetUpRate() {
        return netUpRate;
    }

    public void setNetUpRate(double netUpRate) {
        this.netUpRate = netUpRate;
    }

    public double getNetDownRate() {
        return netDownRate;
    }

    public void setNetDownRate(double netDownRate) {
        this.netDownRate = netDownRate;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<Policy> policies) {
        this.policies = policies;
    }

    public void addPolicy(Policy policy) {
        this.policies.add(policy);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", sourceId='" + sourceId + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", isConnected=" + isConnected +
                ", isAttacked=" + isAttacked +
                ", isHip=" + isHip +
                ", bind='" + bind + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", disUsage=" + disUsage +
                ", memeryUsage=" + memeryUsage +
                ", netUpRate=" + netUpRate +
                ", netDownRate=" + netDownRate +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", policies=" + policies +
                '}';
    }
}
