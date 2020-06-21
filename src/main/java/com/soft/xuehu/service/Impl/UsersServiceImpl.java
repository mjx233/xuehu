package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.mapper.UsersMapper;
import com.soft.xuehu.service.UsersService;
import com.soft.xuehu.transcation.MyException;
import com.soft.xuehu.util.UploadUsers;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By majianxin on 2020/5/10.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users get(String uid){
        Users users=null;
        users=usersMapper.selectByPrimaryKey(uid);
        return users;
    }

    @Override
    public void addUsers(Users users){
        usersMapper.addUsers(users);
    }

    @Override
    //获取一条用户数据
    public Users selectByUname(String uname){
        return usersMapper.selectByUname(uname);
    }

    @Override
    //  改
    public void update(Users users){
        usersMapper.update(users);
    }

    @Override
    public byte[] getPic(String uid) {

        byte[] pic = null;

        Users users= usersMapper.get(uid);

        pic = users.getPic();

        return pic;
    }

    @Override
    //查询所有用户
    public List<Users> selectAll(){
        return usersMapper.selectAll();
    }

    @Override
    public void remove(String uid){
        usersMapper.remove(uid);
    }

    @Override
    //excel上传
    public UploadUsers add(MultipartFile file) throws Exception{
        UploadUsers uploadUsers = new UploadUsers();
        int result =0;
        int fail = 0;
        String id = "\n";
        String name = "\n";

        //存放excel表中所有章节目录
        List<Users> usersList = new ArrayList<>();

        /**
         *
         * 判断文件版本
         */
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);

        InputStream ins = file.getInputStream();

        Workbook wb = null;

        if(suffix.equals("xlsx")){

            wb = new XSSFWorkbook(ins);

        }else{
            wb = new HSSFWorkbook(ins);
        }
        /**
         * 获取excel表单
         */
        Sheet sheet = wb.getSheetAt(0);


        /**
         * line = 1 :从表的第二行开始获取记录
         *
         */
        if(null != sheet){

            for(int line = 1; line <= sheet.getLastRowNum();line++){

                Users users = new Users();

                Row row = sheet.getRow(line);


                if(null == row){
                    continue;
                }
                /**
                 * 判断单元格类型是否为文本类型/数字类型
                 */
                if(1 != row.getCell(0).getCellType() && 0 != row.getCell(0).getCellType()){
                    throw new MyException("单元格类型不是文本类型或数字类型！");
                }

                /**
                 * 获取第一个单元格的内容
                 */
                String uid;
                if(1 == row.getCell(0).getCellType() ) {
                    uid = row.getCell(0).getStringCellValue();
                }
                else{
                    Integer c = (int)row.getCell(0).getNumericCellValue();
                    uid = c.toString();
                }

                //这条语句可以直接转换单元格的格式为String
                /*row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);*/
                /**
                 * 获取第二个单元格的内容
                 */
                String uname;
                if(1 == row.getCell(1).getCellType() ) {
                    uname = row.getCell(1).getStringCellValue();
                }
                else{
                    Integer c = (int)row.getCell(1).getNumericCellValue();
                    uname = c.toString();
                }
                /**
                 * 获取第三个单元格的内容
                 */
                String psw ;
                if(1 == row.getCell(2).getCellType() ) {
                    psw = row.getCell(2).getStringCellValue();
                }
                else{
                    Integer c = (int)row.getCell(2).getNumericCellValue();
                    psw = c.toString();
                }

                /**
                 * 获取第四个单元格的内容
                 */
                Integer role;
                if(1 == row.getCell(3).getCellType() ){
                    role = Integer.parseInt(row.getCell(3).getStringCellValue());
                }
                else{
                    role = (int)row.getCell(3).getNumericCellValue();
                }


                /**
                 * 设置users的字段
                 */
                users.setUid(uid);
                users.setUname(uname);
                users.setPsw(psw);
                users.setRole(role);
                users.setPic(null);
                usersList.add(users);
            }

            for(Users usersInfo:usersList){

                /**
                 * 判断数据库表中是否存在该账号或用户名，若存在，则不做改变，记录下来，不存在，则保存users
                 */
                String uid = usersInfo.getUid();
                String uname = usersInfo.getUname();

                Users users1 = usersMapper.selectByUname(uname);
                Users users2 = usersMapper.get(uid);

                if(users1 != null){
                    id = id+uid+"\n";
                    fail++;
                }
                else if(users2 != null){
                    name = name + uname+"\n";
                    fail++;
                }
                else{
                    usersMapper.addUsers(usersInfo);
                    result++;
                }
            }
        }
        uploadUsers.setResult(result);
        uploadUsers.setFail(fail);
        uploadUsers.setUid(id);
        uploadUsers.setUname(name);
        return uploadUsers;
    }


    @Override
    public XSSFWorkbook show(List<Users> usersList) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Users");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("用户id");//第一列
        titleRow.createCell(1).setCellValue("用户名");
        titleRow.createCell(2).setCellValue("密码");
        titleRow.createCell(3).setCellValue("身份");

        int cell = 1;
        for (Users users: usersList) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(users.getUid());//将数据库的数据遍历出来
            row.createCell(1).setCellValue(users.getUname());
            row.createCell(2).setCellValue(users.getPsw());
            row.createCell(3).setCellValue(users.getRole());//将数据库的数据遍历出来
            cell++;
        }
        return wb;
    }
}
