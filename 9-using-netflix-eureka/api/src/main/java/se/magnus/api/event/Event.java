package se.magnus.api.event;

import java.time.ZonedDateTime;

public class Event<K, T> {
    public enum Type {
        CREATE, DELETE
    }

    private final Type eventType;
    private final K key;
    private final T data;
    private final ZonedDateTime eventCreatedAt;

    public Event() {
        this.eventType = null;
        this.key = null;
        this.data = null;
        this.eventCreatedAt = null;
    }

    public Event(T data, K key, Type eventType) {
        this.data = data;
        this.key = key;
        this.eventType = eventType;
        this.eventCreatedAt = ZonedDateTime.now();
    }

    public Type getEventType() {
        return eventType;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public ZonedDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }
}

