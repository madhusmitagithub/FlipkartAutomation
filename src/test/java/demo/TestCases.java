package demo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
import org.testng.annotations.*;
import java.util.Comparator;

//import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    
    @BeforeSuite
    public void browserSetUp(){
        System.out.println("Constructor: TestCases");
        //WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        
    }
    
    @AfterSuite
    public void endTest()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void navigateTo(){
        driver.get("https://www.flipkart.com/");
    }

    
    public void search(WebDriver driver, String text) throws InterruptedException{
        try{
            //Locate the searchbox and search for the text
            WebElement searchBox = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
            searchBox.clear();
            searchBox.sendKeys(text);
            Thread.sleep(2000);
            searchBox.submit();
            Thread.sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void rating(WebDriver driver) throws InterruptedException{
        try{
            //Locate the popularity tab and click on it
            WebElement popularity = driver.findElement(By.xpath("//div[@class='zg-M3Z' and text()='Popularity']"));
            popularity.click();
            Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("KzDlHZ")));

            //Store all product ratings in a list and and print the count of items with rating less than or equal to 4 stars
            List<WebElement> allProductRatings = driver.findElements(By.className("Y1HWO0"));
            int count = 0;
            
            for(WebElement rating: allProductRatings){
                //Thread.sleep(4000);
                String productRating = rating.getText();
                
                int ratingValue = Integer.parseInt(productRating.replaceAll("[^0-9]", ""));
                if(ratingValue <= 4){
                    count++;
                }
            }
            System.out.println("Count of items with rating less than or equal to 4 stars is >>>>>>> " + count);
            System.out.println();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Search for “iPhone” and print the Titles and discount % of items with more than 17% discount
    public void discount(WebDriver driver) throws InterruptedException{
        try{
            List<WebElement> allProducts = driver.findElements(By.className("yKfJKb"));
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("nt6sNV")));

                       
            // Initialize a HashMap to store titles and discounts
            Map<String, Integer> productDiscounts = new HashMap<>();
            
            for(WebElement product: allProducts){
                try{
                    WebElement title = product.findElement(By.className("KzDlHZ"));
                    Thread.sleep(2000);
                    String productTitle = title.getText();
                    System.out.println("Product title is: "+productTitle);
                    //WebElement discount = product.findElement(By.xpath("//div[@class='UkUFwK']/span"));UkUFwK
                    WebElement discount = product.findElement(By.className("UkUFwK"));
                    //WebElement discount = product.findElement(By.xpath("//span[contains(text(),'off')]"));
                    String discountText = discount.getText();
                    System.out.println("discountText" +discountText);
                        if(discountText.contains("%")){
                            int productDiscount = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));
                            System.out.println("Discount>>>> "+productDiscount);

                            // Add the product to HashMap if discount > 17%
                            if(productDiscount > 17){
                                productDiscounts.put(productTitle, productDiscount);
                            }
                        }
                }catch(Exception e){
                    e.printStackTrace();
                }    
            }
            //print the Titles and discount % of items from HashMap
            for (Map.Entry<String, Integer> entry : productDiscounts.entrySet()) {
                System.out.println("Title is : " + entry.getKey() + " and Discount is : " + entry.getValue() + "%");
            }
            System.out.println();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Search “Coffee Mug”. Print the Title and image URL of the 5 items with highest number of reviews
    public void productReview() throws InterruptedException{
        try{
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("nt6sNV")));

                //JavascriptExecutor js = (JavascriptExecutor) driver;
                // WebElement customerRating = driver.findElement(By.xpath("//div[contains(text(),'Customer Ratings')]"));
                // js.executeScript("arguments[0].scrollIntoView(true);", customerRating);

                //select 4 stars and above
                WebElement reviewChkBox = driver.findElement(By.xpath("(//div[@class='XqNaEv'])[1]"));
                //Thread.sleep(2000);
                reviewChkBox.click();
                Thread.sleep(3000);

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='DOjaWF gdgoEp']")));

                //Get all product elements
                List<WebElement> allProducts = driver.findElements(By.className("slAVV4"));
                
                // Initialize a list to store top 5 product details
                List<Map<String, String>> topProducts = new ArrayList<>();

                for(WebElement product : allProducts){
                    try{
                        // Fetch title of all the product
                        WebElement title = product.findElement(By.className("wjcEIp"));
                        String productTitle = title.getText();
                        // System.out.println("productTitle >>>>>>>>>>>>"+productTitle);
                        Thread.sleep(3000);

                        // Fetch image URL of all the product
                        WebElement imageElement = product.findElement(By.className("DByuf4"));
                        String imageUrl = imageElement.getAttribute("src");
                        // System.out.println("imageurl>>>>"+imageUrl);

                        WebElement review = product.findElement(By.className("Wphh3N"));
                        String reviewText = review.getText();
                        //System.out.println("review text : "+reviewText);
                        String review1 = reviewText.replaceAll("[^0-9]", "");
                        int productReview = Integer.parseInt(review1.trim());
                        // System.out.println("product review = " +productReview);

                        // Initialize a HashMap to store product titles and their image URLs
                        Map<String, String> productDetailsMap = new HashMap<>();
                        
                        productDetailsMap.put("productTitle", productTitle);
                        productDetailsMap.put("imageUrl", imageUrl);
                        productDetailsMap.put("productReview", String.valueOf(productReview));

                        // System.out.println("finished the List Preparation");
                        topProducts.add(productDetailsMap);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("Before the sort.............");
                topProducts.sort((p1, p2) -> Integer.compare(Integer.parseInt(p2.get("productReview")), Integer.parseInt(p1.get("productReview"))));
                System.out.println(":::: Now printing the top five product details based on the review::::");    
                for (int i = 0; i < Math.min(5, topProducts.size()); i++) {
                    Map<String, String> product = topProducts.get(i);
                    System.out.println("Title: " + product.get("productTitle") + ", Image URL: " + product.get("imageUrl") + "productReview :"+product.get("productReview"));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }

    @Test(description="Flipkart Search Functionality Automation")
    public void testCase01() throws InterruptedException{
        try{

            System.out.println("Start Test case: testCase01");
            navigateTo();
            Thread.sleep(5000);
            search(driver, "Washing Machine");
            rating(driver);

            navigateTo();
            Thread.sleep(2000);
            search(driver, "iPhone");
            discount(driver);

            navigateTo();
            Thread.sleep(2000);
            search(driver, "Coffee Mug");
            productReview();

            System.out.println("end Test case: testCase01");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
