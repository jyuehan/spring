package com.defense.controller;

import com.defense.data.PolicyVO;
import com.defense.entity.Policy;
import com.defense.repository.AgentRepo;
import com.defense.repository.PolicyRepo;
import com.defense.service.PolicyService;
import com.defense.service.dto.AgentPolicyDTO;
import com.defense.util.HeaderUtil;
import com.defense.util.PaginationUtil;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/policy")
public class PolicyController {

    Logger logger = LoggerFactory.getLogger(PolicyController.class);

    private final PolicyService policyService;

    private final PolicyRepo policyRepo;

    private static final String ENTITY_NAME = "policy";

    private final AgentRepo agentRepo;

    public PolicyController(PolicyService policyService, PolicyRepo policyRepo, AgentRepo agentRepo) {
        this.policyService = policyService;
        this.policyRepo = policyRepo;
        this.agentRepo = agentRepo;
    }

    /**
     * 客户端更新策略
     */
    @RequestMapping(method = RequestMethod.POST, value = "/client")
    public ResponseEntity updatePolicy(@RequestBody AgentPolicyDTO agentPolicyDTO) {
        if (agentPolicyDTO.getSourceId() == null || Strings.isEmpty(agentPolicyDTO.getSourceId())) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "sourceIdIsNull", "sourceId is null")).body(null);
        }

        if (agentPolicyDTO.getClientPolicyDTOs().size() == 0) {
            return new ResponseEntity(HttpStatus.OK);
        }
        PolicyVO policyVO = policyService.getUpdatedPolicy(agentPolicyDTO);
        return ResponseEntity.ok()
                .body(policyVO);
    }

    /**
     * 获取策略
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getPolicies(Pageable pageable) {
        Page<Policy> page = policyService.getPolicies(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/policy");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * 增加策略
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity savePolicy(@RequestBody Policy policy) {
        if (policyService.checkName(policy).equals("nameExist")) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "nameexist", "policy name exist")).body(null);
        }
        Policy result = policyRepo.save(policy);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * 修改策略
     */
    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity updatePolicy(@RequestBody Policy policy) {

        if (policyService.checkName(policy).equals("nameExist")) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "nameExist", "policy name exist")).body(null);
        }
        Policy result = policyService.updatePolicy(policy);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * 删除策略
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deletePolicy(@Param("id") Long id) {
        if (!policyRepo.exists(id)) {
            return ResponseEntity.notFound().headers(HeaderUtil.createFailureAlert("user-management", "idNotExist", "policy id not exist")).build();
        }
        logger.info("delete policy which id is {}", id);
        policyRepo.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
