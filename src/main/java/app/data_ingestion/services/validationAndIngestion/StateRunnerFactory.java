package app.data_ingestion.services.validationAndIngestion;

public class StateRunnerFactory {

    private static StateRunnerFactory stateRunnerFactory = new StateRunnerFactory();

    private StateRunnerFactory() {
    }


    /**
     * return the instance of this class
     * @return StateRunnerFactory
     */
    public static StateRunnerFactory getInstance() {
        return stateRunnerFactory;
    }

    /**
     * create a new object of StateRunner
     * @return StateRunner
     */
    public StateRunner createStateRunner() {
        return new StateRunner();
    }

}
