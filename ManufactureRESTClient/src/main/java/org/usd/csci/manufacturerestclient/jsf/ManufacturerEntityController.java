package org.usd.csci.manufacturerestclient.jsf;

import org.usd.csci.manufacturerestclient.data.ManufacturerEntity;
import org.usd.csci.manufacturerestclient.jsf.util.JsfUtil;
import org.usd.csci.manufacturerestclient.jsf.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;

@Named("manufacturerEntityController")
@SessionScoped
public class ManufacturerEntityController implements Serializable {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ManufactureRESTService/webresources";
    
    private List<ManufacturerEntity> items = null;
    private ManufacturerEntity selected;

    public ManufacturerEntityController() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("manufacturerentity");
    }

    public ManufacturerEntity getSelected() {
        return selected;
    }

    public void setSelected(ManufacturerEntity selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public ManufacturerEntity prepareCreate() {
        selected = new ManufacturerEntity();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Manufacturer").getString("ManufacturerEntityDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ManufacturerEntity> getItems() {
        if (items == null) {
            items = findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.DELETE) {
                    remove(selected);
                } else if (persistAction == PersistAction.UPDATE){
                    edit(selected);
                }else{
                    createEntity(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Manufacturer").getString("PersistenceErrorOccured"));
            }
        }
    }

    public ManufacturerEntity getManufacturerEntity(java.lang.Integer id) {
        try{
        WebTarget resource = webTarget;
        resource.path(MessageFormat.format("{0}", new Object[]{id}));
        String jsonManufacturerEntity = resource.request(MediaType.APPLICATION_JSON).get(String.class);
        return new ManufacturerEntity(jsonManufacturerEntity);
        }catch(Exception e){
            JsfUtil.addErrorMessage("Unable to find manufacturer" +id);
            return null;
        }
    }

    public List<ManufacturerEntity> getItemsAvailableSelectMany() {
        return findAll();
    }

    public List<ManufacturerEntity> getItemsAvailableSelectOne() {
        return findAll();
    }
    
    public List<ManufacturerEntity> findAll(){
        try{
            WebTarget resource = webTarget;
            String jsonListString = resource.request(MediaType.APPLICATION_JSON).get(String.class);
            return convertToList(jsonListString);
            }catch(Exception e){
                JsfUtil.addErrorMessage(e, "Exception thrown shen attemping to" + "retrive a range of Manufacturers");
                return new ArrayList();    
        }
    }

    private List<ManufacturerEntity> convertToList(String jsonListString) throws JSONException {
        try{
            JSONArray jsonList = new JSONArray(jsonListString);
            List<ManufacturerEntity> list = new ArrayList();
            for( int i =0; i< jsonList.length(); i++){
                ManufacturerEntity anEntity = new ManufacturerEntity(jsonList.get(i).toString());
                list.add(anEntity);
            }
            return list;
        }catch(Exception e){
            throw new JSONException (e.getMessage());
        }
        
    }

    private void remove(ManufacturerEntity entity) {
        String idString = entity.getManufacturerId()+"";
        WebTarget resource = webTarget;
        resource.path(java.text.MessageFormat.format("{0}",
                new Object[]{idString})).request().delete();
    }

    private void createEntity(ManufacturerEntity entity) throws Exception {
        Response response = webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).
                post(javax.ws.rs.client.Entity.entity(entity,javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        
        if(response.getStatus() != Response.Status.OK.getStatusCode()){
            throw new Exception("Unable to create Manufacturer. Code: " + response.getStatus());
        }
    }

    private void edit(ManufacturerEntity entity) throws Exception {
        Response response = webTarget.path(java.text.MessageFormat.format("{0}",
                new Object[]{entity.getManufacturerId() + ""})).request(MediaType.APPLICATION_JSON).
                put(javax.ws.rs.client.Entity.entity(entity,
                        javax.ws.rs.core.MediaType.APPLICATION_JSON),Response.class);
        
        if(response.getStatus() != Response.Status.OK.getStatusCode()){
            throw new Exception("Unable to update Manufacturer. ID: " + 
                    entity.getManufacturerId() + "Code: " + response.getStatus());
        }
    }

    @FacesConverter(forClass = ManufacturerEntity.class)
    public static class ManufacturerEntityControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ManufacturerEntityController controller = (ManufacturerEntityController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "manufacturerEntityController");
            return controller.getManufacturerEntity(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof ManufacturerEntity) {
                ManufacturerEntity o = (ManufacturerEntity) object;
                return getStringKey(o.getManufacturerId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ManufacturerEntity.class.getName()});
                return null;
            }
        }

    }

}
