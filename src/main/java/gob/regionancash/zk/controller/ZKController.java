package gob.regionancash.zk;

import gob.regionancash.zk.LogData;
import gob.regionancash.zk.service.AttendanceService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZKController {
  
  @Autowired
  private AttendanceService attendanceService;

  @GetMapping({ "/{machine}" })
  public List<LogData> getWebSocket(@PathVariable Integer machine, @RequestParam(required = false) String from,
      @RequestParam(required = false) String to) {
    Date today = null, fromDate = null;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    if (to != null) {
      try {
        today = format.parse(to);
      } catch (ParseException parseException) {
      }
    }
    if (today == null)
      today = new Date();
    Calendar calendar = Calendar.getInstance();
    if (from == null) {
      calendar.setTime(today);
    } else {
      try {
        calendar.setTime(format.parse(from));
      } catch (ParseException parseException) {
      }
    }
    calendar.set(11, 0);
    calendar.set(12, 0);
    calendar.set(13, 0);
    return this.attendanceService.getLogDataList(machine.intValue(), calendar.getTime(), today);
  }

  @GetMapping({ "/{machine}/info" })
  public Object getInfo(@PathVariable Integer machine) {
    return this.attendanceService.getInfo(machine);
  }

  @GetMapping({ "/{machine}/users" })
  public List getUserList(@PathVariable Integer machine) {
    return this.attendanceService.getUserList(machine.intValue());
  }

  @GetMapping({ "/{machine}/disconnect" })
  public boolean disconnect(@PathVariable Integer machine) {
    return this.attendanceService.disconnect(machine.intValue());
  }

  @GetMapping({ "/{machine}/connect" })
  public boolean connect(@PathVariable Integer machine) {
    return this.attendanceService.connect(machine.intValue());
  }

  @GetMapping({ "/test" })
  public String test(@PathVariable Integer machine) {
    return "test:"+machine;
  }

  @CrossOrigin
  @PostMapping({ "/{machine}/sync-attendance" })
  public Object syncAttendance(@PathVariable Integer machine, @RequestBody SyncAttendanceForm form) {
    List l = this.attendanceService.syncAttendance(machine.intValue(), form.getFrom(), form.getTo());
    form.setSize(Integer.valueOf(l.size()));
    return form;
  }

  @CrossOrigin
  @PostMapping({ "/{machine}/sync" })
  public Object syncAttendance(@PathVariable Integer machine, @RequestBody HashMap<String, Integer> map)
      throws Exception {
    System.out.println(map);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date from = format.parse((String) map.get("from"));
    Date to = format.parse((String) map.get("to"));
    List l = this.attendanceService.syncAttendance(machine.intValue(), from, to);
    map.put("machine", machine);
    map.put("size", Integer.valueOf(l.size()));
    map.put("test", 12);
    return map;
  }
}

/*
 * Location:
 * /Users/ealarcop/Projects/java/spring/spring-zk-api/zk_web-0.0.1-SNAPSHOT.jar!
 * /BOOT-INF/classes/gob/regionancash/zk/controller/ZKController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version: 1.1.3
 */