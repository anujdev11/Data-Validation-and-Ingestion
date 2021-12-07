package app.data_ingestion.services.organizationAdminService;

import java.sql.SQLException;
import java.util.List;

import app.data_ingestion.dataLayer.models.User;
import app.data_ingestion.exceptions.ResourceAlreadyExistsException;
import app.data_ingestion.exceptions.ResourceNotFoundException;

public interface IOrganizationAdminService {

    public User getOrganizationAdmin(String username) throws SQLException, ResourceNotFoundException;

    public List<User> listAllOrganizationAdmin() throws SQLException, ResourceNotFoundException;

    public User addOrganizationUser(User systemUser) throws SQLException, ResourceAlreadyExistsException;

    public User deleteOrganization(String username) throws SQLException, ResourceNotFoundException;

    public User updateOrganization(User systemUser) throws SQLException, ResourceNotFoundException;

    public List<User> listAllOrganizationUser(String organizationName) throws SQLException, ResourceNotFoundException;

}
