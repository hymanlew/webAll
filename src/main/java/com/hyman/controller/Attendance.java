package com.hyman.controller;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class Attendance {

    ///**
    // *
    // * @param constructionId
    // * @param areaId
    // * @param siteId
    // * @param identity
    // * @param companyId
    // * @param beginTime
    // * @param type
    // * @param name
    // * @param constructionName 建设单位企业名称
    // * @param address 工地地址
    // * @param build 施工单位
    // * @param category 项目类别
    // * @param pageNumber
    // * @param pageSize
    // * @param request
    // * @return
    // */

//    @RequestMapping("findAttendancePager")
//    @ResponseBody
//    public Map<String, Object> findAttendancePager(Integer constructionId, Integer areaId, String areaIds, Integer siteId, Integer groupId, String identity, Integer companyId, String beginTime, Integer type, String name, String constructionName, String address , String build, Integer category, Integer pageNumber, Integer pageSize, Integer siteState, HttpServletRequest request){
//
//        // 拿到日期
//        Calendar calendar = Calendar.getInstance();
//
//        // 格式化开始日期并设置进去，SimpleDateFormat
//        calendar.setTime(DateUtil.StrToDateWithPattern(beginTime,"yyyy-MM-dd"));
//
//        // 在当前日期上加 7天作为结束日期。与 DAY_OF_MONTH 作用相同，是指一个月中的某一天
//        calendar.add(Calendar.DATE, 7);
//
//        // calendar.getTime()，返回一个表示此 Calendar时间值的 date对象（结束日期）
//        Date endTime = calendar.getTime();
//
//        // 拿到建筑公司，工地，劳务公司 ID
//        if(request.getSession().getAttribute("constructionID")!=null&&TextUtil.notEmpty(request.getSession().getAttribute("constructionID").toString())){
//            constructionId=Integer.valueOf(request.getSession().getAttribute("constructionID").toString());
//        }
//        if(request.getSession().getAttribute("siteID")!=null&&TextUtil.notEmpty(request.getSession().getAttribute("siteID").toString())){
//            siteId=Integer.valueOf(request.getSession().getAttribute("siteID").toString());
//        }
//        if(request.getSession().getAttribute("companyID")!=null&&TextUtil.notEmpty(request.getSession().getAttribute("companyID").toString())){
//            companyId=Integer.valueOf(request.getSession().getAttribute("companyID").toString());
//        }
//
//        // 得到所有的员工，并分页 pagesize
//        List<Employee> employees = employeeService.findEmployeeAttendence(constructionId,areaId,areaIds,siteId,companyId,type,groupId,name,identity,constructionName,address,build,category,pageNumber,pageSize);
//        String employeeIds="";
//        List<Employee> employees1=new ArrayList<Employee>();
//
//        // 如果有工种条件，则按照工种查询，得到所有员工的 ID
//        if (type!=null){
//            for (int i=0;i<employees.size();i++){
//                if (employees.get(i).getTypeEntity().getPro()==type){
//                    employees1.add(employees.get(i));
//                }
//            }
//            for(Employee employee:employees1){
//                employeeIds+=employee.getId()+",";
//            }
//        }else {
//            for(Employee employee:employees){
//                employeeIds+=employee.getId()+",";
//            }
//            employees1=employees;
//        }
//
//        // 去掉最后一个逗号
//        if(TextUtil.notEmpty(employeeIds)){
//            employeeIds=employeeIds.substring(0,employeeIds.length()-1);
//        }
//
//        // 拿到考勤的数据
//        List<Attendance> list = attendanceService.findAttendancePager(constructionId,areaId,areaIds,siteId,groupId,companyId,DateUtil.StrToDateWithPattern(beginTime,"yyyy-MM-dd"),endTime,type,null,name,identity,null,employeeIds);
//
//        // 拿到员工的数量，用于分页
//        Integer count = employeeService.findEmployeeCountAttendance(constructionId, areaId,areaIds, siteId, companyId, type, groupId,name, identity, constructionName, address, build, category);
//
//        // attendanceResponces = list < List < List <Attendance> > >
//        List<AttendanceResponce> attendanceResponces = new ArrayList<AttendanceResponce>();
//
//        // 遍历每个员工
//        for(Employee employee:employees1){
//            // 用于存储工地中，每个人员的入职与离职时间初始化日期
//            List<DateRange> dateRangeList=null;
//
//            // 拿到指定工人的工地流转记录，入职与离职时间， SELECT * FROM zh_site_record WHERE employee_id=48
//            List<SiteRecord> recordList=employee.getSiteRecords();

//            if(recordList!=null&&recordList.size()>0&&siteId!=null){//时间范围与工地相关，如果没有选择工地，则全部日期都能查看
//                dateRangeList=new ArrayList<DateRange>();
//                for(SiteRecord record:recordList){
//
//                    // 拿到工地流转记录中的 time 和 leaving time，入职与离职时间，存入到 dateRangeList，下面要用
//                    // 初始化日期，设置指定日期为 0时0分0秒
//                    dateRangeList.add(new DateRange(record.getTime(),record.getLeaving()));
//                }
//            }
//
//            // 将每个员工与其打卡记录, 一一对应起来
//            AttendanceResponce attendanceResponce = new AttendanceResponce();
//
//            // AttendanceResponce ( List < List <Attendance> > )
//            // 将每个员工与其打卡记录, 一一对应起来
//            attendanceResponce.setEmployee(employee);
//
//            // 搜索的考勤时间范围，dateSplit 得到指定周期内所有的天数
//            List<Date> lists = dateSplit(DateUtil.StrToDateWithPattern(beginTime,"yyyy-MM-dd"), endTime);//查询的日期范围组成的list
//            List<List<Attendance>> lists2= new ArrayList<List<Attendance>>();//查询日期范围内，考勤记录的list；
//            if (!lists.isEmpty()) {
//
//                // 遍历考勤时间范围内的每一天
//                for (Date date : lists) {
//
//                    //有范围限制的，查看比较工地流转记录时间
//                    if(dateRangeList!=null){
//                        // 比较考勤时间 与 登记的离职时间，如果查询范围内该天已经离职，则添加两个空
//                        // inRange，比较两个日期的毫秒数
//                        if(!DateUtil.inRange(dateRangeList,date)){
//                            List<Attendance> noShow = new ArrayList<Attendance>();
//                            noShow.add(null);
//                            noShow.add(null);
//                            lists2.add(noShow);
//                            continue;
//                        }
//                    }
//                    //如果查询范围内该天尚未离职，则查询对应的考勤记录, 并放入每个人的考勤记录
//                    List<Attendance> list1 = new ArrayList<Attendance>();
//
//                    // 遍历所有的打卡记录, 每一天
//                    for(Attendance attendance : list){
//                        // 员工的考勤打卡时间
//                        String d1 = DateUtil.fmPattern(attendance.getDate(), "yyyy-MM-dd");
//
//                        // 搜索的考勤开始的时间
//                        String d2 = DateUtil.fmPattern(date, "yyyy-MM-dd");
//
//                        /**
//                         * intValue() 是java.lang.Number类的方法，Number是一个抽象类。Java中所有的数值类都继承它。也就是说不单是
//                         * Integer有intValue方法，Double，Long等都有此方法。
//                         *
//                         * 此方法的意思是：输出int数据。每个数值类中具体的实现是不同的。例如：Float类和Double类的intValue方法，就是丢
//                         * 掉了小数位，而Long的intValue方法又不一样的。
//                         */
//                        if(attendance.getEmployeeId().intValue()==employee.getId().intValue()&&d1.equals(d2)){
//                            // 将符合条件的打卡数据放入 list 中, 同一个人同一天的记录
//                            list1.add(attendance);
//                        }
//                    }
//                    List<Attendance> list2 = new ArrayList<Attendance>();
//
//                    // 因为attendance是时间倒序，因为是递归调用生成的, 所以保存的 attendance 时间数据也是倒序的
//                    if(list1.size()>=2){
//
//                        // list1 级别等同于 list2，并把第一个时间设为上班时间，最后一个时间设为下班时间
//                        list2.add(list1.get(list1.size()-1));
//                        list2.add(list1.get(0));
//                    }else if(list1.size()==1){
//                        list2.add(list1.get(0));
//                        list2.add(null);
//                    }else if(list1.size()==0){
//                        list2.add(null);
//                        list2.add(null);
//                    }
//                    lists2.add(list2);
//                }
//            }
//
//            // 将每个员工与其打卡记录, 一一对应起来， AttendanceResponce ( List < List <Attendance> > )
//            attendanceResponce.setList(lists2);
//
//            // attendanceResponces = list < List < List <Attendance> > >，包含两层 list
//            attendanceResponces.add(attendanceResponce);
//        }
//
//        //
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("list", attendanceResponces);
//        map.put("count", count);
//        return map;
//    }


    private static List<Date> dateSplit(Date startDate, Date endDate){
        // 获得指定时间段内的，毫秒数范围
        Long spi = endDate.getTime() - startDate.getTime();
        // 转换为天数范围
        Long step = spi / (24 * 60 * 60 * 1000);

        List<Date> dateList = new ArrayList<Date>();

        // 先加入结束的日期, 此时 datelist中只有一个日期
        dateList.add(endDate);
        for (int i = 1; i <= step; i++) {
            // 递归调用 datelist 表中的日期数据, 拿出来减一天再放进去, 再拿出来减一天再放进去 ,生成倒序的日期对象 date
            // new Date(long mills), 使用给定毫秒时间值构造一个 Date 对象。
            dateList.add(new Date(dateList.get(i - 1).getTime()
                    - (24 * 60 * 60 * 1000)));
        }
        return dateList;
    }


    /**
     * 导出考勤记录
     * @param hour 以多少小时为单位计算工时
     */
//    @RequestMapping("/exportAttendanceExcel")
//    public void exportAttendanceExcel(HttpServletRequest request, HttpServletResponse response, Integer constructionId, Integer areaId, String areaIds, Integer siteId, Integer groupId, Integer companyId, String endDate, String beginTime, Integer type, String name, String constructionName, String address , String build, Integer category, String identity, Integer hour, Integer siteState) throws UnsupportedEncodingException {
//        Calendar calendar = Calendar.getInstance();
//        if (TextUtil.isEmpty(beginTime)) {
//            calendar.add(Calendar.DAY_OF_MONTH, -7);
//            beginTime = DateUtil.fmPattern(calendar.getTime(), "yyyy-MM-dd");
//        }
//        Date endTime = null;
//        if (TextUtil.notEmpty(endDate)) {
//            endTime = DateUtil.StrToDateWithPattern(endDate, "yyyy-MM-dd");
//        } else {
//            calendar.setTime(DateUtil.StrToDateWithPattern(beginTime, "yyyy-MM-dd"));
//            calendar.add(Calendar.DATE, 7);
//            endTime = calendar.getTime();
//        }
//        if (request.getSession().getAttribute("constructionID") != null && TextUtil.notEmpty(request.getSession().getAttribute("constructionID").toString())) {
//            constructionId = Integer.valueOf(request.getSession().getAttribute("constructionID").toString());
//        }
////		if (request.getSession().getAttribute("siteID") != null && TextUtil.notEmpty(request.getSession().getAttribute("siteID").toString())) {
////			siteId = Integer.valueOf(request.getSession().getAttribute("siteID").toString());
////		}
//        if (request.getSession().getAttribute("companyID") != null && TextUtil.notEmpty(request.getSession().getAttribute("companyID").toString())) {
//            companyId = Integer.valueOf(request.getSession().getAttribute("companyID").toString());
//        }
//        if(name!=null){
//            name = new String( name.getBytes("ISO-8859-1") , "utf-8");
//        }
//        List<Employee> employees = new ArrayList<Employee>();
//
//        // 当建筑公司账号登录时，拿到下属所有工地的所有人员信息
//        if(siteId==null && constructionId!=null){
//            List<Site> siteLists = siteService.findSite(constructionId,null,null,null,null,null);
//            for(Site site:siteLists){
//                siteId = site.getId();
//                // 根据竣工状态拿到对应的数据
//                List<Employee> list = employeeService.findEmployeeAttendence(constructionId, areaId, areaIds,siteId, companyId, type, groupId,name, identity, constructionName, null, build, category, null, null,siteState);
//                employees.addAll(list);
//            }
//        }else{
//            // 根据竣工状态拿到对应的数据
//            employees = employeeService.findEmployeeAttendence(constructionId, areaId, areaIds,siteId, companyId, type, groupId,name, identity, constructionName, null, build, category, null, null,siteState);
//        }
//
//        //里面包含了以前曾经在该工地工作过的员工，这些员工的新
//        String employeeIds = "";
//        List<Employee> employees1=new ArrayList<Employee>();
//
//        // 按照具体工种来拿到所有的数据
//        if (type!=null){
//            for (int i=0;i<employees.size();i++){
//                if (employees.get(i).getTypeEntity().getPro()==type){
//                    employees1.add(employees.get(i));
//                }
//            }
//            for(Employee employee:employees1){
//                employeeIds+=employee.getId()+",";
//            }
//
//        // 拿到所有工种的数据
//        }else {
//            for(Employee employee:employees){
//                employeeIds+=employee.getId()+",";
//            }
//            employees1=employees;
//        }
//        employeeIds = employeeIds.substring(0, employeeIds.length() - 1);
//        List<AttendanceResponce> attendanceResponces = new ArrayList<AttendanceResponce>();
//        List<Date> lists = dateSplitASC(DateUtil.StrToDateWithPattern(beginTime, "yyyy-MM-dd"), endTime);
//        List<Integer>siteIds=new ArrayList<Integer>();//用于判断考勤是否是在本公司本工地的
//        if(constructionId!=null){
//            List<Site>siteList=siteService.findSite(constructionId,null,null,null,null,null);
//            if(siteList!=null){
//                for(Site site:siteList){
//                    siteIds.add(site.getId());
//                }
//            }
//        }
//        if(siteId!=null){
//            siteIds.add(siteId);
//        }
//        for (Employee employee : employees1) {
//            // 拿到员工列表中，每个员工的所有考勤信息，如果有分组则只查询分组数据
//            List<Attendance> empAttendanceList=attendanceService.findAttendanceMiniPager(null, null, null, groupId,null, DateUtil.StrToDateWithPattern(beginTime, "yyyy-MM-dd"), endTime, null, null, null, null, employee.getId(), null);
//            AttendanceResponce attendanceResponce = new AttendanceResponce();
//            attendanceResponce.setEmployee(employee);
//            List<List<Attendance>> lists2 = new ArrayList<List<Attendance>>();
//            if (!lists.isEmpty()) {
//                for (Date date : lists) {
//                    //如果查询范围内该天尚未离职，则查询对应的考勤记录,
//                    List<Attendance> list1 = new ArrayList<Attendance>();
//                    for (Attendance attendance : empAttendanceList) {
//                        String d1 = DateUtil.fmPattern(attendance.getDate(), "yyyy-MM-dd");
//                        String d2 = DateUtil.fmPattern(date, "yyyy-MM-dd");
//                        if (attendance.getEmployeeId().intValue() == employee.getId().intValue() && d1.equals(d2)) {
//                            if(attendance.getSiteId()!=null&&siteIds.contains(attendance.getSiteId())){
//                                list1.add(attendance);
//                            }
//                        }
//                    }
//                    List<Attendance> list2 = new ArrayList<Attendance>();
//                    if (list1.size() >= 2) {
//                        list2.add(list1.get(list1.size() - 1));//因为考勤信息是倒序查询的，所以后面的时间早，放第一个
//                        list2.add(list1.get(0));
//                    } else if (list1.size() == 1) {
//                        list2.add(list1.get(0));
//                        list2.add(null);
//                    } else if (list1.size() == 0) {
//                        list2.add(null);
//                        list2.add(null);
//                    }
//                    lists2.add(list2);
//                }
//            }
//            attendanceResponce.setList(lists2);
//            attendanceResponces.add(attendanceResponce);
//        }
//
//        List<List<String>> strsList = new ArrayList<List<String>>();
//        List<String> title = new ArrayList<String>();
//        title.add("员工姓名");
//        title.add("身份证号码");
//        title.add("考勤天数");
//        title.add("考勤异常天数");
//        if(hour!=null){
//            title.add("正常且满足工时要求天数");
//        }
//        for (Date date : lists) {
//            String str = DateUtil.fmPattern(date, "yyyy-MM-dd");
//            title.add(str);
//        }
//        strsList.add(title);
//        for (int i = 0; i < attendanceResponces.size(); i++) {
//            List<String> strs = new ArrayList<String>();
//            //姓名
//            if(attendanceResponces.get(i).getEmployee().getName()!=null){
//                strs.add(attendanceResponces.get(i).getEmployee().getName());
//            }else{
//                strs.add("");
//            }
//            //身份证
//            if(attendanceResponces.get(i).getEmployee().getIdentity()!=null){
//                strs.add(attendanceResponces.get(i).getEmployee().getIdentity());
//            }else{
//                strs.add("");
//            }
//
//            //考勤天数
//            strs.add(attendanceResponces.get(i).getList().size()+"");
//            //考勤异常天数
//            int abnormal=0;
//            List<List<Attendance>>attendanceList=attendanceResponces.get(i).getList();
//            for(List<Attendance> al:attendanceList){
//                if(al.get(1)==null||al.get(0)==null){
//                    abnormal++;
//                }
//            }
//            strs.add(abnormal+"");
//            //正常且满足工时要求天数
//            int qualified=0;
//            if(hour!=null){
//
//            }
//            for(int j=0;j<attendanceResponces.get(i).getList().size();j++){
//                String string = "";
//                for(int k=0;k<attendanceResponces.get(i).getList().get(j).size();k++){
//                    if(k==0){
//                        string+="上班：";
//                    }else{
//                        string+="    下班：";
//                    }
//                    if(attendanceResponces.get(i).getList().get(j).get(k)!=null){
//                        string += DateUtil.fmPattern(attendanceResponces.get(i).getList().get(j).get(k).getTime(), "HH:mm:ss");
//                        //如果两个都不为空，则计算工作时间
//                        if(hour!=null){
//                            if(k==1&&attendanceResponces.get(i).getList().get(j).get(0)!=null){
//                                long time=attendanceResponces.get(i).getList().get(j).get(1).getDateTime().getTime()-attendanceResponces.get(i).getList().get(j).get(0).getDateTime().getTime();
//                                if(time>hour*3600*1000){
//                                    qualified++;
//                                }
//                            }
//                        }
//
//                    }else{
//                        string += "            ";
//                    }
//
//                }
//                strs.add(string);
//
//            }
//            if(hour!=null) {
//                strs.add(4, qualified + "");
//            }
//            strsList.add(strs);
//        }
//        String fileName = "员工考勤信息";
//
//        response.reset();
//        response.setContentType("application/vnd.ms-excel;charset=utf-8");
//
//        try {
//            response.setHeader("Content-Disposition", "attachment;filename="
//                    + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
//
//            ServletOutputStream out = response.getOutputStream();
//            OutputStream os = out;
//            POIExcelUtil.writerDataInExcelIo(strsList, os);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}


