/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usd.csci.manufacturerestclient.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "MANUFACTURER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManufacturerEntity.findAll", query = "SELECT m FROM ManufacturerEntity m")
    , @NamedQuery(name = "ManufacturerEntity.findByManufacturerId", query = "SELECT m FROM ManufacturerEntity m WHERE m.manufacturerId = :manufacturerId")
    , @NamedQuery(name = "ManufacturerEntity.findByName", query = "SELECT m FROM ManufacturerEntity m WHERE m.name = :name")
    , @NamedQuery(name = "ManufacturerEntity.findByAddressline1", query = "SELECT m FROM ManufacturerEntity m WHERE m.addressline1 = :addressline1")
    , @NamedQuery(name = "ManufacturerEntity.findByAddressline2", query = "SELECT m FROM ManufacturerEntity m WHERE m.addressline2 = :addressline2")
    , @NamedQuery(name = "ManufacturerEntity.findByCity", query = "SELECT m FROM ManufacturerEntity m WHERE m.city = :city")
    , @NamedQuery(name = "ManufacturerEntity.findByState", query = "SELECT m FROM ManufacturerEntity m WHERE m.state = :state")
    , @NamedQuery(name = "ManufacturerEntity.findByZip", query = "SELECT m FROM ManufacturerEntity m WHERE m.zip = :zip")
    , @NamedQuery(name = "ManufacturerEntity.findByPhone", query = "SELECT m FROM ManufacturerEntity m WHERE m.phone = :phone")
    , @NamedQuery(name = "ManufacturerEntity.findByFax", query = "SELECT m FROM ManufacturerEntity m WHERE m.fax = :fax")
    , @NamedQuery(name = "ManufacturerEntity.findByEmail", query = "SELECT m FROM ManufacturerEntity m WHERE m.email = :email")
    , @NamedQuery(name = "ManufacturerEntity.findByRep", query = "SELECT m FROM ManufacturerEntity m WHERE m.rep = :rep")})
public class ManufacturerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MANUFACTURER_ID")
    private Integer manufacturerId;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE1")
    private String addressline1;
    @Size(max = 30)
    @Column(name = "ADDRESSLINE2")
    private String addressline2;
    @Size(max = 25)
    @Column(name = "CITY")
    private String city;
    @Size(max = 2)
    @Column(name = "STATE")
    private String state;
    @Pattern(regexp="^[1-9][0-9]{4}$",message = "zipcode must be 5 digits")
    @Size(max = 10)
    @Column(name = "ZIP")
    private String zip;
    @Size(max = 12)
    @Column(name = "PHONE")
    private String phone;
    @Size(max = 12)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 40)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 30)
    @Column(name = "REP")
    private String rep;

    public ManufacturerEntity() {
    }
    
    public ManufacturerEntity(String JSONEntity) throws JSONException{
        JSONObject obj = new JSONObject(JSONEntity);
        this.manufacturerId = obj.getInt("manufactureId");
        this.name = obj.getString("name");
        this.addressline1 = obj.getString("addressline1");
        this.addressline2 = obj.getString("addressline2");
        this.city = obj.getString("city");
        this.state = obj.getString("state");
        this.zip = obj.getString("zip");
        this.phone = obj.getString("phone");
        this.fax = obj.getString("fax");
        this.email = obj.getString("email");
        this.rep = obj.getString("rep");
        

    }

    public ManufacturerEntity(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (manufacturerId != null ? manufacturerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManufacturerEntity)) {
            return false;
        }
        ManufacturerEntity other = (ManufacturerEntity) object;
        if ((this.manufacturerId == null && other.manufacturerId != null) || (this.manufacturerId != null && !this.manufacturerId.equals(other.manufacturerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    public String toJSON() throws JSONException{
        JSONObject obj = new JSONObject();
        obj.put("manufacturerId",this.manufacturerId);
        obj.put("name",this.name);
        obj.put("addressline1",this.addressline1);
        obj.put("addressline2",this.addressline2);
        obj.put("city",this.city);
        obj.put("state",this.state);
        obj.put("zip",this.zip);
        obj.put("phone",this.phone);
        obj.put("fax",this.fax);
        obj.put("email",this.email);
        obj.put("rep",this.rep);
        return obj.toString();      
    }
    
}
