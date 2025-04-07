
package gob.regionancash.rh.jpa;

import java.io.Serializable;
import java.util.Date;

public class Attendance
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Integer id;
  private int peopleId;
  private Integer contractId;
  private Date dateTime;
  private Date createDate;
  private Integer inOutMode;
  private short canceled;
  private int machineNumber;
  private int verifyMode;
  
  public Attendance() {}
  
  public Attendance(Integer id) {
    this.id = id;
  }
  
  public Integer getId() {
    return this.id;
  }
  
  public void setId(Integer id) {
    this.id = id;
  }


  
  public Integer getInOutMode() {
    return this.inOutMode;
  }
  
  public void setInOutMode(Integer inOutMode) {
    this.inOutMode = inOutMode;
  }
  
  public int getMachineNumber() {
    return this.machineNumber;
  }
  
  public void setMachineNumber(int machineNumber) {
    this.machineNumber = machineNumber;
  }
  
  public int getVerifyMode() {
    return this.verifyMode;
  }
  
  public void setVerifyMode(int verifyMode) {
    this.verifyMode = verifyMode;
  }
  
  public int getPeopleId() {
    return this.peopleId;
  }
  
  public void setPeopleId(int peopleId) {
    this.peopleId = peopleId;
  }
  
  public Integer getContractId() {
    return this.contractId;
  }
  
  public void setContractId(Integer contractId) {
    this.contractId = contractId;
  }
  
  public static long getSerialversionuid() {
    return 1L;
  }
  
  public Date getDateTime() {
    return this.dateTime;
  }
  
  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }
  
  public Date getCreateDate() {
    return this.createDate;
  }
  
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  public short getCanceled() {
    return this.canceled;
  }
  
  public void setCanceled(short canceled) {
    this.canceled = canceled;
  }

  
  public int hashCode() {
    int hash = 0;
    hash += (this.id != null) ? this.id.hashCode() : 0;
    return hash;
  }


  
  public boolean equals(Object object) {
    if (!(object instanceof gob.regionancash.rh.jpa.Attendance)) {
      return false;
    }
    gob.regionancash.rh.jpa.Attendance other = (gob.regionancash.rh.jpa.Attendance)object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  
  public String toString() {
    return "gob.regionancash.rh.rest.RhAssist[ id=" + this.id + " ]";
  }
}


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/rh/jpa/Attendance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */