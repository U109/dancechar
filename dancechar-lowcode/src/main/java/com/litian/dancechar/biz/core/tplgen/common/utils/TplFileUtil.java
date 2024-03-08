package com.litian.dancechar.biz.core.tplgen.common.utils;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.shaded.com.google.common.collect.FluentIterable;
import org.apache.curator.shaded.com.google.common.io.ByteSink;
import org.apache.curator.shaded.com.google.common.io.Files;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.annotation.Nullable;
import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TplFileUtil {

    public static void renderTpl(Map<String, File> tplPathFileMap, VelocityContext velocityContext)throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        for(Map.Entry<String, File> entry : tplPathFileMap.entrySet()){
            String filePath = entry.getKey();
            File file = entry.getValue();
            StringWriter sw = new StringWriter();
            Reader reader = new InputStreamReader(new FileInputStream(file));
            Velocity.evaluate(velocityContext, sw, "logTag", reader);
            // 转化为真实路径
            // String realPath =
            zos.putNextEntry(new ZipEntry(StringUtils.removeEnd(filePath, ".vm")));
            IOUtils.write(sw.toString(), zos, "UTF-8");
            IOUtils.closeQuietly(sw);
            zos.flush();
            zos.closeEntry();
        }
        IOUtils.closeQuietly(zos);
        byte[] bytes = outputStream.toByteArray();
        ByteSink byteSink = Files.asByteSink(new File("C://myDemo"));
        byteSink.write(bytes);
    }

    /**
     * 筛选功能目录模板单独处理
     */
    public static void functionDirHandle(){

    }



    public static void main(String[] args) throws Exception{
        URL resource = TplFileUtil.class.getClassLoader().getResource("templates/new");
        File sourceFile = new File(resource.getFile());
        String rootPath = sourceFile.getAbsolutePath();
        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(sourceFile).filter(new Predicate<File>() {
            @Override
            public boolean apply(@Nullable File input) {
                return input.isFile();  //只要文件
            }
        });
        Map<String, File> tplPathFileMap = Maps.newConcurrentMap();
        for (File file : files) {
            String filePath = file.getAbsolutePath().substring(rootPath.length());
            // 相对路径 渲染替换路径及文件流处理
            tplPathFileMap.put(filePath, file);
        }
        renderTpl(tplPathFileMap, new VelocityContext());
    }
}
