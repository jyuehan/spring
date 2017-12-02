package com.defense.service;

import com.defense.entity.Agent;
import com.defense.entity.Policy;
import com.defense.repository.AgentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AgentService {
    Logger logger = LoggerFactory.getLogger(AgentService.class);

    private final AgentRepo agentRepo;

    public AgentService(AgentRepo agentRepo) {
        this.agentRepo = agentRepo;
    }

    /**~
     * 更新存储代理信息
     */
    public Agent update(Agent agent) {
        logger.info("agent info : {}", agent.toString());
        Agent agent1 = agentRepo.findBySourceId(agent.getSourceId());
        if (agent1 != null) {
            agent1.setUpdatedTime(new Date());
            if (agent.getIp() != null) {
                agent1.setIp(agent.getIp());
            }
            if (agent.getPort() != null) {
                agent1.setPort(agent.getIp());
            }
            agent1.setConnected(agent.getIsConnected());
            agent1.setAttacked(agent.getIsAttacked());
            agent1.setCpuUsage(agent.getCpuUsage());
            agent1.setDisUsage(agent.getDisUsage());
            agent1.setMemeryUsage(agent.getMemeryUsage());
            agent1.setHip(agent.getIsHip());
            agent1.setNetDownRate(agent.getNetDownRate());
            agent1.setNetUpRate(agent.getNetUpRate());
            return agentRepo.save(agent1);
        }
        return agentRepo.save(agent);
    }

    public void addPolicy(Agent agent, Policy policy) {
        agent.addPolicy(policy);
        agentRepo.save(agent);
    }

    /**
     * 判断代理中是否已经存在相同 type 的策略
     * @param agent
     * @param policy
     * @return
     */
    public boolean checkPolicyType(Agent agent, Policy policy) {
        List<String> types = getPolicyTypes(agent);
        if (types.contains(policy.getType())) {
            return true;
        }
        return false;
    }

    /**
     * 获取 agent 下的所有策略类型
     * @param agent
     * @return
     */
    public List<String> getPolicyTypes(Agent agent) {
        List<String> types = new ArrayList<>();
        for (Policy p : agent.getPolicies()) {
            types.add(p.getType());
        }
        return types;
    }
}
