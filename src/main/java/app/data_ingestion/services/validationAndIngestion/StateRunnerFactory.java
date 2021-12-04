package app.data_ingestion.services.validationAndIngestion;

public class StateRunnerFactory {

    private static StateRunnerFactory stateRunnerFactory = new StateRunnerFactory();
    private StateRunnerFactory(){}

    public static StateRunnerFactory getInstance(){
        return stateRunnerFactory;
    }
    public StateRunner createStateRunner(){
        return new StateRunner();
    }
    
}
