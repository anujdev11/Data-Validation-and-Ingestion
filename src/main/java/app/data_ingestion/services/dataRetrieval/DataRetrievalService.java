package app.data_ingestion.services.dataRetrieval;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.database.QueryExecutor;
import app.data_ingestion.helpers.QueryConstants;

@Service
public class DataRetrievalService implements IDataRetrievalService {

    @Autowired
    QueryExecutor queryExecutor;

    /**
     * @param table_name
     * @return
     */
    @Override
    public Object fetchData(String table_name) {
        String query = String.format(QueryConstants.SELECT_WITHOUT_CONDITION, table_name);
        return queryExecutor.fetchRecords(query);
    }

}
