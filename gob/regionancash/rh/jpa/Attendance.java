/*     */ package BOOT-INF.classes.gob.regionancash.rh.jpa;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Date;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Attendance
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private Integer id;
/*     */   private int peopleId;
/*     */   private Integer contractId;
/*     */   private Date dateTime;
/*     */   private Date createDate;
/*     */   private Integer inOutMode;
/*     */   private short canceled;
/*     */   private int machineNumber;
/*     */   private int verifyMode;
/*     */   
/*     */   public Attendance() {}
/*     */   
/*     */   public Attendance(Integer id) {
/*  61 */     this.id = id;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/*  65 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(Integer id) {
/*  69 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getInOutMode() {
/*  75 */     return this.inOutMode;
/*     */   }
/*     */   
/*     */   public void setInOutMode(Integer inOutMode) {
/*  79 */     this.inOutMode = inOutMode;
/*     */   }
/*     */   
/*     */   public int getMachineNumber() {
/*  83 */     return this.machineNumber;
/*     */   }
/*     */   
/*     */   public void setMachineNumber(int machineNumber) {
/*  87 */     this.machineNumber = machineNumber;
/*     */   }
/*     */   
/*     */   public int getVerifyMode() {
/*  91 */     return this.verifyMode;
/*     */   }
/*     */   
/*     */   public void setVerifyMode(int verifyMode) {
/*  95 */     this.verifyMode = verifyMode;
/*     */   }
/*     */   
/*     */   public int getPeopleId() {
/*  99 */     return this.peopleId;
/*     */   }
/*     */   
/*     */   public void setPeopleId(int peopleId) {
/* 103 */     this.peopleId = peopleId;
/*     */   }
/*     */   
/*     */   public Integer getContractId() {
/* 107 */     return this.contractId;
/*     */   }
/*     */   
/*     */   public void setContractId(Integer contractId) {
/* 111 */     this.contractId = contractId;
/*     */   }
/*     */   
/*     */   public static long getSerialversionuid() {
/* 115 */     return 1L;
/*     */   }
/*     */   
/*     */   public Date getDateTime() {
/* 119 */     return this.dateTime;
/*     */   }
/*     */   
/*     */   public void setDateTime(Date dateTime) {
/* 123 */     this.dateTime = dateTime;
/*     */   }
/*     */   
/*     */   public Date getCreateDate() {
/* 127 */     return this.createDate;
/*     */   }
/*     */   
/*     */   public void setCreateDate(Date createDate) {
/* 131 */     this.createDate = createDate;
/*     */   }
/*     */   
/*     */   public short getCanceled() {
/* 135 */     return this.canceled;
/*     */   }
/*     */   
/*     */   public void setCanceled(short canceled) {
/* 139 */     this.canceled = canceled;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 144 */     int hash = 0;
/* 145 */     hash += (this.id != null) ? this.id.hashCode() : 0;
/* 146 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 152 */     if (!(object instanceof gob.regionancash.rh.jpa.Attendance)) {
/* 153 */       return false;
/*     */     }
/* 155 */     gob.regionancash.rh.jpa.Attendance other = (gob.regionancash.rh.jpa.Attendance)object;
/* 156 */     if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
/* 157 */       return false;
/*     */     }
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 164 */     return "gob.regionancash.rh.rest.RhAssist[ id=" + this.id + " ]";
/*     */   }
/*     */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/rh/jpa/Attendance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */