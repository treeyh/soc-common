package com.treeyh.common.web.config;

import com.treeyh.common.constants.SocCommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author TreeYH
 * @version 1.0
 * @description 提交参数日期转换
 * @create 2019-05-20 11:54
 */
public class StringDateConverter implements Converter<String, Date> {

    private static final Logger logger = LoggerFactory.getLogger(StringDateConverter.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

    private static final String TIME_PATTERN_REGEX = "^\\d{1,13}$";

    @Override
    public Date convert(final String source) {
        if(source == null || source.trim().equals("")){
            return null;
        }
        String _src = source.trim();
        // 1,数字类型
        if(_src.matches(TIME_PATTERN_REGEX)){
            try{
                long lTime = Long.parseLong(_src);
                if(_src.length() > 10){
                    return new Date(lTime);
                }else{
                    return new Date(SocCommonConstants.MILLISECOND_UNIT * lTime);
                }
            }catch(Exception e){
                logger.error(e.getMessage(), e);
            }
        }else if(_src.length() == 19){
            // 2,长日期类型
            try {
                dateFormat.setLenient(false);
                return dateFormat.parse(source);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }else if(_src.length() == 10) {
            // 3,短日期类型
            try {
                dateFormat1.setLenient(false);
                return dateFormat1.parse(source);
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return null;
    }
}