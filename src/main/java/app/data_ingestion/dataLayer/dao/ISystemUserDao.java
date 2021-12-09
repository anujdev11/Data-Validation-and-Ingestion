package app.data_ingestion.dataLayer.dao;

import java.sql.SQLException;

import app.data_ingestion.dataLayer.models.SystemUser;

public interface ISystemUserDao {

    /**
     * @param systemUser
     * @return
     * @throws SQLException
     */
    public SystemUser addSystemUser(SystemUser systemUser)
            throws SQLException;


    /**
     * @param systemUser
     * @return
     * @throws SQLException
     */
    public SystemUser deleteSystemUser(SystemUser systemUser) throws SQLException;

    /**
     * @param username
     * @return
     * @throws SQLException
     */
    public SystemUser authenticateSystemUser(String username, String password) throws SQLException;

    /**
     * @return
     * @throws SQLException
     */
    public SystemUser getSystemUser(String username) throws SQLException;

    /**
     * @param systemUser
     * @throws SQLException
     */
    public SystemUser updateSystemUser(SystemUser systemUser) throws SQLException;

}
