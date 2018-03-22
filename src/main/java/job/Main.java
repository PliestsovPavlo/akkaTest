package job;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.fasterxml.jackson.databind.ObjectMapper;
import job.types.FBJob;
import job.types.Job;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 22.03.18.
 */
public class Main {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("jobsSystem");

        ActorRef parentActor = actorSystem.actorOf(Props.create(ParentActor.class), "parentActor");

        List<Job>jobs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            jobs.add(new Job("JOB NUMBER: " + i));
        }

        // sending to flow will be changed to stream
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println("RUNNING JOB " + jobs.get(i).getId());
            parentActor.tell(jobs.get(i), ActorRef.noSender());
        }

        actorSystem.terminate();
    }
}
