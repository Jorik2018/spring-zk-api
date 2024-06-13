/*    */ package BOOT-INF.classes.gob.regionancash.zk;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class LogData
/*    */   implements Serializable {
/*    */   private int machineNumber;
/*    */   private int enrollNumber;
/*    */   private int verifyMode;
/*    */   private int inOutMode;
/*    */   private int year;
/*    */   private int month;
/*    */   private int day;
/*    */   private int hour;
/*    */   private int minute;
/*    */   private int second;
/*    */   private int workCode;
/*    */   
/*    */   public int getMachineNumber() {
/* 20 */     return this.machineNumber;
/*    */   }
/*    */   public void setMachineNumber(int machineNumber) {
/* 23 */     this.machineNumber = machineNumber;
/*    */   }
/*    */   public int getEnrollNumber() {
/* 26 */     return this.enrollNumber;
/*    */   }
/*    */   public void setEnrollNumber(int enrollNumber) {
/* 29 */     this.enrollNumber = enrollNumber;
/*    */   }
/*    */   public int getVerifyMode() {
/* 32 */     return this.verifyMode;
/*    */   }
/*    */   public void setVerifyMode(int verifyMode) {
/* 35 */     this.verifyMode = verifyMode;
/*    */   }
/*    */   public int getInOutMode() {
/* 38 */     return this.inOutMode;
/*    */   }
/*    */   public void setInOutMode(int inOutMode) {
/* 41 */     this.inOutMode = inOutMode;
/*    */   }
/*    */   public int getYear() {
/* 44 */     return this.year;
/*    */   }
/*    */   public void setYear(int year) {
/* 47 */     this.year = year;
/*    */   }
/*    */   public int getMonth() {
/* 50 */     return this.month;
/*    */   }
/*    */   public void setMonth(int month) {
/* 53 */     this.month = month;
/*    */   }
/*    */   public int getDay() {
/* 56 */     return this.day;
/*    */   }
/*    */   public void setDay(int day) {
/* 59 */     this.day = day;
/*    */   }
/*    */   public int getHour() {
/* 62 */     return this.hour;
/*    */   }
/*    */   public void setHour(int hour) {
/* 65 */     this.hour = hour;
/*    */   }
/*    */   public int getMinute() {
/* 68 */     return this.minute;
/*    */   }
/*    */   public void setMinute(int minute) {
/* 71 */     this.minute = minute;
/*    */   }
/*    */   public int getSecond() {
/* 74 */     return this.second;
/*    */   }
/*    */   public void setSecond(int second) {
/* 77 */     this.second = second;
/*    */   }
/*    */   public int getWorkCode() {
/* 80 */     return this.workCode;
/*    */   }
/*    */   public void setWorkCode(int workCode) {
/* 83 */     this.workCode = workCode;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 87 */     return "machineNumber=" + this.machineNumber + ";enrollNumber=" + this.enrollNumber + " in " + this.year + "-" + this.month + "-" + this.day + " " + String.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.second) });
/*    */   }
/*    */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/LogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */