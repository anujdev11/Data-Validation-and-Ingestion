package app.data_ingestion.service_layer.services;

public interface DataRetrievalService {
    /**
     * @param table_name
     * @return
     */
    public Object fetchData(String table_name);
}
