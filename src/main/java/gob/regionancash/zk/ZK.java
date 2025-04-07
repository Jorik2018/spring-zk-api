package gob.regionancash.zk;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.DispatchEvents;
import com.jacob.com.STA;
import com.jacob.com.Variant;
import gob.regionancash.zk.LogData;
import gob.regionancash.zk.SensorEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;


@Component
public class ZK
  implements Runnable
{
  private int machineNumber = 0;
  
  private String ip;
  
  private int id = 0;
  
  private boolean connected = false;
  
  public boolean isConnected() {
    return this.connected;
  }
  
  public void setConnected(boolean connected) {
    this.connected = connected;
  }
  
  public String getIp() {
    return this.ip;
  }
  
  public void setIp(String ip) {
    this.ip = ip;
  }
  
  public int getId() {
    return this.id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public int getMachineNumber() {
    return this.machineNumber;
  }
  
  public void setMachineNumber(int machineNumber) {
    this.machineNumber = machineNumber;
  }







  
  public ActiveXComponent getZkem() {
    return this.zkem;
  }
  
  public boolean disconnect() {
    boolean b = this.zkem.invoke("Disconnect").getBoolean();
    if (b)
      this.connected = false; 
    System.out.println("Device Disconnect on " + getIp());
    return b;
  }
  
  private int toInt(Variant v) {
    try {
      return (v != null) ? Integer.parseInt(v.toString()) : 0;
    } catch (NumberFormatException e) {
      return 0;
    } 
  }
  
  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); private ZKListener listener;
  
  public String getInfo(String infoName) {
    Variant info = new Variant("", true);
    if ("GetSDKVersion".equals(infoName) && 
      this.zkem.invoke(infoName, new Variant[] { info }).getBoolean())
      return info.toString(); 
    if (this.zkem.invoke(infoName, new Variant[] { new Variant(this.machineNumber), info }).getBoolean())
      return info.toString(); 
    throw new RuntimeException("Error on " + infoName);
  }
  
  public Map getInfo() {
    Map<Object, Object> infoMap = new HashMap<>();
    String[] infoNames = { "GetDeviceFirmwareVersion", "SerialNumber", "ProductCode", "FirmwareVersion", "SDKVersion", "DeviceIP", "DeviceMAC", "WiegandFmt", "Platform" };
    
    for (String infoName : infoNames) {
      try {
        infoMap.put(infoName, getInfo("Get" + infoName));
      } catch (Exception e) {
        infoMap.put(infoName, e.toString());
      } 
    }  return infoMap;
  }
  
  public List<LogData> getLogDataList(Date from, Date to) {
    List<LogData> list = new ArrayList<>();
    System.out.println("DESDE " + this.format.format(from));
    System.out.println("HASTA " + this.format.format(to));
    Calendar c = Calendar.getInstance();
    c.setTime(from);
    int fromInt = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
    c.setTime(to);
    int toInt = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
    System.out.println(getIp() + ".EnableDevice " + this.zkem.invoke("EnableDevice", new Variant[] { new Variant(this.machineNumber), new Variant(false) }));

    
    boolean readGeneralLogData = this.zkem.invoke("ReadGeneralLogData", new Variant[] { new Variant(this.machineNumber) }).getBoolean();
    System.out.println(getIp() + ".ReadGeneralLogData=" + readGeneralLogData);
    
    Variant enrollNumber = new Variant("", true);
    Variant verifyMode = new Variant(Integer.valueOf(0), true);
    Variant inOutMode = new Variant(Integer.valueOf(0), true);
    
    Variant year = new Variant(Integer.valueOf(0), true);
    Variant month = new Variant(Integer.valueOf(0), true);
    Variant day = new Variant(Integer.valueOf(0), true);
    
    Variant hour = new Variant(Integer.valueOf(0), true);
    Variant minute = new Variant(Integer.valueOf(0), true);
    Variant second = new Variant(Integer.valueOf(0), true);
    
    Variant workCode = new Variant(Integer.valueOf(0), true);
    
    while (readGeneralLogData) {



      
      readGeneralLogData = this.zkem.invoke("SSR_GetGeneralLogData", new Variant[] { new Variant(this.machineNumber), enrollNumber, verifyMode, inOutMode, year, month, day, hour, minute, second, workCode }).getBoolean();
      int peopleId = toInt(enrollNumber);
      if (peopleId > 0) {
        
        int y = toInt(year);
        int m = toInt(month);
        int d = toInt(day);
        
        int ymd = y * 10000 + m * 100 + d;
        if (ymd >= fromInt && ymd <= toInt) {
          LogData logData = new LogData();
          logData.setMachineNumber((this.machineNumber > 0) ? this.machineNumber : this.id);
          logData.setEnrollNumber(peopleId);
          logData.setVerifyMode(toInt(verifyMode));
          logData.setInOutMode(toInt(inOutMode));
          logData.setYear(y);
          logData.setMonth(m);
          logData.setDay(d);
          logData.setHour(toInt(hour));
          logData.setMinute(toInt(minute));
          logData.setSecond(toInt(second));
          logData.setWorkCode(toInt(workCode));
          short v = 3;
          list.add(logData); continue;
        }  if (ymd > toInt) {
          break;
        }
      } 
    } 
    System.out.println("list.size()=" + list.size());
    System.out.println(this.zkem.invoke("EnableDevice", new Variant[] { new Variant(this.machineNumber), new Variant(true) }));
    return list;
  }


  
  public ZKListener getListener() {
    return this.listener;
  }
  
  public void setListener(ZKListener listener) {
    this.listener = listener;
  }
  
  private STA sta = new STA();
  
  private DispatchEvents dispatchEvents;
  
  private ActiveXComponent zkem;
  
  public void run() {
    this.zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
    System.out.println("zkemkeeper.ZKEM.1 created");
    this.dispatchEvents = new DispatchEvents(this.zkem.getObject(), new SensorEvent(this));
    if (!connect()) {
      System.out.println("Can't connect to " + getIp());
    }
  }

  
  public boolean connect() {
    return this.zkem.invoke("Connect_NET", getIp(), 4370).getBoolean();
  }
  
  public void bindEvents() {
    System.out.println("OnConnected " + this.machineNumber + " on " + getIp());
    if (this.machineNumber >= 0) {
      boolean regEvent = this.zkem.invoke("RegEvent", new Variant[] { new Variant(this.machineNumber), new Variant(65535) }).getBoolean();
      System.out.println(getIp() + ".RegEvent=" + regEvent);
      if (regEvent) {
        System.out.println(getIp() + ".ReadRTLog=" + this.zkem.invoke("ReadRTLog", new Variant[] { new Variant(this.machineNumber) }));
        System.out.println(getIp() + ".ReadRTLog=" + this.zkem.invoke("GetRTLog", new Variant[] { new Variant(this.machineNumber) }));
      } 
    } 
  }








  
  public List getUserList() {
    List<Map<Object, Object>> l = new ArrayList();
    System.out.println("getUserList for " + this.ip);
    boolean hasData = this.zkem.invoke("ReadAllUserID", new Variant[] { new Variant(this.machineNumber) }).getBoolean();
    int i = 0;
    while (hasData) {
      Variant enrollNumber = new Variant("", true);
      Variant name = new Variant("", true);
      Variant pass = new Variant("", true);
      Variant privilege = new Variant(Integer.valueOf(0), true);
      Variant enabled = new Variant(Boolean.valueOf(false), true);
      
      hasData = this.zkem.invoke("SSR_GetAllUserInfo", new Variant[] { new Variant(this.machineNumber), enrollNumber, name, pass, privilege, enabled }).getBoolean();
      Map<Object, Object> m = new HashMap<>();
      m.put("enrollNumber", enrollNumber.toString());
      m.put("name", name.toString());
      m.put("pass", pass.toString());
      m.put("privilege", privilege.toString());
      m.put("enabled", enabled.toString());
      l.add(m);
    } 
    return l;
  }

  
  public String toString() {
    return "ZK [machineNumber=" + this.machineNumber + ", ip=" + this.ip + ", id=" + this.id + ", connected=" + this.connected + "]";
  }
}


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/ZK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */