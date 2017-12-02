package com.defense.data;

import com.defense.entity.Policy;

import java.util.List;

public class PolicyVO {

    private List<Long> deletedPolicy;

    private List<Policy> updatedPolicy;

    public List<Long> getDeletedPolicy() {
        return deletedPolicy;
    }

    public void setDeletedPolicy(List<Long> deletedPolicy) {
        this.deletedPolicy = deletedPolicy;
    }

    public void addDeletedPolicy(Long deletedPolicy) {
        this.deletedPolicy.add(deletedPolicy);
    }

    public List<Policy> getUpdatedPolicy() {
        return updatedPolicy;
    }

    public void setUpdatedPolicy(List<Policy> updatedPolicy) {
        this.updatedPolicy = updatedPolicy;
    }

    public void addUpdatedPolicy(Policy policy) {
        this.updatedPolicy.add(policy);
    }
}
