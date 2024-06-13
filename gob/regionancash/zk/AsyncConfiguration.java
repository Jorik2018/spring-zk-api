/*    */ package BOOT-INF.classes.gob.regionancash.zk;
/*    */ 
/*    */ import java.util.concurrent.Executor;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.scheduling.annotation.EnableAsync;
/*    */ import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
/*    */ 
/*    */ @Configuration
/*    */ @EnableAsync
/*    */ public class AsyncConfiguration {
/* 14 */   private static final Logger LOGGER = LoggerFactory.getLogger(gob.regionancash.zk.AsyncConfiguration.class);
/*    */   @Bean(name = {"taskExecutor"})
/*    */   public Executor taskExecutor() {
/* 17 */     LOGGER.debug("Creating Async Task Executor");
/* 18 */     ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
/* 19 */     executor.setCorePoolSize(2);
/* 20 */     executor.setMaxPoolSize(2);
/* 21 */     executor.setQueueCapacity(100);
/* 22 */     executor.setThreadNamePrefix("CarThread-");
/* 23 */     executor.initialize();
/* 24 */     return (Executor)executor;
/*    */   }
/*    */ }


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/AsyncConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */