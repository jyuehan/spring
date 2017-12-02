import com.defense.MainApplication;
import com.defense.entity.Agent;
import com.defense.entity.Policy;
import com.defense.repository.AgentRepo;
import com.defense.repository.PolicyRepo;
import com.defense.service.AgentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class AgentServiceTest {
    @Autowired
    private AgentRepo agentRepo;

    @Autowired
    private PolicyRepo policyRepo;

    @Autowired
    private AgentService agentService;

    @Before
    public void setup() {
        policyRepo.deleteAll();
        agentRepo.deleteAll();
    }

    @Test
    public void test() {
        assertThat(agentRepo.count()).isEqualTo(0);

        Policy policy1 = new Policy();
        policy1.setType("1");
        policy1.setName("123");
        policy1.setContent("test");
        policyRepo.save(policy1);

        Policy policy2 = new Policy();
        policy2.setType("2");
        policy2.setName("123");
        policy2.setContent("test");
        policyRepo.save(policy2);

        Agent agent = new Agent();
        agent.setSourceId("test1");
        agent.setIp("210.28.131.1");
        agent.setPort("1234");
        agent.addPolicy(policy1);
        agent.addPolicy(policy2);
        agentRepo.save(agent);

        List<String> types = agentService.getPolicyTypes(agent);
        assertThat(types.size()).isEqualTo(2);
    }
}
