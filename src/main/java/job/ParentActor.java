package job;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import job.types.FBJob;
import job.types.InstaJob;
import job.types.Job;

/**
 * Created by taras on 22.03.18.
 */

/**
 * This is a parrent actor (e.g. jsmarty langDetector)
 */
public class ParentActor extends AbstractLoggingActor {

    private final ActorRef childActorRef = getContext().actorOf(Props.create(ChildActor.class));

    // we can separate jobs here
    @Override
    public Receive createReceive() {
        log().info("On create method");
        return receiveBuilder()
                .match(InstaJob.class, this::doWithInsta)
                .match(FBJob.class, this::doWithFb)
                .matchAny(this::error)
                .build();
    }

    private void error(Object o) {
        log().error("ERROR " + o.toString());
    }

    private void doWithFb(FBJob job) {
       log().info("On parent FBJOB");
        childActorRef.tell(job, getSelf());
    }

    // this method will send to next component (child actor)
    private void doWithInsta(InstaJob job) {
        log().debug("ON PARENT SENDING TO CHILD Instagram job");
        childActorRef.tell(job, getSelf());
    }
}
