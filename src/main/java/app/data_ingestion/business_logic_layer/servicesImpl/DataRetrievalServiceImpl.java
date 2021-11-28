package app.data_ingestion.business_logic_layer.servicesImpl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.business_logic_layer.services.DataRetrievalService;
import app.data_ingestion.data_layer.database.QueryExecutor;

@Service
public class DataRetrievalServiceImpl implements DataRetrievalService{

    @Autowired
    QueryExecutor queryExecutor;
    
    @Override
    public Object fetchData(String table_name) {
        String query = String.format("select * from %s", table_name);
        return queryExecutor.fetchRecords(query);
    }
    
}