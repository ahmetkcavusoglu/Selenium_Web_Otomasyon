import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class Base_Test extends Main_Driver {
    public abstract void doTest() throws InterruptedException;

    public void assertEqualsFunc(String expected, String actual){
        Assert.assertEquals(expected,actual);
    }

    public boolean SepetControl(String value) {

        WebElement webElement = driver.findElement(By.cssSelector(value));

        if (webElement.isEnabled()) { return false; }
        else { return true; }
    }
}