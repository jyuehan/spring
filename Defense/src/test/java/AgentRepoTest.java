import com.defense.MainApplication;
import com.defense.entity.Agent;
import com.defense.repository.AgentRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AgentRepoTest {

    @Autowired
    private AgentRepo agentRepo;


    @Before
    public void setup() {
        agentRepo.deleteAll();
    }

    @Test
    public void test() {
        assertThat(agentRepo.count()).isEqualTo(0);
        Agent agent = new Agent();
        agent.setSourceId("test1");
        agent.setIp("210.28.131.1");
        agent.setPort("1234");
        agentRepo.save(agent);
        assertThat(agentRepo.count()).isEqualTo(1);
        assertThat(agentRepo.findBySourceId("test1")).isNotNull();
    }
}
