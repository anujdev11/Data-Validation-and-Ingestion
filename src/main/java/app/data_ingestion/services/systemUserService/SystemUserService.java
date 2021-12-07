package app.data_ingestion.services.systemUserService;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data_ingestion.dataLayer.dao.ISystemUserDao;
import app.data_ingestion.dataLayer.models.SystemUser;
import app.data_ingestion.exceptions.ResourceNotFoundException;
import app.data_ingestion.helpers.LiteralConstants;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;

@Service
public class SystemUserService implements ISystemUserService {

    @Autowired
    ISystemUserDao systemUserDao;
    SystemUser systemUser;

    
    /** 
     * @return SystemUser
     */
    @Override
    public SystemUser getSystemUser() {
        return systemUser;
    }

    
    /** 
     * @param username
     * @param password
     * @return SystemUser
     * @throws ResourceNotFoundException
     * @throws SQLException
     */
    @Override
    public SystemUser systemUserAuthentication(String username, String password)
            throws ResourceNotFoundException, SQLException {
        systemUser = systemUserDao.authenticateSystemUser(username, password);
        if (systemUser == null) {
            throw new ResourceNotFoundException(LiteralConstants.INVALID_CREDENTIAL_STRING);
        }
        return systemUser;
    }

    
    /** 
     * @param systemUser
     * @return SystemUser
     * @throws SQLException
     * @throws ResourceAlreadyExistsException
     */
    @Override
    public SystemUser addSystemUser(SystemUser systemUser) throws SQLException, ResourceAlreadyExistsException {
        try {
            systemUser = systemUserDao.addSystemUser(systemUser);
            return systemUser;
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ResourceAlreadyExistsException(LiteralConstants.USER_ALREADY_EXISTS_STRING);
        }
    }

    
    /** 
     * @param username
     * @return SystemUser
     * @throws SQLException
     * @throws ResourceNotFoundException
     */
    @Override
    public SystemUser deleteSystemUser(String username) throws SQLException, ResourceNotFoundException {
        systemUser = systemUserDao.getSystemUser(username);
        if (systemUser == null) {
            throw new ResourceNotFoundException(LiteralConstants.USER_NOT_FOUND_STRING);
        }
        return systemUserDao.deleteSystemUser(systemUser);
    }

    
    /** 
     * @param systemUser
     * @return SystemUser
     * @throws SQLException
     * @throws ResourceNotFoundException
     */
    @Override
    public SystemUser updateSystemUser(SystemUser systemUser) throws SQLException, ResourceNotFoundException {
        SystemUser systemUserExists = systemUserDao.getSystemUser(systemUser.getUsername());
        if (systemUserExists == null) {
            throw new ResourceNotFoundException(LiteralConstants.USER_NOT_FOUND_STRING);
        }
        systemUser.setId(systemUserExists.getId());
        return systemUserDao.updateSystemUser(systemUser);
    }
}
