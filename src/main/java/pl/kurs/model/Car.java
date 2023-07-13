package pl.kurs.model;

public class Car {
    private String producer;

    public Car(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        return "Car{" +
                "producer='" + producer + '\'' +
                '}';
    }
}
