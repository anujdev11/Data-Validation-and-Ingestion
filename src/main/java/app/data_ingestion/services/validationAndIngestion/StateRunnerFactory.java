package app.data_ingestion.services.validationAndIngestion;

public class StateRunnerFactory {

    private static StateRunnerFactory stateRunnerFactory = new StateRunnerFactory();
    private StateRunnerFactory(){}

    
    /** 
     * @return StateRunnerFactory
     */
    public static StateRunnerFactory getInstance(){
        return stateRunnerFactory;
    }
    
    /** 
     * @return StateRunner
     */
    public StateRunner createStateRunner(){
        return new StateRunner();
    }
    
}
