package com.zrb.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * Created by zrb on 2017/5/20.
 */
@Service
@Mapper
public interface CourseMapper {


    public void Insert(@Param("id") Integer id, @Param("name") String name);

    @Insert("INSERT INTO course(id, name) VALUES(#{id},#{name})")
    public void Inset(@Param("id") Integer id, @Param("name") String name);
}
