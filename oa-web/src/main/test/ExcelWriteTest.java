
 
import java.io.FileInputStream;
import java.io.InputStream;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.oa.entity.dto.MonthRecordExcelModel;
import com.oa.util.ExcelReaderFactory;
import org.junit.Test;

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

	@Test
	public void db_decrypt_test() throws Exception {
		String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJU0DT8SEPIDEfb4t2nvD++bp1k0nZgS2Zcks6oj34mS74JTiF2ItWg3rWq5ZKzxFM2VGLO03RSCxYYaa6Oe4UECAwEAAQ==";
		String password = "hOIRk6/PUQFIKhqqSkCfPv4Ppm0hWEfOTB5DGaAwM01P0HTMP02fDCHUo6rR26ejkSCiIEonMYGHTqHixfO88w==";
		System.out.println(ConfigTools.decrypt(publicKey, password));
	}

	public static void main(String[] args){
		try {
			read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}