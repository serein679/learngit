package com.shxy.sudatqd.dbService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.models.dbBean.picture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface pictureService extends IService<picture> {
    void addDefaultPicture(int type, int objectId,int oneId);
    void addPicture(List<MultipartFile> pictureData, int type, int objectId,int oneId);
    void addTestPicture(List<String> pictureData, int type, int objectId,int oneId);
    void addTestPicture1(String pictureData, int type, int objectId, int oneId);
    void delePicture(int type, int id);
}
