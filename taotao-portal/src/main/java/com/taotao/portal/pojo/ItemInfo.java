package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by h on 2017-03-07.
 */
public class ItemInfo extends TbItem {
    public String[] getImages(){
        String image = getImage();
        if(getImage() != null){
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}
