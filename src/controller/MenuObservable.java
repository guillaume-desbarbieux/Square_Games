package controller;

public interface MenuObservable {
    void subscribe(MenuObserver menuObserver, EventType... eventTypes);
    void unsubscribe(MenuObserver menuObserver, EventType... eventTypes);

    void notify(EventType eventType, Generique param);
}