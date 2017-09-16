package com.amazon.web.dao;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author RenXintao
 * @Date 12/25/16
 */
@Repository("loginDao")
public interface ILoginDao {
    List<Map<String, Object>> getUser();
}
