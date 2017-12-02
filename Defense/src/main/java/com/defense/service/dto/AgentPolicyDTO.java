package com.defense.service.dto;

import java.util.List;

public class AgentPolicyDTO {

    private String sourceId;

    private List<ClientPolicyDTO> clientPolicyDTOs;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public List<ClientPolicyDTO> getClientPolicyDTOs() {
        return clientPolicyDTOs;
    }

    public void setClientPolicyDTOs(List<ClientPolicyDTO> clientPolicyDTOs) {
        this.clientPolicyDTOs = clientPolicyDTOs;
    }
}
