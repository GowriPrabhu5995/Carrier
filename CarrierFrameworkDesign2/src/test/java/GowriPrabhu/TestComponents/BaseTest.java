package GowriPrabhu.TestComponents;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import GowriPrabhu.pageobjects.Landingpage;

public class BaseTest {
	public WebDriver driver;
	public Landingpage lp;
	public String baseUrl;

	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir")+"\\src\\main\\java\\GowriPrabhu\\resorces\\GlobalData.properties");
		prop.load(fis);
		String browserName =System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");

		// prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();

		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			// firefox
		}

		else if (browserName.equalsIgnoreCase("Edge")) {
			driver = new EdgeDriver();
		}

            // ✅ Set Base URL based on Environment
            String environment = System.getProperty("environment", prop.getProperty("environment", "NetCoreTest")).trim().toUpperCase();

            switch (environment) {
                case "NETCORETEST":
                    baseUrl = prop.getProperty("NetCoreTest_URL");
                    break;
                case "CLIENTSERVER":
                    baseUrl = prop.getProperty("ClientServer_URL");
                    break;
                case "LIVE":
                    baseUrl = prop.getProperty("LIVE_URL");
                    break;
                default:
                    throw new RuntimeException("Invalid environment specified: " + environment);
            }

            System.out.println("✅ Browser Initialized: " + browserName);
            System.out.println("✅ Base URL: " + baseUrl);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
            driver.get(baseUrl);
       
        return driver;
	 }

    
public List<HashMap<String, Object>> getJsonDataToMap(String FilePath) throws IOException {
		
		//Read Json to String 
		//String jsonContent = Files.readString(Path.of(System.getProperty("user.dir") + "\\src\\test\\java\\GowriPrabhu\\data\\PurchaseOrder.json"));
		 String jsonContent = Files.readString(
	                Path.of(FilePath), 
	                StandardCharsets.UTF_8);
	             
// String to HashMap ----> Jackson Databind
		
		 ObjectMapper objectMapper = new ObjectMapper();
		    return objectMapper.readValue(jsonContent, new TypeReference<>() {});
	}






public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
	TakesScreenshot TS = (TakesScreenshot)driver;
	 File Source = TS.getScreenshotAs(OutputType.FILE);
	 File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
	 FileUtils.copyFile(Source, file);
	 return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
}
    @BeforeClass(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver(); // ✅ Ensures only one driver instance
        lp = new Landingpage(driver);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit(); // ✅ Closes all browser windows and terminates WebDriver session
            driver = null; // ✅ Reset driver to avoid stale sessions
            System.out.println("✅ WebDriver Closed Successfully");
        }
    }
}
