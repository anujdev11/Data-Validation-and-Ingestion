package app.data_ingestion.services.system_user_service;

import java.sql.SQLException;

import app.data_ingestion.dataLayer.models.SystemUser;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;
import app.data_ingestion.exceptions.ResourceNotFoundException;

public interface ISystemUserService {

    /**
     *
     * @return
     */
    public SystemUser getSystemUser();

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws ResourceNotFoundException
     * @throws SQLException
     */
    public SystemUser systemUserAuthentication(String username, String password)
            throws ResourceNotFoundException, SQLException;

    public SystemUser addSystemUser(SystemUser systemUser) throws SQLException, ResourceAlreadyExistsException;

    public SystemUser deleteSystemUser(String username) throws SQLException, ResourceNotFoundException;

    public SystemUser updateSystemUser(SystemUser systemUser) throws SQLException, ResourceNotFoundException;
}
