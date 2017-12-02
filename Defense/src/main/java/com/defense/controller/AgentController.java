package com.defense.controller;

import com.defense.entity.Agent;
import com.defense.entity.Policy;
import com.defense.repository.AgentRepo;
import com.defense.repository.PolicyRepo;
import com.defense.service.AgentService;
import com.defense.util.HeaderUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/agent")
public class AgentController {

    private final AgentService agentService;

    private final AgentRepo agentRepo;

    private final PolicyRepo policyRepo;

    public AgentController(AgentService agentService, AgentRepo agentRepo, PolicyRepo policyRepo) {
        this.agentService = agentService;
        this.agentRepo = agentRepo;
        this.policyRepo = policyRepo;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/info")
    public ResponseEntity uploadInfo(@RequestBody Agent agent) {
        System.out.println("111111 " + agent.getSourceId());
        if (agent.getSourceId() == null || Strings.isEmpty(agent.getSourceId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "sourceIdIsNull", "sourceId is null")).body(null);
        }
        agentService.update(agent);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/policy")
    public ResponseEntity addPolicy(@Param("agentId") Long agentId, @Param("policyId") Long policyId) {
        Agent agent = agentRepo.findOne(agentId);
        Policy policy = policyRepo.findOne(policyId);
        if (agent == null || policy == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "paramError", "param error")).body(null);
        }
        if (agentService.checkPolicyType(agent, policy)) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "policyTypeExist", "policy type exist")).body(null);
        }
        agentService.addPolicy(agent, policy);
        return new ResponseEntity(HttpStatus.OK);
    }
}
