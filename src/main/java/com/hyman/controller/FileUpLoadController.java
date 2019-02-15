package com.hyman.controller;

import com.hyman.util.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("upload")
public class FileUpLoadController {


    @RequestMapping("/excel_upload")
    @ResponseBody
    public String excel_upload(@RequestParam(value = "doc", required = false) List<MultipartFile> excelPath
            ,HttpServletRequest request){
        String result="";

        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //定义的文件存放相对路径
        String uploadPath = "uploadFile/excel/bank/"+userInfo.getId()+"/";

        //定义文件存放的绝对路径
        String path =request.getSession().getServletContext().getRealPath("/").replace("webapps\\building\\","")+uploadPath;

        /**
         * MultipartFile 的：
         * getFileName 方法是用于一次获取多个上传的文件名字时,得到的不是文件的名字,而是 input 的name属性
         * 而 getOriginalFilename : 是获取上传文件的原名
         */
        String  fileName = excelPath.get(0).getOriginalFilename();
        String timeStamp=(new Date()).getTime()+"";
        try {
            File userFile = new File(path);
            if(!userFile.exists()){
                userFile.mkdirs();
            }
            File file=new File(path+timeStamp+fileName.substring(fileName.lastIndexOf(".")));

            /**
             * 在MultipartFile接口中定义了如下很多有用的方法。
             *
             * 使用 getSize()方法获得文件长度，以此决定允许上传的文件大小。
             * 使用 isEmpty()方法判断上传文件是否为空文件，以此决定是否拒绝空文件。
             * 使用 getInputStream()方法将文件读取为java.io.InputStream流对象。
             * 使用 getContentType()方法获得文件类型，以此决定允许上传的文件类型。
             * 使用 transferTo（dest）方法将上传文件写到服务器上指定的文件。
             */
            excelPath.get(0).transferTo(file);
            result=uploadPath+timeStamp+fileName.substring(fileName.lastIndexOf("."));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="上传失败";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            result="上传失败";
        }
        return result;
    }

