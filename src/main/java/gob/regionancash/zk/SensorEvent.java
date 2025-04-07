package gob.regionancash.zk;

import com.jacob.com.Variant;
import gob.regionancash.zk.LogData;
import gob.regionancash.zk.ZK;
import java.util.ArrayList;
import java.util.List;

public class SensorEvent {
  private ZK zk;

  public SensorEvent(ZK zk) {
    this.zk = zk;
  }

  public void log(String eventName, Variant[] variants) {
    List<String> l = new ArrayList();
    for (Variant v : variants) {
      l.add(v.toString());
    }
    System.out.println("SensorEvent->" + eventName + ": " + String.join(", ", (Iterable) l));
  }

  public void OnConnected(Variant[] arge) {
    this.zk.setConnected(true);
    this.zk.setMachineNumber(toInt((arge.length > 0) ? arge[0] : null));
    this.zk.getListener().OnConnected(this.zk);
  }

  public void OnDisConnected(Variant[] arge) {
    log("OnDisConnected on " + this.zk.getIp(), arge);
    this.zk.setConnected(false);
  }

  public void OnAlarm(Variant[] arge) {
    log("OnConnected", arge);
  }

  private int toInt(Variant v) {
    try {
      return (v != null) ? Integer.parseInt(v.toString()) : 0;
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public void OnAttTransactionEx(Variant[] arge) {
    if (this.zk.getListener() == null)
      return;
    log(this.zk.getIp() + ".OnAttTransactionEx", arge);
    int enrollNumber = toInt(arge[0]);
    if (enrollNumber > 0) {
      LogData logData = new LogData();
      logData.setMachineNumber(this.zk.getMachineNumber());
      if (logData.getMachineNumber() == 0)
        logData.setMachineNumber(this.zk.getId());
      logData.setEnrollNumber(enrollNumber);
      logData.setVerifyMode(toInt(arge[3]));
      logData.setInOutMode(0);
      logData.setYear(toInt(arge[4]));
      logData.setMonth(toInt(arge[5]));
      logData.setDay(toInt(arge[6]));
      logData.setHour(toInt(arge[7]));
      logData.setMinute(toInt(arge[8]));
      logData.setSecond(toInt(arge[9]));
      logData.setWorkCode(toInt(arge[10]));
      this.zk.getListener().OnAttTransactionEx(this.zk, logData);
    }
  }

  public void OnEnrollFingerEx(Variant[] arge) {
    log("OnEnrollFingerEx", arge);
  }

  public void OnFinger(Variant[] arge) {
    log("OnFinger", arge);
  }

  public void OnFingerFeature(Variant[] arge) {
    log("OnFingerFeature", arge);
  }

  public void OnHIDNum(Variant[] arge) {
    log("OnHIDNum", arge);
  }

  public void OnNewUser(Variant[] arge) {
    log("OnNewUser", arge);
  }

  public void OnVerify(Variant[] arge) {
    log("OnVerify", arge);
  }

  public void OnWriteCard(Variant[] arge) {
    log("OnWriteCard", arge);
  }

  public void OnEmptyCard(Variant[] arge) {
    log("OnEmptyCard", arge);
  }

  public void OnEMData(Variant[] arge) {
    log("OnEmptyCard", arge);
  }
}
