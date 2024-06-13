/*     */ package BOOT-INF.classes.gob.regionancash.zk.service;
/*     */ 
/*     */ import gob.regionancash.rh.jpa.Attendance;
/*     */ import gob.regionancash.zk.LogData;
/*     */ import gob.regionancash.zk.ZK;
/*     */ import gob.regionancash.zk.service.AttendanceService;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.PostConstruct;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.beans.factory.annotation.Value;
/*     */ import org.springframework.core.task.TaskExecutor;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.web.client.RestTemplate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Service
/*     */ public class AttendanceServiceImp
/*     */   implements AttendanceService
/*     */ {
/*     */   @Value("${intranet.domain}")
/*     */   private String domain;
/*     */   @Value("${device}")
/*     */   private String[] device;
/*  37 */   private HashMap<Object, ZK> zkMap = new HashMap<>();
/*     */   
/*  39 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
/*     */   
/*     */   @Autowired
/*     */   private TaskExecutor taskExecutor;
/*     */   
/*  44 */   private ZK.ZKListener zkListener = (ZK.ZKListener)new Object(this);
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
/*     */   @PostConstruct
/*     */   public void created() {
/*  64 */     String[] ips = this.device;
/*  65 */     int id = 1;
/*  66 */     for (String ip : ips) {
/*  67 */       ZK zk = new ZK();
/*  68 */       zk.setId(id++);
/*  69 */       System.out.println(ip);
/*  70 */       zk.setIp(ip);
/*  71 */       zk.setListener(this.zkListener);
/*  72 */       this.taskExecutor.execute((Runnable)zk);
/*  73 */       this.zkMap.put(Integer.valueOf(zk.getId()), zk);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getInfo(Integer id) {
/*  78 */     ZK zk = this.zkMap.get(id);
/*  79 */     return zk.getInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<LogData> getLogDataList(int id, Date from, Date to) {
/*  85 */     ZK zk = this.zkMap.get(Integer.valueOf(id));
/*  86 */     return zk.getLogDataList(from, to);
/*     */   }
/*     */   
/*  89 */   private RestTemplate restTemplate = new RestTemplate();
/*     */ 
/*     */   
/*     */   public void syncAttendance() {
/*  93 */     for (Map.Entry<Object, ZK> entry : this.zkMap.entrySet()) {
/*  94 */       if (((ZK)entry.getValue()).isConnected()) {
/*  95 */         syncAttendance(((ZK)entry.getValue()).getId(), null, null);
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 100 */       ProcessBuilder p = new ProcessBuilder(new String[] { "D:\\microservicios\\test-zk\\test-zk.exe" });
/* 101 */       p.redirectOutput(ProcessBuilder.Redirect.INHERIT);
/* 102 */       p.start();
/* 103 */     } catch (IOException e) {
/* 104 */       System.out.println(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List syncAttendance(int id, Date from, Date to) {
/* 111 */     if (to == null)
/* 112 */       to = new Date(); 
/* 113 */     Calendar calendar = Calendar.getInstance();
/* 114 */     if (from != null) {
/* 115 */       calendar.setTime(from);
/* 116 */       calendar.set(11, 0);
/* 117 */       calendar.set(12, 0);
/* 118 */       calendar.set(13, 0);
/*     */     } 
/* 120 */     from = calendar.getTime();
/* 121 */     ZK zk = this.zkMap.get(Integer.valueOf(id));
/* 122 */     calendar.setTime(to);
/* 123 */     calendar.set(11, 0);
/* 124 */     calendar.set(12, 0);
/* 125 */     calendar.set(13, 0);
/* 126 */     calendar.add(10, 24);
/* 127 */     calendar.add(13, -1);
/* 128 */     to = calendar.getTime();
/* 129 */     List<LogData> data = zk.getLogDataList(from, to);
/* 130 */     List<Attendance> attendanceList = new ArrayList<>();
/* 131 */     for (LogData logData : data) {
/* 132 */       Attendance a = new Attendance();
/* 133 */       a.setPeopleId(logData.getEnrollNumber());
/* 134 */       calendar.set(1, logData.getYear());
/* 135 */       calendar.set(2, logData.getMonth());
/* 136 */       calendar.set(5, logData.getDay());
/* 137 */       calendar.set(11, logData.getHour());
/* 138 */       calendar.set(12, logData.getMinute());
/* 139 */       calendar.set(13, logData.getSecond());
/* 140 */       a.setDateTime(calendar.getTime());
/* 141 */       a.setInOutMode(Integer.valueOf(logData.getInOutMode()));
/* 142 */       a.setMachineNumber(logData.getMachineNumber());
/* 143 */       a.setVerifyMode(logData.getVerifyMode());
/* 144 */       attendanceList.add(a);
/*     */     } 
/* 146 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 147 */     System.out.println("SyncAttendance from=" + format.format(from) + ";to=" + format.format(to) + ";send " + data.size());
/* 148 */     String resultado = postForObject(data);
/* 149 */     System.out.println("attendanceList.size()=" + attendanceList.size());
/* 150 */     return attendanceList;
/*     */   }
/*     */   
/*     */   private String postForObject(Object data) {
/*     */     try {
/* 155 */       if (data instanceof List)
/* 156 */         System.out.println("post to" + this.domain + "/admin/rh/api/attendance/bulk=" + ((List)data).size()); 
/* 157 */       return (String)this.restTemplate.postForObject(this.domain + "/admin/rh/api/attendance/bulk", data, String.class, new Object[0]);
/* 158 */     } catch (Exception ex) {
/* 159 */       System.out.println("ERROR POST TO " + this.domain + "/admin/rh/api/attendance/bulk -> " + ex.toString());
/* 160 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List getUserList(int id) {
/* 166 */     ZK zk = this.zkMap.get(Integer.valueOf(id));
/* 167 */     return zk.getUserList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean disconnect(int id) {
/* 172 */     ZK zk = this.zkMap.get(Integer.valueOf(id));
/* 173 */     return zk.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean connect(int id) {
/* 178 */     ZK zk = this.zkMap.get(Integer.valueOf(id));
/* 179 */     zk.connect();
/* 180 */     return true;
/*     */   }
/*     */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/service/AttendanceServiceImp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */