/*    */ package BOOT-INF.classes.gob.regionancash.zk;
/*    */ 
/*    */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*    */ import org.springframework.boot.builder.SpringApplicationBuilder;
/*    */ import org.springframework.scheduling.annotation.EnableScheduling;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SpringBootApplication
/*    */ @EnableScheduling
/*    */ public class ZkWebApplication
/*    */ {
/*    */   public static void main(String[] args) {
/* 30 */     (new SpringApplicationBuilder(new Class[] { gob.regionancash.zk.ZkWebApplication.class
/* 31 */         })).properties(new String[] { "spring.config.name:zk-web" }).build().run(args);
/*    */   }
/*    */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/ZkWebApplication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */