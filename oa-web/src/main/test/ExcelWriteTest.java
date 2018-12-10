
 
import java.io.FileInputStream;
import java.io.InputStream;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.oa.entity.dto.MonthRecordExcelModel;
import com.oa.util.ExcelReaderFactory;

public class ExcelWriteTest {

	public static void read() throws Exception {
		//text.xlsx
		try (InputStream in = new FileInputStream("F:\\Users\\zw\\Desktop\\templet.xlsx");) {
			AnalysisEventListener<MonthRecordExcelModel> listener = new AnalysisEventListener<MonthRecordExcelModel>() {
 
				@Override
				public void invoke(MonthRecordExcelModel object, AnalysisContext context) {
					System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object.toString());
				}
 
				@Override
				public void doAfterAllAnalysed(AnalysisContext context) {
					System.err.println("doAfterAllAnalysed...");
				}
			};
			ExcelReader excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
			// 第二个参数为表头行数，按照实际设置
			excelReader.read(new Sheet(1, 1, MonthRecordExcelModel.class));
		}
	}

	public static void main(String[] args){
		try {
			read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}