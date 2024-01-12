package org.example.notify;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class Event implements Serializable {
    private static final AtomicLong SEQUENCE = new AtomicLong(0);

    private final long sequence = SEQUENCE.getAndIncrement();

    /**
     * Event sequence number, which can be used to handle the sequence of events.
     *
     * @return sequence num, It's best to make sure it's monotone.
     */
    public long sequence() {
        return sequence;
    }

    /**
     * Event scope.
     *
     * @return event scope, return null if for all scope
     */
    public String scope() {
        return null;
    }

    /**
     * Whether is plugin event. If so, the event can be dropped when no publish and subscriber without any hint. Default
     * false
     *
     * @return {@code true} if is plugin event, otherwise {@code false}
     */
    public boolean isPluginEvent() {
        return false;
    }
}
