package test.Resources.Generic.TrafficJam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class FrameworkLibrary extends GlobalVariables 
{
	
}
	class GlobalVariables extends Zipper
	{
		public static String RunManagerName = "RunManager.xlsx";
		public static String MainMethodName  = "testdefinition";
		public static String testdatapath = "TestData\\TestData.xlsx";
		public static String TestDataSheetName = "TestData";
		public static String CurrentTestCase = "";
		public static String ScreenshotsFolderPath = "";
		public static String ResultsFolderPath;
		public static String CurrentResultsFolderPath;
		public static String ResultFilePath;
		public static String CurrentDirectory;
		public static String TestDataPath;
		
		public static WebDriver driver;
		public static String ClassPath;
		public static int StartIteration;
		public static int EndIteration;
		public static int CurrentIteration;
		public static String dateName = null;
	}
	 class Zipper extends DriverFacade {

		public void zip(String folderToZip,String zipName) throws Exception {
			Zipper zf = new Zipper();
			zf.zipFolder(Paths.get(folderToZip), Paths.get(zipName));
		}

		private void zipFolder(final Path folderToZip, Path zipName) throws IOException{
			final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName.toFile()));
			Files.walkFileTree(folderToZip, new SimpleFileVisitor<Path>() {
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			zos.putNextEntry(new ZipEntry(folderToZip.relativize(file).toString()));
			Files.copy(file, zos);
			zos.closeEntry();
			return FileVisitResult.CONTINUE;
				}
			});
			zos.close();
		}
	}
	 class DriverFacade {

			public final static Logger log = Logger.getLogger(DriverFacade.class);
			ExtentHtmlReporter htmlReporter;
			public static ExtentTest logger;
			protected final static String Log4Path = "./target/TextLogs/";
			protected final String ScreenshotPath = "./target/Screenshot/";
			protected final String screenShotsZip = "./target/Screenshot.zip";
			public static String dateName = null;
			protected static WebDriver driver = null;
			protected static ThreadLocal<WebDriver> drivers = new ThreadLocal<WebDriver>();
			protected static List<String> items = new ArrayList<String>();
			Zipper zipper = new Zipper();
			Path path;
			public static String getProps1(String name) {
				try {
					Properties prop = new Properties();
					InputStream in = new FileInputStream("./src/test/resources/ExternalData/Configuration.properties");
					prop.load(in);
					return prop.getProperty(name).trim();
				} catch (Exception e) {
					log.info(e);
					throw new NullPointerException("No such property in APIs.properties");

				}
			}

			protected static WebDriver getDriver() {
				return drivers.get();
			}

			protected static void setDriver(WebDriver driver) {
				drivers.set(driver);
			}

			public void configureLogger() {
				String tenant = null ; //TC_Entities.hostName;
				Logger rootLogger = Logger.getRootLogger();
				rootLogger.setLevel(Level.INFO);
				PatternLayout layout = new PatternLayout("[%p] %m%n");
				rootLogger.addAppender(new ConsoleAppender(layout));
				String path = Log4Path + tenant + "-Log.txt";
				try {
					RollingFileAppender fileAppender = new RollingFileAppender(layout, path);
					fileAppender.setAppend(false);
					rootLogger.addAppender(fileAppender);
				} catch (Exception e) {
					log.info(e);
				}
			}

			public void writeReport(String message) {
				try {
					String tenant = null ; //TC_Entities.hostName;
					FileWriter fw = new FileWriter(Log4Path + tenant + ".txt", true);
					fw.write(message + "\n");
					fw.close();
				} catch (Exception e) {
					log.info(e);
				}
			}

			public void writeReport1(String message) {
				try {
					String tenant = null ; //TC_Entities.hostName;
					FileWriter fw = new FileWriter(Log4Path + tenant + "1.txt", true);
					fw.write(message + "\n");
					fw.close();
				} catch (Exception e) {
					log.info(e);
				}
			}

			public void logs(String messages) {
				log.info(messages);
				writeReport(messages);
				writeReport1(messages);
			}

			public void getScreenshot(String screenshotName, String hostName) {
				try {
					String normal = getProps1("normalScreenShot");
					String fullPage = getProps1("fullPageScreenShot");
					String timeStamp = getCurrentDateWithTimeStamp();
					Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
					Date date = calendar.getTime();
					String day = date.toString();
					if (day.contains("Thu")) {
						if (fullPage.equalsIgnoreCase("yes")) {
							new File(ScreenshotPath + hostName).mkdir();
							path = Paths.get(
									ScreenshotPath + hostName + "/" + hostName + "/" + screenshotName + timeStamp + ".png");
							Files.createDirectories(path);
							Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
									.takeScreenshot(driver);
							String path1 = path.toString();
							ImageIO.write(screenshot.getImage(), "PNG", new File(path1));
							JavascriptExecutor js = (JavascriptExecutor) driver;
							js.executeScript("scroll(0,-1000)");
						} else if (normal.equalsIgnoreCase("yes")) {
							new File(ScreenshotPath + hostName).mkdir();
							path = Paths.get(
									ScreenshotPath + hostName + "/" + hostName + "/" + screenshotName + timeStamp + ".png");
							File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
							String path1 = path.toString();
							FileUtils.copyFile(scrFile, new File(path1));
						}
					}
				} catch (IOException e) {
					log.info(e);
				}
			}

			public void GetJSErrosLog(String page) {
				LogEntries jserrors = driver.manage().logs().get(LogType.BROWSER);
				for (LogEntry error : jserrors) {
					if (error.toString().contains("[WARNING]")) {
						writeReport1("<p style='color:#f4a641;'> <b style='color:#9de835;'>$$ " + page + driver.getCurrentUrl()
								+ "  </b>" + error + "</p>");
					} else if (error.toString().contains("[SEVERE]")) {
						writeReport1("<p style='color:#f44941;'> <b style='color:#9de835;'>$$ " + page + driver.getCurrentUrl()
								+ "  </b>" + error + "</p>");
					}
					if (!error.getMessage().contains("You are currently using minified code outside of NODE_ENV")) {

						if (!error.getMessage().contains("ag-grid: It looks like your paging datasource is of the old type")) {
							if (!error.getMessage().contains(
									"Deprecation warning: value provided is not in a recognized RFC2822 or ISO forma")) {
								if (!error.getMessage().contains("Each child in an array or iterator should have a unique")) {
									if (!error.getMessage()
											.contains("ag-grid: From ag-grid 1.9.0, now the getRows takes one parameter")) {
										if (error.toString().contains("[WARNING]")) {
											writeReport("<p style='color:#f4a641;'> <b style='color:#9de835;'>$$ " + page
													+ driver.getCurrentUrl() + "  </b>" + error + "</p>");
											log.info("<p style='color:#f4a641;'> <b style='color:#9de835;'>$$ " + page
													+ driver.getCurrentUrl() + "  </b>" + error + "</p>");
										} else if (error.toString().contains("[SEVERE]")) {
											writeReport("<p style='color:#f44941;'> <b style='color:#9de835;'>$$ " + page
													+ driver.getCurrentUrl() + "  </b>" + error + "</p>");
											log.info("<p style='color:#f44941;'> <b style='color:#9de835;'>$$ " + page
													+ driver.getCurrentUrl() + "  </b>" + error + "</p>");
										}
									}
								}
							}
						}
					}
				}
			}

			@BeforeMethod(alwaysRun = true)
			@Parameters(value = { "browser", "exec" })
			protected void createBrowserService(String value, String executionFlag) {
				try {
					String osName = System.getProperty("os.name").toLowerCase();
					DesiredCapabilities caps = null;
					if (value.equals("chrome")) {
						if (osName.contains("windows")) {
							System.setProperty("webdriver.chrome.driver",
									"./src/test/resources/BrowserDrivers/chromedriver.exe");
						} else {
							System.setProperty("webdriver.chrome.driver", "./src/test/resources/BrowserDrivers/chromedriver");
						}
						if (executionFlag.equals("local")) {
							driver = new ChromeDriver();
							DriverFacade.setDriver(driver);
						} else {
							caps = DesiredCapabilities.chrome();
							caps.setBrowserName("chrome");
							caps.setVersion("45.0");
							caps.setJavascriptEnabled(true);
							caps.setCapability("os.version", Platform.WINDOWS);
							new RemoteWebDriver(new URL(getProps1("HubURL")), caps);
							DriverFacade.setDriver(driver);
						}

					} else if (value.equals("firefox")) {
						if (executionFlag.equals("local")) {
							System.setProperty("webdriver.gecko.driver", "./src/test/resources/BrowserDrivers/geckodriver.exe");
							driver = new FirefoxDriver();
							DriverFacade.setDriver(driver);
						} else {
							caps = DesiredCapabilities.firefox();
							caps.setBrowserName("firefox");
							caps.setVersion("41.0");
							caps.setJavascriptEnabled(true);
							caps.setCapability("os.version", Platform.WINDOWS);
							new RemoteWebDriver(new URL(getProps1("HubURL")), caps);
							DriverFacade.setDriver(driver);
						}
					} else if (value.equals("ie")) {
						System.setProperty("webdriver.ie.driver",
								System.getProperty("user.dir") + "./src/test/resources/BrowserDrivers/IEDriverServer.exe");
						if (executionFlag.equals("local")) {
							driver = new InternetExplorerDriver();
							DriverFacade.setDriver(driver);
						} else {
							caps = DesiredCapabilities.internetExplorer();
							caps.setBrowserName("internet explorer");
							caps.setVersion("11");
							caps.setJavascriptEnabled(true);
							caps.setCapability("os.version", Platform.WINDOWS);
							new RemoteWebDriver(new URL(getProps1("HubURL")), caps);
							DriverFacade.setDriver(driver);
						}
					} else {
						Assert.assertTrue(false, "Invalid browser");
					}
					maximizeBrowser();
				} catch (Exception e) {
					log.info(e);
				}
			}

			public Map<String, String> getProperties(String Prop_FilePath) {
				Map<String, String> map = new HashMap<String, String>();
				FileInputStream fis;
				try {
					fis = new FileInputStream(Prop_FilePath);
					ResourceBundle resources = new PropertyResourceBundle(fis);
					Enumeration<String> keys = resources.getKeys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();
						map.put(key, resources.getString(key));
					}
				} catch (Exception e) {
					log.info(e);
				}
				return map;
			}

			public static String getCurrentDateWithTimeStamp() {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
				String Current = format1.format(cal.getTime());
				return Current;
			}
			protected void maximizeBrowser() {
				DriverFacade.getDriver().manage().window().maximize();
			}

			protected void navigateBack() {
				DriverFacade.getDriver().navigate().back();
			}

			protected String currentUrl() {
				String url = driver.getCurrentUrl();
				return url;
			}
			protected void setImplicitWait() {
				long timeInSec = Long.parseLong(getProps1("ImplicitWaitDuration"));
				DriverFacade.getDriver().manage().timeouts().implicitlyWait(timeInSec, TimeUnit.SECONDS);
			}

			protected static String getProps(String name, String hostName) {
				try {
					Properties prop = new Properties();
					InputStream in = new FileInputStream(
							new File("./src/test/resources/ExternalData/Configuration.properties"));
					prop.load(in);
					String value = prop.getProperty(name).trim();
					value = value.replace("#{HostName}", hostName);
					return value;
				} catch (Exception e) {
					log.info(e);
				}
				return null;
			}

			
			

			public static String getProps2(String name) {
				try {
					Properties prop = new Properties();
					InputStream in = new FileInputStream("./src/test/resources/ExternalData/createUpdate.properties");
					prop.load(in);
					return prop.getProperty(name).trim();
				} catch (Exception e) {
					log.info(e);
					throw new NullPointerException("No such property in APIs.properties");

				}
			}

			public static String getAPI(String name) {
				try {
					Properties prop = new Properties();
					InputStream in = new FileInputStream("./src/test/resources/ExternalData/loginAuth.properties");
					prop.load(in);
					return prop.getProperty(name).trim();
				} catch (Exception e) {
					log.info(e);
					throw new NullPointerException("No such property in loginAuth.properties");
				}
			}

			public static String getGridNames(String name) {
				try {
					Properties prop = new Properties();
					InputStream in = new FileInputStream("./src/test/resources/ExternalData/grids.properties");
					prop.load(in);
					return prop.getProperty(name).trim();
				} catch (Exception e) {
					log.info(e);
					throw new NullPointerException("No such property in grids.properties");
				}
			}

			public static String getDataFromExcel(String name, String grid, String hostName) throws Exception {
				FileInputStream fs = null;
				if (hostName.contains("lme")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/lme.xlsx");
				} else if (hostName.contains("nex")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/nex.xlsx");
				} else if (hostName.contains("nasdaq")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/nasdaq.xlsx");
				} else if (hostName.contains("icap")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/icap.xlsx");
				} else if (hostName.contains("cme")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/cme.xlsx");
				}else if (hostName.contains("dtcc")) {
					fs = new FileInputStream("./src/test/resources/ExternalData/dtcc.xlsx");
				}
				Workbook wb = new XSSFWorkbook(fs);
				Sheet sh = wb.getSheet(grid);
				int totalNoOfRows = sh.getPhysicalNumberOfRows();
				String value = null;
				DataFormatter df = new DataFormatter();
				try {
					for (int row = 0; row < totalNoOfRows; row++) {
						if (sh.getRow(row).getCell(0) != null) {
							if (sh.getRow(row).getCell(0).getStringCellValue().trim().equalsIgnoreCase(name.trim())) {
								value = df.formatCellValue(sh.getRow(row).getCell(1));
								break;
							}
						}
					}
				} catch (Exception e) {
					log.info(e);
				} finally {
					wb.close();
				}
				return value;
			}

			public int generateRandomNumber() throws Exception {
				return new Random().nextInt(100000);
			}

			public void htmlReport1(String source, String target) {
				FileReader fr;
				try {
					fr = new FileReader(source);
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(fr);
					FileWriter fw = new FileWriter(target);
					BufferedWriter bw = new BufferedWriter(fw);
					String line = null;
					while ((line = br.readLine()) != null) {
						bw.write(line);
					}
					bw.close();
				} catch (Exception e) {
					log.info(e);
				}
			}

			public void htmlReport(String hostName) {
				htmlReport1(Log4Path + hostName + ".txt", Log4Path + hostName + ".html");
				htmlReport1(Log4Path + hostName + "1.txt", Log4Path + hostName + "1.html");
			}
			public int number_Elements(String xpath){
				int size=  driver.findElements(By.xpath(xpath)).size();
				
				return size;
				
			}

	 }
