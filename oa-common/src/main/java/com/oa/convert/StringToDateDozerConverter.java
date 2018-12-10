package com.oa.convert;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dozer.DozerConverter;

public class StringToDateDozerConverter extends DozerConverter<String, Date> {

    public StringToDateDozerConverter() {
        super(String.class, Date.class);
    }

    @Override
    public String convertFrom(Date source, String destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        destination = formatter.format(source);
        return destination;
    }

    @Override
    public Date convertTo(String source, Date destination) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        ParsePosition pos = new ParsePosition(0);
        destination = formatter.parse(source, pos);
        return destination;
    }

}