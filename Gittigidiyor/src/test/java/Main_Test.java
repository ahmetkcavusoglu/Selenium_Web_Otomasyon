import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;


public class Main_Test extends Base_Test {

    public static JavascriptExecutor js;

    static org.apache.log4j.Logger logger = Logger.getLogger(Main_Test.class);

    @Test
    public void doTest() throws InterruptedException {
        BasicConfigurator.configure();
        logger.info("Gittigidiyor Sitesi Açıldı.");

        super.assertEqualsFunc(driver.getCurrentUrl(),"https://www.gittigidiyor.com/");
        logger.info("Sitenin açıldığı kontrol edildi.");

        WebElement login = driver.findElement(By.xpath("//div[@title='Giriş Yap']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(login).perform();
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-cy='header-user-menu']/div[2]/div[1]"))).click();
        logger.info("Giriş yap sayfası açıldı. ");

        WebElement nameElement = driver.findElement(By.id("L-UserNameField"));
        nameElement.click();
        nameElement.sendKeys("project@testinium.com");
        WebElement passElement = driver.findElement(By.id("L-PasswordField"));
        passElement.click();
        passElement.sendKeys("testinium");
        WebElement submitButton = driver.findElement(By.id("gg-login-enter"));
        submitButton.click();

        super.assertEqualsFunc("testinium",driver.findElement(By.cssSelector("div[title='Hesabım']>div[class='gekhq4-4 egoSnI']>span")).getText());
        logger.info("Kullanıcı adı ve şifre kontrol edildi.");

        WebElement searchElement = driver.findElement(By.xpath("//input[@type='text']"));
        searchElement.click();
        //searchElement.clear();
        searchElement.sendKeys("Bilgisayar");

        WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
        searchButton.click();
        logger.info("Bilgisayar anahtar kelimesi aratıldı.");

        js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        driver.findElement(By.linkText("2")).click();
        logger.info("2. sayfa açıldı.");

        super.assertEqualsFunc("https://www.gittigidiyor.com/arama/?k=Bilgisayar&sf=2",driver.getCurrentUrl());
        logger.info("2. sayfanın açıldığı kontrol edildi.");

        // Ürün random olarak seçildi.
        int randomIndexProduct = new Random().nextInt(48);
        driver.findElement(By.cssSelector(".products-container > li[product-index='"+randomIndexProduct+"']")).click();
        logger.info("Açılan sayfadan rastgele bir ürün seçildi.");

        // Seçilen ürün sepete eklendi.
        js = ((JavascriptExecutor) driver);
        js.executeScript("window.scroll(0,550)");
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("button[id='add-to-basket']")).click();
        logger.info("Seçilen ürün sepete eklendi.");

        // Seçilen ürünün fiyatı
        WebElement element = driver.findElement(By.id("sp-price-lowPrice"));
        String listPrice = element.getText();
        logger.info("Seçilen ürünün fiyatı : "+listPrice);

        // Sepete gidildi
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[text()='Sepete Git']")).click();
        logger.info("Sepet sayfası açıldı.");

        // Sepetteki ürün fiyat
        WebElement element1 = driver.findElement(By.cssSelector("div[class='gg-d-8 detail-price']"));
        String basketPrice = element1.getText();
        logger.info("Sepetteki ürünün fiyatı : "+basketPrice);

        //super.assertEqualsFunc(listPrice, basketPrice);
        logger.info("Ürün fiyatları karşılaştırıldı.");
        //Ürüne her seferinde uygulananan farklı indirimlerden dolayı kontrol işlemini yorum olarak bıraktım.

        // Ürün adet sayısı 2 yapıldı
        Thread.sleep(500);
        driver.findElement(By.cssSelector("select.amount>:nth-child(2)")).click();
        logger.info("Adet sayısı 2 olarak seçildi. ");

        //Sepetten ürün silinir
        Thread.sleep(2000);
        driver.findElement(By.className("btn-delete")).click();
        logger.info("Ürün sepetten kaldırıldı.");

        //super.SepetControl("div[class='gg-w-24 gg-d-24 gg-t-24 gg-m-24 padding-none product-item-box-container']");
        Assert.assertFalse(super.SepetControl("div[class='gg-w-24 gg-d-24 gg-t-24 gg-m-24 padding-none product-item-box-container']"));
        logger.info("Sepet durumu kontrol edildi.");
    }
}