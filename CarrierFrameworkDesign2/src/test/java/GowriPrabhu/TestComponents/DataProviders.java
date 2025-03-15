package GowriPrabhu.TestComponents;

import org.testng.annotations.DataProvider;

public class DataProviders  extends BaseTest{

	
	@DataProvider
	public Object[][] getData() {

//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("ASMLogin_ID", "Ine00913");
//		map.put("Password", "password");
//		map.put("ChannelPartner_Code", "834911");
//		map.put("ShipToAddressCode", "1967391");
//		String[] items = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
//        String[] items = { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" };
//      map.put("ProductsToBuy", "items"); // Keeping array format
//      map.put("quantity", "2");
//      map.put("BranchManager_ID", "Ine00688");
//      map.put("EditedQty", "5");

		return new Object[][] { { "Ine00913", "password", "834911", "1967391",
				new String[] { "224050092290@CMI", "224050091400@CMI", "224050091490@CMI", "224050089000@CMI" }, "2",
				"Ine00688", "5" } };
	}
}
