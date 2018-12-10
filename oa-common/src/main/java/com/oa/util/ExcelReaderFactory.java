package com.oa.util;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
 
import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.util.IOUtils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
 
public class ExcelReaderFactory {
   public static ExcelReader getExcelReader(InputStream in, Object customContent,
         AnalysisEventListener<?> eventListener) throws EmptyFileException, IOException, InvalidFormatException {
      // 如果输入流不支持mark/reset，需要对其进行包裹
      if (!in.markSupported()) {
         in = new PushbackInputStream(in, 8);
      }
 
      // 确保至少有一些数据
      byte[] header8 = IOUtils.peekFirst8Bytes(in);
      ExcelTypeEnum excelTypeEnum = null;
      if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
         excelTypeEnum = ExcelTypeEnum.XLS;
      }
      if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
         excelTypeEnum = ExcelTypeEnum.XLSX;
      }
      if (excelTypeEnum != null) {
         return new ExcelReader(in, excelTypeEnum, customContent, eventListener);
      }
      throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
 
   }
 
   /**
    * @param in
    *           文件输入流
    * @param customContent
    *           自定义模型可以在
    *           {@link AnalysisEventListener#invoke(Object, AnalysisContext) }
    *           AnalysisContext中获取用于监听者回调使用
    * @param eventListener
    *           用户监听
    * @param trim
    *           是否对解析的String做trim()默认true,用于防止 excel中空格引起的装换报错。
    * @throws IOException
    * @throws EmptyFileException
    * @throws InvalidFormatException
    */
   public static ExcelReader getExcelReader(InputStream in, Object customContent,
         AnalysisEventListener<?> eventListener, boolean trim)
         throws EmptyFileException, IOException, InvalidFormatException {
      // 如果输入流不支持mark/reset，需要对其进行包裹
      if (!in.markSupported()) {
         in = new PushbackInputStream(in, 8);
      }
 
      // 确保至少有一些数据
      byte[] header8 = IOUtils.peekFirst8Bytes(in);
      ExcelTypeEnum excelTypeEnum = null;
      if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
         excelTypeEnum = ExcelTypeEnum.XLS;
      }
      if (DocumentFactoryHelper.hasOOXMLHeader(in)) {
         excelTypeEnum = ExcelTypeEnum.XLSX;
      }
      if (excelTypeEnum != null) {
         return new ExcelReader(in, excelTypeEnum, customContent, eventListener, trim);
      }
      throw new InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
   }
}