    // 自定义一个内存空间，用于存储数据（即缓存）
    public static Map<String,Object> salaryRecordResponceMap=new HashMap<String,Object>();

//    public Map<String,Object> importSalaryByPoi(String filePath, Integer siteId, HttpServletRequest request) {
//
//        // 拿到银行登录的对应的 bank信息（id）
//        UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Integer userId = userInfo.getId();
//
//        // 银行表
//        Bank bank =bankDao.findByUserId(userId);
//
//        // 拿到文件在磁盘中的绝对路径
//        String path =request.getSession().getServletContext().getRealPath("");
//        path = path.replaceAll("\\\\", "/");
//        String url = path+"/"+filePath;
//
//        // 解析文件，读取数据（所有员工的银行工资发放记录）
//        List<SalaryRecordResponce> list = POIExcelUtil.importSalaryByPoi(url);//表格中读取到的数据
//
//
//        //将读出的数据存入内存，根据 bankId 来存储对应的工资记录
//        if(salaryRecordResponceMap.get(bank.getId()+"list")==null){
//            salaryRecordResponceMap.put(bank.getId()+"list",list);
//        }else {
//            //将内存中原有的数据清除，放入新的
//            salaryRecordResponceMap.remove(bank.getId()+"list");
//            salaryRecordResponceMap.put(bank.getId()+"list",list);
//        }
//
//        // 从数据库中读取到当前工地的所有员工
//        PropSet propSet1 = new PropSet();
//        if(siteId!=null){
//            propSet1.setObj2(siteId);
//        }
//        // 员工表
//        List<Employee> employees = employeeDao.findEmployeeSingle(propSet1);
//
//        // 将银行上传的员工信息与数据库数据一一比对（比对名字），有则过，系统中没有则添加到新的员工工资 list中（more）
//        List<SalaryRecordResponce> more = new ArrayList<SalaryRecordResponce>();//存储列表中有，数据库中没有的异常数据
//        for (SalaryRecordResponce salaryRecordResponce:list){
//            for (int i=0;i<employees.size();i++){
//
//                // 如果有该员工信息，则跳过当前员工（跳出循环 break），比对下一员工
//                if (salaryRecordResponce.getName().equals(employees.get(i).getName())){
//                    break;
//                }else {
//                    if (i==(employees.size()-1)){
//                        more.add(salaryRecordResponce);
//                    }
//                }
//            }
//        }
//
//        // 将数据库数据与银行上传的员工信息一一比对（比对名字），有则过，银行系统没有则添加到新的员工工资 list中（less）
//        List<Employee> less = new ArrayList<Employee>();//存储列表中没有，数据库中有的异常数据
//        for (Employee employee:employees){
//            List<SalaryRecordResponce> emps = new ArrayList<>();
//            for (int i=0;i<list.size();i++){
//
//                // 如果有该员工信息，则跳过当前员工（跳出循环 break），比对下一员工
//                if (list.get(i).getName().equals(employee.getName())){
//                    break;
//                }
//                else {
//                    if (i==(list.size()-1)){
//                        less.add(employee);
//                    }
//                }
//            }
//
//        }
//
//        // 当上传的文件数据中员工，没有考勤记录的数据，存入 list
//        List<SalaryRecordResponce> noAttendance = new ArrayList<SalaryRecordResponce>();
//        for (SalaryRecordResponce salaryRecordResponce:list){
//            PropSet propSet = new PropSet();
//            if(siteId!=null){
//                propSet.setObj2(siteId);
//            }
//            propSet.setObj4(DateUtil.StrToDate(DateUtil.getFirstDay()));
//            propSet.setObj5(DateUtil.StrToDate(DateUtil.getLastDay()));
//            propSet.setObj7(salaryRecordResponce.getName());
//            propSet.setObj9(salaryRecordResponce.getIdentity());
//
//            // 考勤表
//            int attendance = attendanceDao.findAttendancePagerCount(propSet);
//            if (attendance==0){
//                noAttendance.add(salaryRecordResponce);
//            }
//        }
//        SalaryRecordBank salaryRecordBank = new SalaryRecordBank();
//        salaryRecordBank.setTime(new Date());
//        salaryRecordBank.setFile(filePath);
//        salaryRecordBank.setBankId(bank.getId());
////        salaryRecordBank.setRemark(result);
//        salaryRecordBank.setSiteId(siteId);
//
//        // 记录存储，上传的员工工资记录中，系统工地中没有该员工
//        salaryRecordBank.setMore(more.size());//多
//
//        // 记录存储，上传的员工工资记录中，系统工地考勤中没有考勤记录的员工
//        salaryRecordBank.setLess(less.size());//少
//
//        // 上传的员工的人数
//        salaryRecordBank.setCount(list.size());
//
//        // 银行上传记录表
//        salaryRecordBankDao.save(salaryRecordBank);
//
//        // 银行系统中有该员工，但本项目系统中无
//        for(SalaryRecordResponce salaryRecordResponce:more){
//            SalaryRecordDetail salaryRecordDetail = new SalaryRecordDetail();
//            salaryRecordDetail.setRecordId(salaryRecordBank.getId());
//            salaryRecordDetail.setName(salaryRecordResponce.getName());
//            salaryRecordDetail.setIdentify(salaryRecordResponce.getIdentity());
//            salaryRecordDetail.setType(2);
//
//            // 银行薪资信息与当前系统中员工信息的对比，记录表
//            salaryRecordDetailDao.save(salaryRecordDetail);
//        }
//
//        // 本项目系统中无考勤
//        for(SalaryRecordResponce salaryRecordResponce:noAttendance){
//            SalaryRecordDetail salaryRecordDetail = new SalaryRecordDetail();
//            salaryRecordDetail.setRecordId(salaryRecordBank.getId());
//            salaryRecordDetail.setName(salaryRecordResponce.getName());
//            salaryRecordDetail.setIdentify(salaryRecordResponce.getIdentity());
//            salaryRecordDetail.setType(1);
//            salaryRecordDetailDao.save(salaryRecordDetail);
//        }
//
//        //银行系统发放工资记录中没有该员工，但本项目系统中有
//        for(Employee employee:less){
//            SalaryRecordDetail salaryRecordDetail = new SalaryRecordDetail();
//            salaryRecordDetail.setRecordId(salaryRecordBank.getId());
//            salaryRecordDetail.setName(employee.getName());
//            salaryRecordDetail.setIdentify(employee.getIdentity());
//            salaryRecordDetail.setType(3);
//            salaryRecordDetailDao.save(salaryRecordDetail);
//        }
//
//        //保留本次匹配结果的数据库中的id，操作人员确定操作后生成SalaryRecordSite,本次匹配结果要与生成的结果关联
//        if(salaryRecordResponceMap.get(bank.getId()+"salaryRecordBankId")==null){
//            salaryRecordResponceMap.put(bank.getId()+"salaryRecordBankId",salaryRecordBank.getId());
//        }else {
//            salaryRecordResponceMap.remove(bank.getId()+"salaryRecordBankId");
//            salaryRecordResponceMap.put(bank.getId()+"salaryRecordBankId",salaryRecordBank.getId());
//        }
//        Map<String,Object> map = new HashMap<>();
//        map.put("more",more);
//        map.put("less",less);
//        map.put("noAttendance",noAttendance);
//        map.put("total",list.size());
//        return map;
//    }
//
//    public String importSalaryPoi(Integer siteId) {
//        double totalMoney = 0;
//
//        // 拿到当前登录银行的信息，id
//        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Integer userId = userInfo.getId();
//        Bank bank =bankDao.findByUserId(userId);
//
//        // 从缓存中拿到当前银行对应的，所上传的文件数据
//        for(SalaryRecordResponce salaryRecordResponce:(List<SalaryRecordResponce>)salaryRecordResponceMap.get(bank.getId()+"list")){
//            // 计算出发放工资的总金额
//            totalMoney += Double.parseDouble(salaryRecordResponce.getMoney());
//        }
//        SalaryRecordSite salaryRecordSite = new SalaryRecordSite();
//        salaryRecordSite.setMoney(totalMoney);
//        salaryRecordSite.setSiteId(siteId);
//        salaryRecordSite.setTime(new Date());
//        salaryRecordSite.setState(1);
//        salaryRecordSite.setBankId(bank.getId());
//
//        // salaryRecordSite：工地工资发放记录表
//        salaryRecordSiteDao.save(salaryRecordSite);
//
//        Integer salaryRecordSiteId = salaryRecordSite.getId();//工地总发放记录id
//
//        // 银行上传数据对比后的记录表，从中拿到当前银行对应的，所上传的文件数据与当前系统中数据对比后的结果数据
//        SalaryRecordBank salaryRecordBank = salaryRecordBankDao.get(salaryRecordResponceMap.get(bank.getId()+"salaryRecordBankId")+"");
//        salaryRecordBank.setRecordId(salaryRecordSiteId);
//
//        // 仅当当前银行账号确认提交时才去添加，工资发放记录表的数据 id，用于外键
//        salaryRecordBankDao.update(salaryRecordBank);
//
//        // 遍历当前银行账号上传的工资发放的数据，每个员工数据
//        for(SalaryRecordResponce salaryRecordResponce:(List<SalaryRecordResponce>)salaryRecordResponceMap.get(bank.getId()+"list")){
//            //根据工地id，员工姓名，员工身份证，员工卡号获取员工
//            List<Employee> employees = employeeDao.findEmployee(null,salaryRecordResponce.getName().trim(),null,salaryRecordResponce.getBankCard().trim());
//            SalaryRecord salaryRecord = null;
//            if (employees.size()==1){
//                salaryRecord = new SalaryRecord();
//                salaryRecord.setTime(new Date());
//
//                // 仅当员工更改银行卡号时，才直接存银行上传的卡号信息
//                if(salaryRecordResponce.getBankCard()!=null&&!salaryRecordResponce.getBankCard().equals(employees.get(0).getBankCard())){
//                    salaryRecord.setCard(salaryRecordResponce.getBankCard());
//                }else {
//                    salaryRecord.setCard(employees.get(0).getBankCard());
//                }
//                salaryRecord.setMoney(Double.parseDouble(salaryRecordResponce.getMoney()));
//                salaryRecord.setEmployeeId(employees.get(0).getId());
//                salaryRecord.setName(salaryRecordResponce.getName());
//                salaryRecord.setIdentity(employees.get(0).getIdentity());
//                salaryRecord.setRecordId(salaryRecordSiteId);
//                salaryRecordDao.save(salaryRecord);
//            }else{
//                salaryRecord = new SalaryRecord();
//                salaryRecord.setTime(new Date());
//                salaryRecord.setCard(salaryRecordResponce.getBankCard());
//                salaryRecord.setMoney(Double.parseDouble(salaryRecordResponce.getMoney()));
//                salaryRecord.setName(salaryRecordResponce.getName());
//                salaryRecord.setIdentity(salaryRecordResponce.getIdentity());
//                salaryRecord.setRecordId(salaryRecordSiteId);
//                salaryRecordDao.save(salaryRecord);
//            }
//        }
//        return "success";
//    }
}
