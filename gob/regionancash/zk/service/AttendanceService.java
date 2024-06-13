package BOOT-INF.classes.gob.regionancash.zk.service;

import gob.regionancash.zk.LogData;
import java.util.Date;
import java.util.List;

public interface AttendanceService {
  List<LogData> getLogDataList(int paramInt, Date paramDate1, Date paramDate2);
  
  List syncAttendance(int paramInt, Date paramDate1, Date paramDate2);
  
  List getUserList(int paramInt);
  
  boolean disconnect(int paramInt);
  
  boolean connect(int paramInt);
  
  Object getInfo(Integer paramInteger);
}


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/service/AttendanceService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */