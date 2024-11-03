package com.shxy.sudatqd.dbService.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.models.dbBean.*;
import com.shxy.sudatqd.dbService.*;
import com.shxy.sudatqd.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.List;

@Component
public class pictureServiceImpl extends ServiceImpl<pictureMapper, picture> implements pictureService {
    @Autowired
    private pictureMapper pictureMapper;
    @Override
    public void addDefaultPicture(int type, int objectId,int oneId) {
        String foldName = "";
        if(type==0){
            foldName = "archivePics";
        } else if(type==1){
            foldName = "paperPics";
        } else if(type==2){
            foldName = "institutionPics";
        } else if(type==3){
            foldName = "fiberPics";
        } else if(type==4){
            foldName = "componentPics";
        }

        String SOURCE_FILE = "src/main/resources/pictures/default.gif";
        String DESTINATION_DIR = "/Users/kwai/Desktop/picture/"+foldName+"/";

        Path sourcePath = Paths.get(SOURCE_FILE);
        Path oneFile = Paths.get(DESTINATION_DIR +objectId+"-"+generateRandomFileName() + sourcePath.getFileName());
        try {
            Files.createDirectories(oneFile.getParent());
            Files.copy(sourcePath, oneFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileName = oneFile.getFileName().toString();
        picture map = new picture();
        map.setObjectId(objectId);
        map.setObjectType(type);
        map.setPictureName(fileName);
        map.setPictureLocation( "/Users/kwai/Desktop/picture/"+foldName+"/" + fileName);
        map.setState(0);
        map.setCreatorId(oneId);
        map.setCreationTime(DateTime.now());
        pictureMapper.insert(map);
    }

    @Override
    public void addPicture(List<MultipartFile> pictureData, int type,int objectId,int oneId) {
        String foldName = "";
        if(type==0){
            foldName = "archivePics";
        } else if(type==1){
            foldName = "paperPics";
        } else if(type==2){
            foldName = "institutionPics";
        } else if(type==3){
            foldName = "fiberPics";
        } else if(type==4){
            foldName = "componentPics";
        }
//        for (MultipartFile oneFile : pictureData) {
//            String filePath = System.getProperty("user.dir")+"/src/main/resources/picutres/"+foldName+"/" + oneFile.getOriginalFilename();
//            File destinationFile = new File(filePath);
//            try {
//                oneFile.transferTo(destinationFile);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            picture map = new picture();
//            map.setObjectId(objectId);
//            map.setObjectType(type);
//            map.setPictureName(oneFile.getOriginalFilename());
//            map.setPictureLocation( "src/main/resources/picutres/"+foldName+"/" + oneFile.getOriginalFilename());
//            map.setState(0);
//            map.setCreatorId(oneId);
//            map.setCreationTime(DateTime.now());
//            pictureMapper.insert(map);
//        }
    }

    @Override
    public void addTestPicture(List<String> pictureData, int type,int objectId,int oneId) {
        String foldName = "";
        if(type==0){
            foldName = "archivePics";
        } else if(type==1){
            foldName = "paperPics";
        } else if(type==2){
            foldName = "institutionPics";
        } else if(type==3){
            foldName = "fiberPics";
        } else if(type==4){
            foldName = "componentPics";
        }
        for (String oneFile : pictureData) {
            try {
                String[] parts = oneFile.split(":");
                String decodedFileName = parts[0];
                byte[] bytes = Base64Utils.decodeFromString(parts[1]);
                //获取Base64，并解码成字节
                String filePath = System.getProperty("user.dir")+"/src/main/resources/pictures/"+foldName+"/" + decodedFileName;
                File file = new File(filePath);
                //判断文件所在目录是否存在，如果不存在，则就创建目录
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                //创建新的图片文件
                file.createNewFile();
                //创建文件输出流
                FileOutputStream fos = new FileOutputStream(file);
                //将字节写入到图片文件中
                fos.write(bytes);
                fos.close();
                System.out.println(true);

                picture map = new picture();
                map.setObjectId(objectId);
                map.setObjectType(type);
                map.setPictureName(decodedFileName);
                map.setPictureLocation( "src/main/resources/picutres/"+foldName+"/" + decodedFileName);
                map.setState(0);
                map.setHomePage(1);
                map.setCreatorId(oneId);
                map.setCreationTime(DateTime.now());
                pictureMapper.insert(map);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(false);
            }
        }

    }
    public void addTestPicture1(String pictureData, int type, int objectId, int oneId) {
        String foldName = "";
        if(type==0){
            foldName = "archivePics";
        } else if(type==1){
            foldName = "paperPics";
        } else if(type==2){
            foldName = "institutionPics";
        } else if(type==3){
            foldName = "fiberPics";
        } else if(type==4){
            foldName = "componentPics";
        }


            try {
                String[] parts = pictureData.split(":");
                String decodedFileName = parts[0];
                byte[] bytes = Base64Utils.decodeFromString(parts[1]);
                //获取Base64，并解码成字节
                String filePath = System.getProperty("user.dir")+"/src/main/resources/pictures/"+foldName+"/" + decodedFileName;
                File file = new File(filePath);
                //判断文件所在目录是否存在，如果不存在，则就创建目录
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                //创建新的图片文件
                file.createNewFile();
                //创建文件输出流
                FileOutputStream fos = new FileOutputStream(file);
                //将字节写入到图片文件中
                fos.write(bytes);
                fos.close();
                System.out.println(true);

                picture map = new picture();
                map.setObjectId(objectId);
                map.setObjectType(type);
                map.setPictureName(decodedFileName);
                map.setPictureLocation( "src/main/resources/picutres/"+foldName+"/" + decodedFileName);
                map.setState(0);
                map.setHomePage(0);
                map.setCreatorId(oneId);
                map.setCreationTime(DateTime.now());
                pictureMapper.insert(map);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(false);
            }


    }

    @Override
    public void delePicture(int type, int id){
        // 0为档案类型，1为纸张，2为机构，3为纤维，4为成分
        UpdateWrapper<picture> wrapper = new UpdateWrapper<>();
        wrapper.eq("objectType", type).eq("objectId",id);
        pictureMapper.delete(wrapper);
    }





    private String generateRandomFileName() {
        String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
        }
        return sb.toString();
    }
}
