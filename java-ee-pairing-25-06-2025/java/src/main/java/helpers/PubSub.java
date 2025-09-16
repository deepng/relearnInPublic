package helpers;

import java.util.*;

public class PubSub {

    Map<String, Set<ISubscriber>> topicVsSub = new HashMap<>();

    private PubSub(){}

    public static PubSub getInstance() {
        return InstanceHolder.instance;
    }

    public void registerSubscriber(String topic, ISubscriber subscriber) {
        Set<ISubscriber> subList = topicVsSub.getOrDefault(topic, new HashSet<>());
        subList.add(subscriber);
    }

    public void publish(String topic, Object event) {
        Set<ISubscriber> subscribers = topicVsSub.getOrDefault(topic, new HashSet<>(2));
        subscribers.forEach( sub -> {
            sub.processEvent(event);
        });
    }

    public Set<String> getTopics() {
        return topicVsSub.keySet();
    }

    private class InstanceHolder {
        private static final PubSub instance = new PubSub();
    }
}
