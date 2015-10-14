
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a1.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table; 


@Entity
@Table(name="event_types")
public class Event_types  implements java.io.Serializable {


     private int id;
     private String name;
     private long color;

    public Event_types() {
    }
	
    public Event_types(int id, String name) {
        this.id = id;
        this.name = name;
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
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

 public long getColor() {
        return this.color;
    }
    
    public void setColor(long color) {
        this.color = color;
    }
    public String toString() {
    return getName();
    }
}


