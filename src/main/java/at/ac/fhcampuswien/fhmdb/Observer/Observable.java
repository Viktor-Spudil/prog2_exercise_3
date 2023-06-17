package at.ac.fhcampuswien.fhmdb.Observer;

public interface Observable {
    void notifyObservers(String message);
    void subscribe(Observer observer);
    void unsubscribe(Observer observer);
}
