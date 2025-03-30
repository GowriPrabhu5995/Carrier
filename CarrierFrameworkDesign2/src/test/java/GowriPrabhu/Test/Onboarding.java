package GowriPrabhu.Test;

import org.testng.annotations.Test;

import GowriPrabhu.TestComponents.BaseTest;
import GowriPrabhu.TestComponents.DataProviders;

public class Onboarding extends BaseTest {

	@Test(dataProvider = "getDataFromExcel", dataProviderClass = DataProviders.class)
	public void testCaseData(String GSTNUM, String BroadClassification, String ClassificationType,
			String Firm_Registration_Num, String Company_Registration_num, String Firm_Type, String Div_10,
			String Div_20, String Contact_Type, String Contact_Person_name, String Mobile_Number, String Email) {

		System.out.println(GSTNUM + " | " + BroadClassification + " | " + ClassificationType + " | "
				+ Firm_Registration_Num + " | " + Company_Registration_num + " | " + Firm_Type + " | " + Div_10 + " | "
				+ Div_20 + " | " + Contact_Type + " | " + Contact_Person_name + " | " + Mobile_Number + " | " + Email);
	}
	
	
	
	
	
}


