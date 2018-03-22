package job;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import job.types.InstaJob;
import job.types.Job;

/**
 * Created by taras on 22.03.18.
 */
public class ChildActor extends AbstractLoggingActor{

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Job.class, this::doWork)
                .matchAny(this::reply)
                .build();
    }

    private void reply(Object o) {
        System.err.println("return job");
        getSender().tell(o, getSelf());
    }

    private void doWork(Job p) {
        System.out.println("ON TRANSLATION:  " + p.getId());
        p.setTranslation("some translation");
        //get next child
        ActorRef nextChildRef = getContext().actorOf(Props.create(NextChild.class));
        nextChildRef.tell(p, getSelf());
    }
}
