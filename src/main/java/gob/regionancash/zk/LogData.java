package gob.regionancash.zk;

import java.io.Serializable;

public class LogData
  implements Serializable {
  private int machineNumber;
  private int enrollNumber;
  private int verifyMode;
  private int inOutMode;
  private int year;
  private int month;
  private int day;
  private int hour;
  private int minute;
  private int second;
  private int workCode;
  
  public int getMachineNumber() {
    return this.machineNumber;
  }
  public void setMachineNumber(int machineNumber) {
    this.machineNumber = machineNumber;
  }
  public int getEnrollNumber() {
    return this.enrollNumber;
  }
  public void setEnrollNumber(int enrollNumber) {
    this.enrollNumber = enrollNumber;
  }
  public int getVerifyMode() {
    return this.verifyMode;
  }
  public void setVerifyMode(int verifyMode) {
    this.verifyMode = verifyMode;
  }
  public int getInOutMode() {
    return this.inOutMode;
  }
  public void setInOutMode(int inOutMode) {
    this.inOutMode = inOutMode;
  }
  public int getYear() {
    return this.year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public int getMonth() {
    return this.month;
  }
  public void setMonth(int month) {
    this.month = month;
  }
  public int getDay() {
    return this.day;
  }
  public void setDay(int day) {
    this.day = day;
  }
  public int getHour() {
    return this.hour;
  }
  public void setHour(int hour) {
    this.hour = hour;
  }
  public int getMinute() {
    return this.minute;
  }
  public void setMinute(int minute) {
    this.minute = minute;
  }
  public int getSecond() {
    return this.second;
  }
  public void setSecond(int second) {
    this.second = second;
  }
  public int getWorkCode() {
    return this.workCode;
  }
  public void setWorkCode(int workCode) {
    this.workCode = workCode;
  }
  
  public String toString() {
    return "machineNumber=" + this.machineNumber + ";enrollNumber=" + this.enrollNumber + " in " + this.year + "-" + this.month + "-" + this.day + " " + String.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(this.hour), Integer.valueOf(this.minute), Integer.valueOf(this.second) });
  }
}


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/LogData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */