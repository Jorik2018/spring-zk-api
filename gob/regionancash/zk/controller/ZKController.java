/*     */ package BOOT-INF.classes.gob.regionancash.zk.controller;
/*     */ 
/*     */ import gob.regionancash.zk.LogData;
/*     */ import gob.regionancash.zk.service.AttendanceService;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.web.bind.annotation.CrossOrigin;
/*     */ import org.springframework.web.bind.annotation.GetMapping;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.PostMapping;
/*     */ import org.springframework.web.bind.annotation.RequestBody;
/*     */ import org.springframework.web.bind.annotation.RequestParam;
/*     */ import org.springframework.web.bind.annotation.RestController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @RestController
/*     */ public class ZKController
/*     */ {
/*     */   @Autowired
/*     */   private AttendanceService attendanceService;
/*     */   
/*     */   @GetMapping({"/{machine}"})
/*     */   public List<LogData> getWebSocket(@PathVariable Integer machine, @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
/*  32 */     Date today = null, fromDate = null;
/*  33 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/*  34 */     if (to != null) {
/*     */       try {
/*  36 */         today = format.parse(to);
/*  37 */       } catch (ParseException parseException) {}
/*     */     }
/*  39 */     if (today == null) today = new Date(); 
/*  40 */     Calendar calendar = Calendar.getInstance();
/*  41 */     if (from == null) {
/*  42 */       calendar.setTime(today);
/*     */     } else {
/*     */       try {
/*  45 */         calendar.setTime(format.parse(from));
/*  46 */       } catch (ParseException parseException) {}
/*     */     } 
/*  48 */     calendar.set(11, 0);
/*  49 */     calendar.set(12, 0);
/*  50 */     calendar.set(13, 0);
/*  51 */     return this.attendanceService.getLogDataList(machine.intValue(), calendar.getTime(), today);
/*     */   }
/*     */   
/*     */   @GetMapping({"/{machine}/info"})
/*     */   public Object getInfo(@PathVariable Integer machine) {
/*  56 */     return this.attendanceService.getInfo(machine);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/test"})
/*     */   public void getUserList2() {}
/*     */ 
/*     */ 
/*     */   
/*     */   @GetMapping({"/{machine}/users"})
/*     */   public List getUserList(@PathVariable Integer machine) {
/*  68 */     return this.attendanceService.getUserList(machine.intValue());
/*     */   }
/*     */   
/*     */   @GetMapping({"/{machine}/disconnect"})
/*     */   public boolean disconnect(@PathVariable Integer machine) {
/*  73 */     return this.attendanceService.disconnect(machine.intValue());
/*     */   }
/*     */   
/*     */   @GetMapping({"/{machine}/connect"})
/*     */   public boolean connect(@PathVariable Integer machine) {
/*  78 */     return this.attendanceService.connect(machine.intValue());
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
/*     */   @CrossOrigin
/*     */   @PostMapping({"/{machine}/sync-attendance"})
/*     */   public Object syncAttendance(@PathVariable Integer machine, @RequestBody SyncAttendanceForm form) {
/* 120 */     List l = this.attendanceService.syncAttendance(machine.intValue(), form.getFrom(), form.getTo());
/* 121 */     form.setSize(Integer.valueOf(l.size()));
/* 122 */     return form;
/*     */   }
/*     */   
/*     */   @CrossOrigin
/*     */   @PostMapping({"/{machine}/sync"})
/*     */   public Object syncAttendance(@PathVariable Integer machine, @RequestBody HashMap<String, Integer> map) throws Exception {
/* 128 */     System.out.println(map);
/* 129 */     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
/* 130 */     Date from = format.parse((String)map.get("from"));
/* 131 */     Date to = format.parse((String)map.get("to"));
/* 132 */     List l = this.attendanceService.syncAttendance(machine.intValue(), from, to);
/* 133 */     map.put("machine", machine);
/* 134 */     map.put("size", Integer.valueOf(l.size()));
/* 135 */     return map;
/*     */   }
/*     */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/controller/ZKController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */