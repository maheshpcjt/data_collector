/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
/**
 *
 * @author maheshchathuranga
 */
@Entity
public class Instagram implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="image",columnDefinition="LONGTEXT")
    private String image;
    private int likesCount;
    private String uploaderName;
    private String uploaderURL;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;
    private String uploadDateTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date currentDateTime;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "instagram")
    private Set<Comment> comment;

    public Instagram() {
    }

    public Instagram(String image, int likesCount, String uploaderName, String uploaderURL, Location location, String uploadDateTime, Set<Comment> comment) {
        this.image = image;
        this.likesCount = likesCount;
        this.uploaderName = uploaderName;
        this.uploaderURL = uploaderURL;
        this.location = location;
        this.uploadDateTime = uploadDateTime;
        this.comment = comment;
        this.currentDateTime = new Date();
    }

    public Instagram(String image, int likesCount, String uploaderName, String uploaderURL, Location location, String uploadDateTime) {
        this.image = image;
        this.likesCount = likesCount;
        this.uploaderName = uploaderName;
        this.uploaderURL = uploaderURL;
        this.location = location;
        this.uploadDateTime = uploadDateTime;
        this.currentDateTime = new Date();
    }

   

    public String getUploaderName() {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName) {
        this.uploaderName = uploaderName;
    }

    public String getUploaderURL() {
        return uploaderURL;
    }

    public void setUploaderURL(String uploaderURL) {
        this.uploaderURL = uploaderURL;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(String uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public Date getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(Date currentDateTime) {
        this.currentDateTime = currentDateTime;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    

    

}
