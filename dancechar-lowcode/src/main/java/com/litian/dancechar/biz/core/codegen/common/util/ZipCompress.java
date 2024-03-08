package com.litian.dancechar.biz.core.codegen.common.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.*;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipCompress {

    public static void zip(ZipOutputStream zos, String sourceFileName, VelocityContext velocityContext)
    {
        try {
            URL resource = ZipCompress.class.getClassLoader().getResource(sourceFileName);
            File sourceFile = new File(resource.getFile());
            //创建zip输出流
            //调用函数
            compress(velocityContext, zos, sourceFile, sourceFile.getName());
        } catch (Exception e) {
        }
    }

    public static void compress(VelocityContext velocityContext, ZipOutputStream zos, File sourceFile, String fileName) throws Exception
    {

        for(Object keyObj : velocityContext.getKeys()){
            String key = String.valueOf(keyObj);
            String p = "${"+key+"}";
            if(fileName.indexOf(p) >= 0){
                fileName = fileName.replace(p, String.valueOf(velocityContext.get(key)));
                if (fileName.endsWith(".vm")){
                    // .vm都为文件模板
                    fileName = fileName.replace(".vm", "");
                }else {
                    // 多级目录处理
                    fileName = fileName.replace(".", "/");
                }

            }
        }
        if((sourceFile.isDirectory()) && "${childBusName}".equals(sourceFile.getName()) && (false)) {
            return;
        }
        //如果路径为目录（文件夹）
        if(sourceFile.isDirectory())
        {
            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();
            if (flist.length == 0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
            } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < flist.length; i++) {
                    compress(velocityContext, zos, flist[i], fileName + "/" + flist[i].getName());
                }
            }

        }
        else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            StringWriter sw = new StringWriter();
            Reader reader = new InputStreamReader(new FileInputStream(sourceFile));
            Velocity.evaluate(velocityContext, sw, "logTag", reader);
            zos.putNextEntry(new ZipEntry(StringUtils.removeEnd(fileName, ".vm")));
            IOUtils.write(sw.toString(), zos, "UTF-8");
            IOUtils.closeQuietly(sw);
            zos.flush();
            zos.closeEntry();
        }
    }
}
