import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/*
 * 
 */
public class webScrapper {

    WebDriver driver = new ChromeDriver();
 
    

    public void setProperty() {
        File file = new File("C:\\chromedriver.exe");
        System.setProperty("webdriver.chrome.dri`ver", file.getAbsolutePath());

    }

    public int getPage() {
        int resultSize;
        String innerText = driver.findElement(By.className("dvt_stockcount_placeholder")).getAttribute("innerHTML");
        resultSize = Integer.parseInt(innerText);
        
        return resultSize;
    }

    /**
     * Open the test website.
     */
    public void openTestSite() {
        driver.get("https://www.tradecarview.com/");

    }

    /**
     * 
     * @param username
     * @param Password
     * 
     *            Logins into the website, by entering provided username and
     *            password
     */
    public void searchSkyline() {
        //
        Select make = new Select(driver.findElement(By.id("used-list-search-make")));
        make.selectByValue("2");
        //
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //
        Select model = new Select(driver.findElement(By.id("used-list-search-model")));
        model.selectByValue("7442");
        //
        //
        WebElement submit_button = driver.findElement(By.xpath("//*[@id='QuickSearch1_SearchForm2']/ul/li[9]/button"));
        submit_button.click();
    }

    /**
     * Scrapes year of vehicle from tradecarview.com
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public JSONArray scrapeYear() {
        JSONArray year = new JSONArray();
        List<WebElement> allElement = driver
                .findElements(By.cssSelector(".vehicle-item-main-info .main-info-box:first-child .main-info-body"));

        Iterator<WebElement> itr = allElement.iterator();

        while (itr.hasNext()) {
            String carYear = itr.next().getText();
            if (carYear.equals(null)) {
                break;
            } else {
                System.out.println(carYear);
                year.add(carYear);
            }
        }
        return year;
    }

    /**
     * Scrapes year of vehicle from tradecarview.com
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public JSONArray scrapeDisplacement() {
        JSONArray engine = new JSONArray();
        List<WebElement> allElement = driver
                .findElements(By.cssSelector(".vehicle-item-main-info .main-info-box:nth-child(2) .main-info-body"));

        Iterator<WebElement> itr = allElement.iterator();

        while (itr.hasNext()) {
            String engineSize = itr.next().getText();
            if (engineSize.equals(null)) {
                break;
            } else {
                System.out.println(engineSize);
                engine.add(engineSize);
            }
        }
        return engine;
    }

    /**
     * Scrapes mileage of vehicle from tradecarview.com
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public JSONArray scrapeMileage() {
        JSONArray miles = new JSONArray();
        List<WebElement> allElement = driver
                .findElements(By.cssSelector(".vehicle-item-main-info .main-info-box:nth-child(3) .main-info-body"));

        Iterator<WebElement> itr = allElement.iterator();

        while (itr.hasNext()) {
            String carMiles = itr.next().getText();
            if (carMiles.equals(null)) {
                break;
            } else {
                System.out.println(carMiles);
                miles.add(carMiles);
            }
        }
        return miles;
    }

    /**
     * Scrapes price of vehicle from tradecarview.com
     * 
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public JSONArray scrapePrice() {
        JSONArray price = new JSONArray();
        List<WebElement> allElement = driver
                .findElements(By.cssSelector(".vehicle-item-main-info .main-info-box.fob-price .main-info-body"));

        Iterator<WebElement> itr = allElement.iterator();

        while (itr.hasNext()) {
            String carPrice = itr.next().getText();
            if (carPrice.equals(null)) {
                break;
            } else {
                System.out.println(carPrice);
                price.add(carPrice);
            }
        }
        return price;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        webScrapper web = new webScrapper();
        
        
        web.setProperty();
        web.openTestSite();
        web.searchSkyline();
        

   
        JSONArray year = web.scrapeYear(); // Gets Year of vehicle
        JSONArray engine = web.scrapeDisplacement(); // Gets engine size of vehicle
        JSONArray miles = web.scrapeMileage(); // Gets miles of vehicle
        JSONArray price = web.scrapePrice(); // Gets miles of vehicle }
        
            
        
        
      

        
        /* Writing each JSON Array to JSON Object */
        JSONObject JSONobj = new JSONObject();
        JSONobj.put("Year", year);
        JSONobj.put("Engine", engine);
        JSONobj.put("Miles", miles);
        JSONobj.put("Price", price);

        
        /* FILE OUTPUT CODE */
        Writer writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("C:\\Users\\Caleb\\Desktop\\status.txt"), "utf-8"));
        writer.write(JSONobj.toJSONString());
        writer.close();

    }

}




