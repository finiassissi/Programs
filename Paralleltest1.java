/* Write a code to launch two broswers parallely
 *  with multithreading concept in java, 
 * search Selenium in google and assert count of links.*/


package Browser1;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class Paralleltest1 extends Thread {

	WebDriver driver;
	String browsertype;
	String Url = "https://www.google.com";

	public Paralleltest1(String browsertype) {
		super();
		this.browsertype = browsertype;
	}

	public void setup(String browsertype) {
		if (browsertype == "chrome") {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\anton\\eclipseWS\\sample\\chromedriver.exe");
			driver = new ChromeDriver();

		}
		if (browsertype == "edge") {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\anton\\eclipseWS\\sample\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		driver.get(Url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public void googlesearch() throws Exception {

		driver.findElement(By.xpath("//*[@id=\"tsf\"]/div[2]/div[1]/div[1]/div/div[2]/input")).sendKeys("Selenium" + Keys.ENTER);

		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println(links.size());

		System.out.println(driver.getTitle());

		List<WebElement> link = driver.findElements(By.tagName("a"));
		System.out.println(link.size());
		Assert.assertEquals(links.size(), 161);

	}

	public void tearDown() {
		driver.close();
		driver.quit();
	}

	public void run() {
		System.out.println("Thread- Started" + Thread.currentThread().getName());
		String threadname = Thread.currentThread().getName();
		System.out.println(threadname);

		try {
			setup(this.browsertype);
			googlesearch();
			tearDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Thread t1 = new Paralleltest1("chrome");
		Thread t2 = new Paralleltest1("edge");
		t1.start();
		t2.start();
	}
}
