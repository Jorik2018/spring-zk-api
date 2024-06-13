/*     */ package BOOT-INF.classes.gob.regionancash.zk;
/*     */ 
/*     */ import com.jacob.activeX.ActiveXComponent;
/*     */ import com.jacob.com.DispatchEvents;
/*     */ import com.jacob.com.STA;
/*     */ import com.jacob.com.Variant;
/*     */ import gob.regionancash.zk.LogData;
/*     */ import gob.regionancash.zk.SensorEvent;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class ZK
/*     */   implements Runnable
/*     */ {
/*  23 */   private int machineNumber = 0;
/*     */   
/*     */   private String ip;
/*     */   
/*  27 */   private int id = 0;
/*     */   
/*     */   private boolean connected = false;
/*     */   
/*     */   public boolean isConnected() {
/*  32 */     return this.connected;
/*     */   }
/*     */   
/*     */   public void setConnected(boolean connected) {
/*  36 */     this.connected = connected;
/*     */   }
/*     */   
/*     */   public String getIp() {
/*  40 */     return this.ip;
/*     */   }
/*     */   
/*     */   public void setIp(String ip) {
/*  44 */     this.ip = ip;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  48 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  52 */     this.id = id;
/*     */   }
/*     */   
/*     */   public int getMachineNumber() {
/*  56 */     return this.machineNumber;
/*     */   }
/*     */   
/*     */   public void setMachineNumber(int machineNumber) {
/*  60 */     this.machineNumber = machineNumber;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActiveXComponent getZkem() {
/*  71 */     return this.zkem;
/*     */   }
/*     */   
/*     */   public boolean disconnect() {
/*  75 */     boolean b = this.zkem.invoke("Disconnect").getBoolean();
/*  76 */     if (b)
/*  77 */       this.connected = false; 
/*  78 */     System.out.println("Device Disconnect on " + getIp());
/*  79 */     return b;
/*     */   }
/*     */   
/*     */   private int toInt(Variant v) {
/*     */     try {
/*  84 */       return (v != null) ? Integer.parseInt(v.toString()) : 0;
/*  85 */     } catch (NumberFormatException e) {
/*  86 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*  90 */   private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); private ZKListener listener;
/*     */   
/*     */   public String getInfo(String infoName) {
/*  93 */     Variant info = new Variant("", true);
/*  94 */     if ("GetSDKVersion".equals(infoName) && 
/*  95 */       this.zkem.invoke(infoName, new Variant[] { info }).getBoolean())
/*  96 */       return info.toString(); 
/*  97 */     if (this.zkem.invoke(infoName, new Variant[] { new Variant(this.machineNumber), info }).getBoolean())
/*  98 */       return info.toString(); 
/*  99 */     throw new RuntimeException("Error on " + infoName);
/*     */   }
/*     */   
/*     */   public Map getInfo() {
/* 103 */     Map<Object, Object> infoMap = new HashMap<>();
/* 104 */     String[] infoNames = { "GetDeviceFirmwareVersion", "SerialNumber", "ProductCode", "FirmwareVersion", "SDKVersion", "DeviceIP", "DeviceMAC", "WiegandFmt", "Platform" };
/*     */     
/* 106 */     for (String infoName : infoNames) {
/*     */       try {
/* 108 */         infoMap.put(infoName, getInfo("Get" + infoName));
/* 109 */       } catch (Exception e) {
/* 110 */         infoMap.put(infoName, e.toString());
/*     */       } 
/* 112 */     }  return infoMap;
/*     */   }
/*     */   
/*     */   public List<LogData> getLogDataList(Date from, Date to) {
/* 116 */     List<LogData> list = new ArrayList<>();
/* 117 */     System.out.println("DESDE " + this.format.format(from));
/* 118 */     System.out.println("HASTA " + this.format.format(to));
/* 119 */     Calendar c = Calendar.getInstance();
/* 120 */     c.setTime(from);
/* 121 */     int fromInt = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
/* 122 */     c.setTime(to);
/* 123 */     int toInt = c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5);
/* 124 */     System.out.println(getIp() + ".EnableDevice " + this.zkem.invoke("EnableDevice", new Variant[] { new Variant(this.machineNumber), new Variant(false) }));
/*     */ 
/*     */     
/* 127 */     boolean readGeneralLogData = this.zkem.invoke("ReadGeneralLogData", new Variant[] { new Variant(this.machineNumber) }).getBoolean();
/* 128 */     System.out.println(getIp() + ".ReadGeneralLogData=" + readGeneralLogData);
/*     */     
/* 130 */     while (readGeneralLogData) {
/* 131 */       Variant enrollNumber = new Variant("", true);
/* 132 */       Variant verifyMode = new Variant(Integer.valueOf(0), true);
/* 133 */       Variant inOutMode = new Variant(Integer.valueOf(0), true);
/*     */       
/* 135 */       Variant year = new Variant(Integer.valueOf(0), true);
/* 136 */       Variant month = new Variant(Integer.valueOf(0), true);
/* 137 */       Variant day = new Variant(Integer.valueOf(0), true);
/*     */       
/* 139 */       Variant hour = new Variant(Integer.valueOf(0), true);
/* 140 */       Variant minute = new Variant(Integer.valueOf(0), true);
/* 141 */       Variant second = new Variant(Integer.valueOf(0), true);
/*     */       
/* 143 */       Variant workCode = new Variant(Integer.valueOf(0), true);
/*     */ 
/*     */ 
/*     */       
/* 147 */       readGeneralLogData = this.zkem.invoke("SSR_GetGeneralLogData", new Variant[] { new Variant(this.machineNumber), enrollNumber, verifyMode, inOutMode, year, month, day, hour, minute, second, workCode }).getBoolean();
/* 148 */       int peopleId = toInt(enrollNumber);
/* 149 */       if (peopleId > 0) {
/*     */         
/* 151 */         int y = toInt(year);
/* 152 */         int m = toInt(month);
/* 153 */         int d = toInt(day);
/*     */         
/* 155 */         int ymd = y * 10000 + m * 100 + d;
/* 156 */         if (ymd >= fromInt && ymd <= toInt) {
/* 157 */           LogData logData = new LogData();
/* 158 */           logData.setMachineNumber((this.machineNumber > 0) ? this.machineNumber : this.id);
/* 159 */           logData.setEnrollNumber(peopleId);
/* 160 */           logData.setVerifyMode(toInt(verifyMode));
/* 161 */           logData.setInOutMode(toInt(inOutMode));
/* 162 */           logData.setYear(y);
/* 163 */           logData.setMonth(m);
/* 164 */           logData.setDay(d);
/* 165 */           logData.setHour(toInt(hour));
/* 166 */           logData.setMinute(toInt(minute));
/* 167 */           logData.setSecond(toInt(second));
/* 168 */           logData.setWorkCode(toInt(workCode));
/* 169 */           short v = 3;
/* 170 */           list.add(logData); continue;
/* 171 */         }  if (ymd > toInt) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/* 176 */     System.out.println("list.size()=" + list.size());
/* 177 */     System.out.println(this.zkem.invoke("EnableDevice", new Variant[] { new Variant(this.machineNumber), new Variant(true) }));
/* 178 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ZKListener getListener() {
/* 184 */     return this.listener;
/*     */   }
/*     */   
/*     */   public void setListener(ZKListener listener) {
/* 188 */     this.listener = listener;
/*     */   }
/*     */   
/* 191 */   private STA sta = new STA();
/*     */   
/*     */   private DispatchEvents dispatchEvents;
/*     */   
/*     */   private ActiveXComponent zkem;
/*     */   
/*     */   public void run() {
/* 198 */     this.zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
/* 199 */     System.out.println("zkemkeeper.ZKEM.1 created");
/* 200 */     this.dispatchEvents = new DispatchEvents(this.zkem.getObject(), new SensorEvent(this));
/* 201 */     if (!connect()) {
/* 202 */       System.out.println("Can't connect to " + getIp());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean connect() {
/* 208 */     return this.zkem.invoke("Connect_NET", getIp(), 4370).getBoolean();
/*     */   }
/*     */   
/*     */   public void bindEvents() {
/* 212 */     System.out.println("OnConnected " + this.machineNumber + " on " + getIp());
/* 213 */     if (this.machineNumber >= 0) {
/* 214 */       boolean regEvent = this.zkem.invoke("RegEvent", new Variant[] { new Variant(this.machineNumber), new Variant(65535) }).getBoolean();
/* 215 */       System.out.println(getIp() + ".RegEvent=" + regEvent);
/* 216 */       if (regEvent) {
/* 217 */         System.out.println(getIp() + ".ReadRTLog=" + this.zkem.invoke("ReadRTLog", new Variant[] { new Variant(this.machineNumber) }));
/* 218 */         System.out.println(getIp() + ".ReadRTLog=" + this.zkem.invoke("GetRTLog", new Variant[] { new Variant(this.machineNumber) }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getUserList() {
/* 232 */     List<Map<Object, Object>> l = new ArrayList();
/* 233 */     System.out.println("getUserList for " + this.ip);
/* 234 */     boolean hasData = this.zkem.invoke("ReadAllUserID", new Variant[] { new Variant(this.machineNumber) }).getBoolean();
/* 235 */     int i = 0;
/* 236 */     while (hasData) {
/* 237 */       Variant enrollNumber = new Variant("", true);
/* 238 */       Variant name = new Variant("", true);
/* 239 */       Variant pass = new Variant("", true);
/* 240 */       Variant privilege = new Variant(Integer.valueOf(0), true);
/* 241 */       Variant enabled = new Variant(Boolean.valueOf(false), true);
/*     */       
/* 243 */       hasData = this.zkem.invoke("SSR_GetAllUserInfo", new Variant[] { new Variant(this.machineNumber), enrollNumber, name, pass, privilege, enabled }).getBoolean();
/* 244 */       Map<Object, Object> m = new HashMap<>();
/* 245 */       m.put("enrollNumber", enrollNumber.toString());
/* 246 */       m.put("name", name.toString());
/* 247 */       m.put("pass", pass.toString());
/* 248 */       m.put("privilege", privilege.toString());
/* 249 */       m.put("enabled", enabled.toString());
/* 250 */       l.add(m);
/*     */     } 
/* 252 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 257 */     return "ZK [machineNumber=" + this.machineNumber + ", ip=" + this.ip + ", id=" + this.id + ", connected=" + this.connected + "]";
/*     */   }
/*     */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/ZK.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */