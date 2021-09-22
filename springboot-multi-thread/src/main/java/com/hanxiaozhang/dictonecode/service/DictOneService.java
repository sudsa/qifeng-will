package com.hanxiaozhang.dictonecode.service;


import com.hanxiaozhang.dictonecode.domain.DictOneDO;
import com.hanxiaozhang.utils.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2019/12/28
 * @since 1.0.0
 */
public interface DictOneService {



    int save(DictOneDO dict);

    R importExcel(MultipartFile file);


}
