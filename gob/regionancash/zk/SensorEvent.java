/*     */ package BOOT-INF.classes.gob.regionancash.zk;
/*     */ 
/*     */ import com.jacob.com.Variant;
/*     */ import gob.regionancash.zk.LogData;
/*     */ import gob.regionancash.zk.ZK;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SensorEvent {
/*     */   private ZK zk;
/*     */   
/*     */   public SensorEvent(ZK zk) {
/*  13 */     this.zk = zk;
/*     */   }
/*     */   
/*     */   public void log(String eventName, Variant[] variants) {
/*  17 */     List<String> l = new ArrayList();
/*  18 */     for (Variant v : variants) {
/*  19 */       l.add(v.toString());
/*     */     }
/*  21 */     System.out.println("SensorEvent->" + eventName + ": " + String.join(", ", (Iterable)l));
/*     */   }
/*     */   
/*     */   public void OnConnected(Variant[] arge) {
/*  25 */     this.zk.setConnected(true);
/*  26 */     this.zk.setMachineNumber(toInt((arge.length > 0) ? arge[0] : null));
/*  27 */     this.zk.getListener().OnConnected(this.zk);
/*     */   }
/*     */   
/*     */   public void OnDisConnected(Variant[] arge) {
/*  31 */     log("OnDisConnected on " + this.zk.getIp(), arge);
/*  32 */     this.zk.setConnected(false);
/*     */   }
/*     */   
/*     */   public void OnAlarm(Variant[] arge) {
/*  36 */     log("OnConnected", arge);
/*     */   }
/*     */   
/*     */   private int toInt(Variant v) {
/*     */     try {
/*  41 */       return (v != null) ? Integer.parseInt(v.toString()) : 0;
/*  42 */     } catch (NumberFormatException e) {
/*  43 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void OnAttTransactionEx(Variant[] arge) {
/*  48 */     if (this.zk.getListener() == null)
/*  49 */       return;  log(this.zk.getIp() + ".OnAttTransactionEx", arge);
/*  50 */     int enrollNumber = toInt(arge[0]);
/*  51 */     if (enrollNumber > 0) {
/*  52 */       LogData logData = new LogData();
/*  53 */       logData.setMachineNumber(this.zk.getMachineNumber());
/*  54 */       if (logData.getMachineNumber() == 0) logData.setMachineNumber(this.zk.getId()); 
/*  55 */       logData.setEnrollNumber(enrollNumber);
/*  56 */       logData.setVerifyMode(toInt(arge[3]));
/*  57 */       logData.setInOutMode(0);
/*  58 */       logData.setYear(toInt(arge[4]));
/*  59 */       logData.setMonth(toInt(arge[5]));
/*  60 */       logData.setDay(toInt(arge[6]));
/*  61 */       logData.setHour(toInt(arge[7]));
/*  62 */       logData.setMinute(toInt(arge[8]));
/*  63 */       logData.setSecond(toInt(arge[9]));
/*  64 */       logData.setWorkCode(toInt(arge[10]));
/*  65 */       this.zk.getListener().OnAttTransactionEx(this.zk, logData);
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
/*     */   public void OnEnrollFingerEx(Variant[] arge) {
/*  77 */     log("OnEnrollFingerEx", arge);
/*     */   }
/*     */ 
/*     */   
/*     */   public void OnFinger(Variant[] arge) {
/*  82 */     log("OnFinger", arge);
/*     */   }
/*     */   
/*     */   public void OnFingerFeature(Variant[] arge) {
/*  86 */     log("OnFingerFeature", arge);
/*     */   }
/*     */   
/*     */   public void OnHIDNum(Variant[] arge) {
/*  90 */     log("OnHIDNum", arge);
/*     */   }
/*     */   
/*     */   public void OnNewUser(Variant[] arge) {
/*  94 */     log("OnNewUser", arge);
/*     */   }
/*     */   
/*     */   public void OnVerify(Variant[] arge) {
/*  98 */     log("OnVerify", arge);
/*     */   }
/*     */   
/*     */   public void OnWriteCard(Variant[] arge) {
/* 102 */     log("OnWriteCard", arge);
/*     */   }
/*     */   
/*     */   public void OnEmptyCard(Variant[] arge) {
/* 106 */     log("OnEmptyCard", arge);
/*     */   }
/*     */   
/*     */   public void OnEMData(Variant[] arge) {
/* 110 */     log("OnEmptyCard", arge);
/*     */   }
/*     */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/SensorEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */