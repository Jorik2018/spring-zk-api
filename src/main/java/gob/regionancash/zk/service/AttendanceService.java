package gob.regionancash.zk;

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