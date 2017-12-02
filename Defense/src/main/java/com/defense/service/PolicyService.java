package com.defense.service;

import com.defense.data.PolicyVO;
import com.defense.entity.Agent;
import com.defense.entity.Policy;
import com.defense.repository.AgentRepo;
import com.defense.repository.PolicyRepo;
import com.defense.service.dto.AgentPolicyDTO;
import com.defense.service.dto.ClientPolicyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepo policyRepo;

    private final AgentRepo agentRepo;

    PolicyService(PolicyRepo policyRepo, AgentRepo agentRepo) {
        this.policyRepo = policyRepo;
        this.agentRepo = agentRepo;
    }

    /**
     * 获取策略
     */
    public Page<Policy> getPolicies(Pageable pageable) {
        return policyRepo.findAll(pageable);
    }

    public PolicyVO getUpdatedPolicy(AgentPolicyDTO agentPolicyDTO) {
        PolicyVO policyVO = new PolicyVO();
        String sourceId = agentPolicyDTO.getSourceId();
        Agent agent = agentRepo.findBySourceId(sourceId);
        if (agent == null) {
            return new PolicyVO();
        }

        List<Long> agentPolicy = new ArrayList<>();
        for (Policy policy : agent.getPolicies()) {
            agentPolicy.add(policy.getId());
        }
        /**
         * 若数据库中不存在 policyDTO 的 id, 则表示其已删除, 若数据库中的 policy 的 version 与其上传的 version 一致,
         * 则表示该策略没有变化, 无需下发
         */
        List<Long> dtoId = new ArrayList<>();
        for (ClientPolicyDTO clientPolicyDTO : agentPolicyDTO.getClientPolicyDTOs()) {
            dtoId.add(clientPolicyDTO.getId());
            Policy policy = policyRepo.findOne(clientPolicyDTO.getId());
            // 删除被删除的策略与移除出 agent 的策略
            if (policy == null || !agentPolicy.contains(clientPolicyDTO.getId())) {
                policyVO.addDeletedPolicy(clientPolicyDTO.getId());
                continue;
            }
            if (policy.getVersion().equals(clientPolicyDTO.getVersion())) {
                continue;
            }
            policyVO.addUpdatedPolicy(policy);
        }
        // 新增的策略
        agentPolicy.removeAll(dtoId);
        for (Long id : agentPolicy) {
            Policy policy = policyRepo.findOne(id);
            if (policy != null) {
                policyVO.addUpdatedPolicy(policy);
            }
        }
        return policyVO;
    }

    /**
     * 判断策略名称是否存在
     */
    public Boolean checkName(Policy policy) {
        Long id = policy.getId();
        if (id != null) {
            String name = policy.getName();
            Policy policy1 = policyRepo.findByName(name);

            if (policy1.getId()!=id) {
                return false;
            } else {
                return true;
            }

        }else {
            String name = policy.getName();
            Policy policy1 = policyRepo.findByName(name);
            if (policy1 == null) {
                return true;
            } else return false;
        }

    }

    /**
     * 更新 policy
     */

    public Policy updatePolicy(Policy policy) {
        Policy policy1 = policyRepo.findByName(policy.getName());

        policy1.setName(policy.getName());
        policy1.setAgent(policy.getAgent());
        policy1.setVersion(policy.getVersion());
        policy1.setContent(policy.getContent());
        policy1.setUpdatedTime(new Date());

        policyRepo.save(policy1);

        return policy1;
    }
}
