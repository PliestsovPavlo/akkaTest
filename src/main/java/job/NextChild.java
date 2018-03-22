package job;

import akka.actor.AbstractLoggingActor;

/**
 * Created by taras on 22.03.18.
 */
public class NextChild extends AbstractLoggingActor{
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(this::printJob)
                .build();
    }

    private void printJob(Object o) {
        System.out.println("ON Next child");
        System.out.println(o.toString());
    }
}
