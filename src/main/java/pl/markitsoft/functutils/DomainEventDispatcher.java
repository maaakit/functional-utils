package pl.markitsoft.functutils;

public class DomainEventDispatcher extends FunctionalSwitch<DomainEvent> {

    public static Builder<DomainEvent> builder() {
        return new Builder<>();
    }



}
