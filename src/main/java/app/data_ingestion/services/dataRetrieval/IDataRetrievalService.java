package app.data_ingestion.services.dataRetrieval;

public interface IDataRetrievalService {
    /**
     * @param table_name
     * @return
     */
    public Object fetchData(String tableName);
}
