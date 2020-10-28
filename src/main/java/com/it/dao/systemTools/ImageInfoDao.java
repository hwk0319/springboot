package com.it.dao.systemTools;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.it.po.ImageInfo;

@Repository(value="imageInfoDao")
public interface ImageInfoDao {
    int deleteById(@Param("id") int id);

    int insert(@Param("po") ImageInfo po);

    ImageInfo selectById(@Param("id") int id);
    
    List<ImageInfo> selectList(@Param("po") ImageInfo po);

    int update(@Param("po") ImageInfo po);

	int bacthDelete(@Param("array") String[] array);
}