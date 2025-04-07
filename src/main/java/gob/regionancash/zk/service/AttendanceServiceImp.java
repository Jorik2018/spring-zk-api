package gob.regionancash.zk;

import gob.regionancash.rh.jpa.Attendance;
import gob.regionancash.zk.LogData;
import gob.regionancash.zk.ZK;
import gob.regionancash.zk.service.AttendanceService;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AttendanceServiceImp
  implements AttendanceService
{
  @Value("${intranet.domain}")
  private String domain;
  @Value("${device}")
  private String[] device;
  private HashMap<Object, ZK> zkMap = new HashMap<>();
  
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  
  @Autowired
  private TaskExecutor taskExecutor;
  
  private ZK.ZKListener zkListener = (ZK.ZKListener)new Object(this);

  @PostConstruct
  public void created() {
    String[] ips = this.device;
    int id = 1;
    for (String ip : ips) {
      ZK zk = new ZK();
      zk.setId(id++);
      System.out.println(ip);
      zk.setIp(ip);
      zk.setListener(this.zkListener);
      this.taskExecutor.execute((Runnable)zk);
      this.zkMap.put(Integer.valueOf(zk.getId()), zk);
    } 
  }
  
  public Object getInfo(Integer id) {
    ZK zk = this.zkMap.get(id);
    return zk.getInfo();
  }


  
  public List<LogData> getLogDataList(int id, Date from, Date to) {
    ZK zk = this.zkMap.get(Integer.valueOf(id));
    return zk.getLogDataList(from, to);
  }
  
  private RestTemplate restTemplate = new RestTemplate();

  
  public void syncAttendance() {
    for (Map.Entry<Object, ZK> entry : this.zkMap.entrySet()) {
      if (((ZK)entry.getValue()).isConnected()) {
        syncAttendance(((ZK)entry.getValue()).getId(), null, null);
      }
    } 
    
    try {
      ProcessBuilder p = new ProcessBuilder(new String[] { "D:\\microservicios\\test-zk\\test-zk.exe" });
      p.redirectOutput(ProcessBuilder.Redirect.INHERIT);
      p.start();
    } catch (IOException e) {
      System.out.println(e);
    } 
  }


  
  public List syncAttendance(int id, Date from, Date to) {
    if (to == null)
      to = new Date(); 
    Calendar calendar = Calendar.getInstance();
    if (from != null) {
      calendar.setTime(from);
      calendar.set(11, 0);
      calendar.set(12, 0);
      calendar.set(13, 0);
    } 
    from = calendar.getTime();
    ZK zk = this.zkMap.get(Integer.valueOf(id));
    calendar.setTime(to);
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    calendar.add(10, 24);
    calendar.add(13, -1);
    to = calendar.getTime();
    List<LogData> data = zk.getLogDataList(from, to);
    List<Attendance> attendanceList = new ArrayList<>();
    for (LogData logData : data) {
      Attendance a = new Attendance();
      a.setPeopleId(logData.getEnrollNumber());
      calendar.set(1, logData.getYear());
      calendar.set(2, logData.getMonth());
      calendar.set(5, logData.getDay());
      calendar.set(11, logData.getHour());
      calendar.set(12, logData.getMinute());
      calendar.set(13, logData.getSecond());
      a.setDateTime(calendar.getTime());
      a.setInOutMode(Integer.valueOf(logData.getInOutMode()));
      a.setMachineNumber(logData.getMachineNumber());
      a.setVerifyMode(logData.getVerifyMode());
      attendanceList.add(a);
    } 
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    System.out.println("SyncAttendance from=" + format.format(from) + ";to=" + format.format(to) + ";send " + data.size());
    //String resultado = ">>>";//postForObject(data);
    System.out.println("attendanceList.size()=" + attendanceList.size());
    return attendanceList;
  }
  
  private String postForObject(Object data) {
    try {
      if (data instanceof List)
        System.out.println("post to" + this.domain + "/admin/rh/api/attendance/bulk=" + ((List)data).size()); 
      return (String)this.restTemplate.postForObject(this.domain + "/admin/rh/api/attendance/bulk", data, String.class, new Object[0]);
    } catch (Exception ex) {
      System.out.println("ERROR POST TO " + this.domain + "/admin/rh/api/attendance/bulk -> " + ex.toString());
      return null;
    } 
  }

  
  public List getUserList(int id) {
    ZK zk = this.zkMap.get(Integer.valueOf(id));
    return zk.getUserList();
  }

  
  public boolean disconnect(int id) {
    ZK zk = this.zkMap.get(Integer.valueOf(id));
    return zk.disconnect();
  }

  
  public boolean connect(int id) {
    ZK zk = this.zkMap.get(Integer.valueOf(id));
    zk.connect();
    return true;
  }
}


/* Location:              /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes/gob/regionancash/zk/service/AttendanceServiceImp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */