package a1.entity;
// Generated 28.04.2015 1:36:56 by Hibernate Tools 4.3.1

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import a1.annotations.Forms;
import a1.annotations.Synonym;

@Entity
@Table(name="contacts")
@Synonym(text="Контакты",textEng="Contacts")
@Forms(element="")
public class Contacts  implements java.io.Serializable {


	@Synonym(text="Код",textEng="Code") 
	private int id;
     @Synonym(text="Фамилия",textEng="Surname")
     private String f;
     @Synonym(text="Имя",textEng="First name")
     private String i;
     @Synonym(text="Отчество",textEng="Father name")
     private String o;
     @Synonym(text="Статус",textEng="Status")
     private Contact_Status status;
     @Synonym(text="Адрес",textEng="Address")
     private String address;
     @Synonym(text="Телефон",textEng="Phone")
     private String phone;
     @Synonym(text="Прочее",textEng="Other")
     private String description;

    public Contacts() {
    }

	
    public Contacts(int id, String f, String i, Contact_Status status, String address, String phone, String description) {
        this.id = id;
        this.f = f;
        this.i = i;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }
    public Contacts(int id, String f, String i, String o, Contact_Status status, String address, String phone, String description) {
       this.id = id;
       this.f = f;
       this.i = i;
       this.o = o;
       this.status = status;
       this.address = address;
       this.phone = phone;
       this.description = description;
    }
   
   @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)    
    @Column(name = "id")     
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    @Synonym(text="Фамилия")
    public String getF() {
        return this.f;
    }
    
    public void setF(String f) {
        this.f = f;
    }
    public String getI() {
        return this.i;
    }
    
    public void setI(String i) {
        this.i = i;
    }
    public String getO() {
        return this.o;
    }
    
    public void setO(String o) {
        this.o = o;
    }
    
    @ManyToOne(targetEntity = Contact_Status.class,cascade={CascadeType.ALL}//,cascade = {CascadeType.ALL}
    )//,cascade={CascadeType.REFRESH},fetch=FetchType.EAGER,optional=true)//, cascade = {CascadeType.ALL},optional=false)//, fetch =FetchType.LAZY)
    @NotFound(action=NotFoundAction.IGNORE)
    @JoinColumn(name = "contact_status",   referencedColumnName="id",nullable=true,insertable=false,updatable=true)     
    public Contact_Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Contact_Status status) {
        this.status = status;
    }
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return this.f+" "+this.i+" "+this.o;
    }


}


