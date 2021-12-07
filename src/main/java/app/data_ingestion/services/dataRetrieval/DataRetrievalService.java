package app.data_ingestion.services.dataRetrieval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.database.IQueryExecutor;
import app.data_ingestion.helpers.QueryConstants;

@Service
public class DataRetrievalService implements IDataRetrievalService {

    @Autowired
    IQueryExecutor queryExecutor;

    /**
     * @param tableName
     * @return List of records as Object
     */
    @Override
    public Object fetchData(String tableName) {
        String query = String.format(QueryConstants.SELECT_WITHOUT_CONDITION, tableName);
        return queryExecutor.fetchRecords(query);
    }

}
