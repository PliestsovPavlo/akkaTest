package job;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;
import job.types.FBJob;
import job.types.InstaJob;
import job.types.Job;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taras on 22.03.18.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("jobsSystem");
        ActorRef parentActor = actorSystem.actorOf(Props.create(ParentActor.class), "parentActor");
        Jedis j = new Jedis("localhost", 6379);
        ObjectMapper mapper = new ObjectMapper();
//        List<Job> jobs = new ArrayList<>();
        List<InstaJob> instaJobs = new ArrayList<>();
        List<FBJob> fbJobs = new ArrayList<>();

        long llen = j.llen("jobs");
        for (int i = 0; i < llen; i++) {
//            jobs.add(mapper.readValue(j.lpop("jobs"), Job.class));
            JSONObject json = new JSONObject(j.lpop("jobs"));
            if ("Instagram post".equals(json.getString("type")))
            {
                instaJobs.add(mapper.readValue(json.toString(), InstaJob.class));
            }else
            {
                fbJobs.add(mapper.readValue(json.toString(), FBJob.class));
            }

        }

//        for (int i = 0; i < 10; i++) {
//            jobs.add(new Job("JOB NUMBER: " + i));
//        }


        // sending to flow will be changed to stream
//        for (int i = 0; i < jobs.size(); i++) {
//            System.out.println("RUNNING JOB " + jobs.get(i).getId());
//            parentActor.tell(jobs.get(i), ActorRef.noSender());
//        }

        for (Job job : instaJobs)
        {
            // sending jobs to actor threads (start flow)
            parentActor.tell(job, ActorRef.noSender());
        }
        for (Job job : fbJobs)
        {
            // sending jobs to actor threads (start flow)
            parentActor.tell(job, ActorRef.noSender());
        }
        actorSystem.terminate();
        j.close();
    }
}
