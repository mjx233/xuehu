package com.soft.xuehu.service.Impl;

import com.soft.xuehu.entity.Course;
import com.soft.xuehu.entity.Cusers;
import com.soft.xuehu.entity.Hwork;
import com.soft.xuehu.entity.Users;
import com.soft.xuehu.mapper.CourseMapper;
import com.soft.xuehu.service.CourseService;
import com.soft.xuehu.transcation.MyException;
import com.soft.xuehu.util.CourseQueryHelper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.OrderBy;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create By majianxin on 2020/5/7.
 */

@Service
@Transactional(rollbackFor = Exception.class)
public
class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    //新增课程记录
    @Override
    public void addCourse(Course course){
        courseMapper.addCourse(course);
    }
    //删除一条课程记录
    @Override
    public void removeCourse(String courseNo){
        courseMapper.removeCourse(courseNo);
    }

    @Override
    //删除一个章节目录
    public void removeZjml(String courseNo,Integer zj,Integer ml){
        courseMapper.removeZjml(courseNo,zj,ml);
    }

    @Override
    //更新一条课程记录
    public void updateCourse(Course course){
        courseMapper.updateCourse(course);
    }

    @Override
    //更新章节目录
    public void updateZjml(Course course){
        courseMapper.updateZjml(course);
    }

    @Override
    //获取一条课程记录
    public Course loadCourseByCourseNo(String courseNo){
        Course course=new Course();
        course=null;
        if(courseNo!=null){
            course=courseMapper.loadCourseByCourseNo(courseNo);
        }
        return course;
    }

    @Override
    //根据课程编号、章节目录获取一条课程记录
    public Course loadCourse(String courseNo,Integer zj,Integer ml){
        return courseMapper.loadCourse(courseNo,zj,ml);
    }

    @Override
    //根据课程编号、章节目录返回查询条数
    public int loadCourse1(String courseNo,Integer zj,Integer ml){
        return courseMapper.loadCourse1(courseNo,zj,ml);
    }

    @Override
    //获取一节课程的内容
    public Course selectMl(Integer cid){
        Course course=courseMapper.selectMl(cid);
        return  course;
    }


    @Override
    //顺序排列章节目录
    public List<Course> loadSortCourses(String courseNo){
        List<Course> list=courseMapper.loadSortCourses(courseNo);
        return list;

    }

    @Override
    //分页查询全部课程
    public List<Course> loadAll(CourseQueryHelper helper){
        List<Course> list=courseMapper.loadAll(helper);
        return list;
    }
    @Override
    //查询用户所学课程
    public List<Course> loadStudy(List<Cusers> cusersList){
        if(cusersList.size()==0){
            List<Course> courseList = new ArrayList<>();
            return  courseList;
        }
        List<Course> list=courseMapper.loadStudy(cusersList);
        return list;
    }

    @Override
    //查询教师所教课程
    public List<Course> loadTeach(List<Cusers> cusersList){
        if(cusersList.size()==0){
            List<Course> courseList = new ArrayList<>();
            return  courseList;
        }
        List<Course> list=courseMapper.loadTeach(cusersList);
        return list;
    }

    @Override
    public byte[] getPic(String courseNo) {

        byte[] pic = null;

        Course course = courseMapper.loadCourseByCourseNo(courseNo);

        pic = course.getPic();

        return pic;
    }


        @Override
        //excel上传
        public int addZjml(MultipartFile file) throws Exception{
            int result = 0;
        //存放excel表中所有章节目录
        List<Course> courseList = new ArrayList<>();
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
                    Course course = new Course();
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
                    String cname = row.getCell(0).getStringCellValue();

                    //这条语句可以直接转换单元格的格式为String
                    /*row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);*/
                    /**
                     * 获取第二个单元格的内容
                     */
                    String teacher = row.getCell(1).getStringCellValue();

                    /**
                     * 获取第三个单元格的内容
                     */
                    //因为单元格类型为double，所以要先用getNumericCellValue()方法获取后再转换为字符串
                    String courseNo = row.getCell(2).getStringCellValue();


                    /**
                     * 获取第四个单元格的内容
                     */
                    //因为单元格类型为double，所以要先用getNumericCellValue()方法获取后再转换为字符串
                    Integer zj;
                    if(1 == row.getCell(3).getCellType() ) {
                        String c = row.getCell(3).getStringCellValue();
                        zj=Integer.parseInt(c);
                    }
                    else{
                        zj  = (int)row.getCell(3).getNumericCellValue();
                    }

                    //这条语句可以直接转换单元格的格式为String
                    /*row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);*/
                    /**
                     * 获取第五个单元格的内容
                     */
                    Integer ml;
                    if(1 == row.getCell(4).getCellType() ) {
                        String c = row.getCell(4).getStringCellValue();
                        ml=Integer.parseInt(c);
                    }
                    else{
                        ml  = (int)row.getCell(4).getNumericCellValue();
                    }

                    /**
                     * 获取第六个单元格的内容
                     */
                    String zjMessage = new String();
                    if(row.getCell(5)==null) {

                        zjMessage = "";
                    }
                    else
                        zjMessage = row.getCell(5).getStringCellValue();

                    /**
                     * 获取第七个单元格的内容
                     */
                    String mlMessage = new String();
                    if(row.getCell(6)==null) {

                        mlMessage = "";
                    }
                    else
                        mlMessage = row.getCell(6).getStringCellValue();

                    /**
                     * 获取第八个单元格的内容
                     */
                    String uid;
                    if(1 == row.getCell(7).getCellType() ) {
                        uid = row.getCell(7).getStringCellValue();
                    }
                    else{
                        Integer c  = (int)row.getCell(7).getNumericCellValue();
                        uid = c.toString();
                    }
                    /**
                     * 设置course的字段
                     */
                    course.setCname(cname);
                    course.setZj(zj);
                    course.setMl(ml);
                    course.setcMessage(null);
                    course.setVideo(null);
                    course.setZjMessage(zjMessage);
                    course.setMlMessage(mlMessage);
                    course.setTeacher(teacher);
                    course.setCourseNo(courseNo);
                    course.setPic(null);
                    course.setUid(uid);
                    courseList.add(course);
                }

                for(Course courseInfo:courseList){
                    /**
                     * 判断数据库表中是否存在作业记录，若存在，则更新，不存在，则保存记录
                     */
                    Integer zj = courseInfo.getZj();
                    Integer ml = courseInfo.getMl();
                    String courseNo = courseInfo.getCourseNo();

                    int count = courseMapper.loadCourse1(courseNo,zj,ml);

                    if(count==0){
                        courseMapper.addCourse(courseInfo);
                        result=1;
                    }else{
                        courseMapper.updateZjml(courseInfo);
                        result=1;
                    }
                }
            }

            return result;
        }

    @Override
    public XSSFWorkbook show(List<Course> courseList) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("ZJML");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        titleRow.createCell(0).setCellValue("课程名");//第一列
        titleRow.createCell(1).setCellValue("教师");
        titleRow.createCell(2).setCellValue("课程编号");
        titleRow.createCell(3).setCellValue("章节序号");
        titleRow.createCell(4).setCellValue("目录序号");
        titleRow.createCell(5).setCellValue("章节内容");
        titleRow.createCell(6).setCellValue("目录内容");
        titleRow.createCell(7).setCellValue("教师id");
        int cell = 1;
        for (Course course : courseList) {
            Row row = sheet.createRow(cell);//从第二行开始保存数据
            row.createCell(0).setCellValue(course.getCname());//将数据库的数据遍历出来
            row.createCell(1).setCellValue(course.getTeacher());
            row.createCell(2).setCellValue(course.getCourseNo());
            row.createCell(3).setCellValue(course.getZj());//将数据库的数据遍历出来
            row.createCell(4).setCellValue(course.getMl());
            row.createCell(5).setCellValue(course.getZjMessage());
            row.createCell(6).setCellValue(course.getMlMessage());
            row.createCell(7).setCellValue(course.getUid());
            cell++;
        }
        return wb;
    }

    @Override
    public List<Course> loadByUid(String uid){
        return courseMapper.loadByUid(uid);
    }

    @Override
    //删除用户所有课程
    public void remove(String uid){
        courseMapper.remove(uid);
    }

}
