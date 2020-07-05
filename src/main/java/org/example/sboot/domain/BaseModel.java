package org.example.sboot.domain;

import io.ebean.Model;
import io.ebean.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.sql.Timestamp;

@Getter
@Setter
@MappedSuperclass
public class BaseModel extends Model {

  @Id
  Long id;

  @Version
  Long version;

  @WhenCreated
  Timestamp whenCreated;

  @WhenModified
  Timestamp whenModified;

  @WhoCreated
  String whoCreated;

  @WhoModified
  String whoModified;

  @SoftDelete
  boolean deleted;

//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public Long getVersion() {
//    return version;
//  }
//
//  public void setVersion(Long version) {
//    this.version = version;
//  }
//
//  public Timestamp getWhenCreated() {
//    return whenCreated;
//  }
//
//  public void setWhenCreated(Timestamp whenCreated) {
//    this.whenCreated = whenCreated;
//  }
//
//  public Timestamp getWhenModified() {
//    return whenModified;
//  }
//
//  public void setWhenModified(Timestamp whenModified) {
//    this.whenModified = whenModified;
//  }
//
//  public String getWhoCreated() {
//    return whoCreated;
//  }
//
//  public void setWhoCreated(String whoCreated) {
//    this.whoCreated = whoCreated;
//  }
//
//  public String getWhoModified() {
//    return whoModified;
//  }
//
//  public void setWhoModified(String whoModified) {
//    this.whoModified = whoModified;
//  }
}
