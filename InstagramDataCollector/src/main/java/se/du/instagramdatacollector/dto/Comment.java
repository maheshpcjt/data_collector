/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.du.instagramdatacollector.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author maheshchathuranga
 */
@Entity
public class Comment implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="comment",columnDefinition="LONGTEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="instagram_id", nullable=false)
    private Instagram instagram;
    
    
    public Comment() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public Instagram getInstagram() {
        return instagram;
    }

    public void setInstagram(Instagram instagram) {
        this.instagram = instagram;
    }

    public Comment(String comment, Instagram instagram) {
        this.comment = comment;
        this.instagram = instagram;
    }
    
    
}
