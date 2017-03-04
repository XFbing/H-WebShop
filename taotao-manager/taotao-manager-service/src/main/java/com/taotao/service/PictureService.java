package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by h on 2017-03-01.
 */
public interface PictureService {
    Map uploadPicture(MultipartFile uploadFile);
}
