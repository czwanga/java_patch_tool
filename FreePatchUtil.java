package com.thinkgem.jeesite.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.thinkgem.jeesite.common.utils.FileUtils;
  
public class FreePatchUtil {  
  
    public static String patchFile="E:/patch0721.txt";//补丁文件,由eclipse svn plugin生成  
      
    public static String projectPath="D:/mypractises/workspace/mockWorkflow/";//项目文件夹路径  
      
    public static String webContent="src/main/webapp/WEB-INF";//web应用文件夹名  
      
    public static String classPath="D:/mypractises/workspace/mockWorkflow/";//class存放路径  
      
    public static String desPath="C:/Users/niuhongwei/Desktop/update_pkg/";//补丁文件包存放路径  
      
    public static String version="20170722";//补丁版本  
    
    public static String projectName = "mockWorkflow";//项目名

    public static SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception { 
        copyFiles(getPatchFileList());  
        FileUtils.zipFiles(desPath+version, "", desPath+projectName+sdFormat.format(new Date())+".zip");
    }  
      
    public static List<String> getPatchFileList() throws Exception{  
        List<String> fileList=new ArrayList<String>();  
        FileInputStream f = new FileInputStream(patchFile);   
        BufferedReader dr=new BufferedReader(new InputStreamReader(f,"utf-8"));  
        String line;  
        while((line=dr.readLine())!=null){   
            if(line.indexOf("Index:")!=-1){  
                line=line.replaceAll(" ","");  
                line=line.substring(line.indexOf(":")+1,line.length());  
                fileList.add(line);  
            }  
        }   
        return fileList;  
    }  
      
    public static void copyFiles(List<String> list){  
          
        for(String fullFileName:list){  
            if(fullFileName.indexOf("/java")!=-1){//对java源文件目录下的文件处理  
                String fileName=fullFileName;  
                fullFileName=classPath+fileName;  
                if(fileName.endsWith(".java")){  
                    fileName=fileName.replace(".java",".class").replace("src/main/java/","/classes/");  
                    fullFileName=fullFileName.replace(".java",".class").replace("/java/","/webapp/WEB-INF/classes/");  
                }  
                String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));  
                String desFilePathStr=desPath+"/"+version+"/WEB-INF"+tempDesPath;  
                String desFileNameStr=desPath+"/"+version+"/WEB-INF"+fileName;  
                File desFilePath=new File(desFilePathStr);  
                if(!desFilePath.exists()){  
                    desFilePath.mkdirs();  
                }  
                copyFile(fullFileName, desFileNameStr);  
                System.out.println(fullFileName+"复制完成");  
            }else if(fullFileName.indexOf("/resource")!=-1){//对resource文件的处理
            	String fileName=fullFileName;  
                fullFileName=classPath+fileName;  
                  
                String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));  
                String desFilePathStr=desPath+"/"+version+"/WEB-INF"+tempDesPath.replace("src/main/resources", "/classes/");  
                String desFileNameStr=desPath+"/"+version+"/WEB-INF"+fileName.replace("src/main/resources", "/classes/");  
                File desFilePath=new File(desFilePathStr);  
                if(!desFilePath.exists()){  
                    desFilePath.mkdirs();  
                }  
                copyFile(fullFileName, desFileNameStr);  
                System.out.println(fullFileName+"复制完成");
            }else if(fullFileName.indexOf("/webapp/WEB-INF/views")!=-1){//对web应用文件的处理
            	String fileName=fullFileName;  
                fullFileName=classPath+fileName;  
                  
                String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));  
                String desFilePathStr=desPath+"/"+version+"/WEB-INF/"+tempDesPath.replace("src/main/webapp/WEB-INF/", "");  
                String desFileNameStr=desPath+"/"+version+"/WEB-INF/"+fileName.replace("src/main/webapp/WEB-INF/", "");  
                File desFilePath=new File(desFilePathStr);  
                if(!desFilePath.exists()){  
                    desFilePath.mkdirs();  
                }  
                copyFile(fullFileName, desFileNameStr);  
                System.out.println(fullFileName+"复制完成");            	
            }else{//对静态资源文件的处理
            	String fileName=fullFileName;  
                fullFileName=classPath+fileName;  
                  
                String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));  
                String desFilePathStr=desPath+"/"+version+tempDesPath.replace("src/main/webapp", "");  
                String desFileNameStr=desPath+"/"+version+fileName.replace("src/main/webapp", "");  
                File desFilePath=new File(desFilePathStr);  
                if(!desFilePath.exists()){  
                    desFilePath.mkdirs();  
                }  
                copyFile(fullFileName, desFileNameStr);  
                System.out.println(fullFileName+"复制完成");
            }  
              
        }  
          
    }  
  
    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {  
        File srcFile=new File(sourceFileNameStr);  
        File desFile=new File(desFileNameStr);  
        try {  
            copyFile(srcFile, desFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
  
      
      
    public static void copyFile(File sourceFile, File targetFile) throws IOException {  
        BufferedInputStream inBuff = null;  
        BufferedOutputStream outBuff = null;  
        try {  
            // 新建文件输入流并对它进行缓冲  
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));  
  
            // 新建文件输出流并对它进行缓冲  
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));  
  
            // 缓冲数组  
            byte[] b = new byte[1024 * 5];  
            int len;  
            while ((len = inBuff.read(b)) != -1) {  
                outBuff.write(b, 0, len);  
            }  
            // 刷新此缓冲的输出流  
            outBuff.flush();  
        } finally {  
            // 关闭流  
            if (inBuff != null)  
                inBuff.close();  
            if (outBuff != null)  
                outBuff.close();  
        }  
    }  
      
}  

