package com.soft.xuehu.service.Impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.mapper.HworkMapper;
import com.soft.xuehu.service.HworkService;
import com.soft.xuehu.transcation.MyException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


/**
 * Create By majianxin on 2020/5/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class HworkServiceImpl implements HworkService {

    @Autowired
    HworkMapper hworkMapper;

    @Override
    //    增
    public int addHwork(MultipartFile file) throws Exception{
        int result = 0;
//		存放excel表中所有Hwork细腻
        List<Hwork> hworkList = new ArrayList<>();
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

                Hwork hwork = new Hwork();

                Row row = sheet.getRow(line);


                if(null == row){
                    continue;
                }
                System.out.println("ddddd"+row.getCell(0).getCellType());
                /**
                 * 判断单元格类型是否为文本类型/数字类型
                 */
                if(1 != row.getCell(0).getCellType() && 0 != row.getCell(0).getCellType()){
                    throw new MyException("单元格类型不是文本类型或数字类型！");
                }

                /**
                 * 获取第一个单元格的内容
                 */
                //因为单元格类型为double，所以要先用getNumericCellValue()方法获取后再转换为字符串
                Integer c = (int)row.getCell(0).getNumericCellValue();
                String uid = c.toString();

                //这条语句可以直接转换单元格的格式为String
                /*row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);*/
                /**
                 * 获取第二个单元格的内容
                 */
                c = (int)row.getCell(1).getNumericCellValue();
                String courseNo = c.toString();

                /**
                 * 获取第三个单元格的内容
                 */
                c = (int)row.getCell(2).getNumericCellValue();
                String hNum = c.toString();

                /**
                 * 获取第四个单元格的内容
                 */
                String hMessage = row.getCell(3).getStringCellValue();

                /**
                 * 获取第五个单元格的内容
                 */
                String Time = row.getCell(4).getStringCellValue();
                SimpleDateFormat srtFormat = new SimpleDateFormat("yyyy/m/d h:mm");
                String endTime= srtFormat.format(Time);
                Date endTime1 = srtFormat.parse(endTime);
                Date finishTime = endTime1;

                /**
                 * 设置开始时间
                 */
                Date date =new Date();
                SimpleDateFormat srtFormat1 = new SimpleDateFormat("yyyy/m/d h:mm");
                String nowTime = srtFormat.format(date);
                Date time = srtFormat.parse(nowTime);
                Date startTime = time;

                hwork.setUid(uid);
                hwork.setCourseNo(courseNo);
                hwork.sethNum(hNum);
                hwork.sethMessage(hMessage);
                hwork.setStartTime(startTime);
                hwork.setFinishTime(finishTime);
                hworkList.add(hwork);
            }

            for(Hwork hworkInfo:hworkList){

                /**
                 * 判断数据库表中是否存在作业记录，若存在，则更新，不存在，则保存记录
                 */
                String hNum = hworkInfo.gethNum();
                String uid = hworkInfo.getUid();

                int count = hworkMapper.selectHwork(hNum,uid);

                if(0 == count){
                    result = hworkMapper.addHwork(hworkInfo);
                }else{
                    result = hworkMapper.updateHwork(hworkInfo);
                }
            }
        }

        return result;
    }

    @Override
    //按照课程编号查询作业
    public List<Hwork> selectBycourseNo(String courseNo){
        return hworkMapper.selectBycourseNo(courseNo);
    }

    @Override
    //    增
    public int addHwork(com.soft.xuehu.entity.Hwork hwork){
        return hworkMapper.addHwork(hwork);
    }
    @Override
    //  查
    public int selectHwork(String hnum,String uid){
        return hworkMapper.selectHwork(hnum,uid);
    }
    @Override
    //  改
    public int updateHwork(com.soft.xuehu.entity.Hwork hwork){
        return hworkMapper.updateHwork(hwork);
    }


    @Override
    //根据主键查询一条问题
    public Hwork selectByHid(Integer hid){
        return hworkMapper.selectByHid(hid);
    }

    @Override
    public void update(Hwork hwork){
        hworkMapper.update(hwork);
    }

    @Override
    //查询作业份数
    public int selectNum(String courseNo,String uid){
        return hworkMapper.selectNum(courseNo,uid);
    }

    @Override
    //删除一份作业
    public void removeHwork(Integer hid){
        hworkMapper.removeHwork(hid);
    }

    @Override
    //查询所有
    public List<Hwork> select(){
        return hworkMapper.select();
    }

}
