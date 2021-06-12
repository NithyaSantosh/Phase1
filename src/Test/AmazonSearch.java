package Test;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class AmazonSearch {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String category = null;
		String searchVal = null;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        
        ChromeDriver driver = new ChromeDriver();
        
        driver.get("http://amazon.com");
        
        driver.manage().window().maximize();
        
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce","root","nithya58");
            
            Statement stmt = con.createStatement();  
            ResultSet rs = stmt.executeQuery("select * from amazon"); 
            
            while(rs.next()) {
                
                System.out.println(rs.getString(2)+"  "+rs.getString(3)); 
           //     List<WebElement> resultList = driver.findElement(By.xpath("//*[@title='Search in']"));
                
               category = rs.getString(2);
               searchVal = rs.getString(3);
            }
            WebElement searchDD = driver.findElement(By.xpath("//select[@id = 'searchDropdownBox']"));
			Select Cat = new Select(searchDD);
  
			Cat.selectByVisibleText(category);
			
			WebElement searchTB= driver.findElement(By.xpath("//*[@id='twotabsearchtextbox']"));
			searchTB.sendKeys(searchVal);
			
			WebElement searchButton=driver.findElement(By.xpath("//*[@id='nav-search-submit-button']"));
			searchButton.click();
			
			List<WebElement> resultList = driver.findElements(By.xpath("//*[@data-component-type='s-search-result']"));
	    	System.out.println("Total search count : " + resultList.size());
	    	
	    	List<WebElement> resultName = driver.findElements(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']"));
	    	
	    	for(int i=0;i <resultList.size();i++)  	
		    {
	    		String mobileName = resultName.get(i).getText();
	    		System.out.println("Mobile name : " + mobileName);
		    }
	    	
	    	TakesScreenshot TsObj = (TakesScreenshot)driver;
	    	File myFile = TsObj.getScreenshotAs(OutputType.FILE);
	    	

	    	try {	
	    		FileUtils.copyFile(myFile, new File("searchresult.png"));
	    		
	    	}
	    	catch (IOException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         }
	    	driver.close();
            
        } 
	    	catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("Class not found");
        } catch (SQLException e) {
            System.out.println("SQL Exception");

	}
	}

}
