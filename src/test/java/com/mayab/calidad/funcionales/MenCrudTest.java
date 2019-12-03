/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mayab.calidad.funcionales;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


/**
 *
 * @author diego
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenCrudTest {
    
    public static String URL;
    public static WebDriver driver;
    public String emailAdded = this.getRandEmail();
    public Boolean yaInicie = false;
    
    public MenCrudTest() {       
        System.out.println("\n\n          Contructor del test\n\n");
    }
    
    @BeforeClass
    public static void setUpClass() {   
    }

    
    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }
    
    @Before
    public void setUp() {  
        URL = "https://mern-crud.herokuapp.com/";
        
        //travis
        System.setProperty("webdriver.chrome.driver", "/chromedriver");
        driver = new ChromeDriver(); 

        /*
        System.out.println("\n\n ------- before ------- ");
        System.out.println(" ------- "+this.yaInicie+" ------- \n\n");
        
        if(this.yaInicie == false){
            driver.get(URL);      
                
            WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/button"));             
            button.click();
            this.settingUpTestAddFail();
            this.settingUpTestEdit();
            this.settingUpTestDelete();
            this.settingUpTestDelete2(); 

            this.waitMoment();
            driver.close();
        }     
        */
    }
    
    @After
    public void tearDown() {
        driver.close();
        
    }
    
    @Test
    public void testA_addSuccesss(){
        System.out.print("\n \n\n --------      add success     ------- \n\n\n");
        driver.get(URL);      
        
        //this.cleaningTable();
        
        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/button"));             
        button.click();
        
        WebElement nameElement = driver.findElement(By.name("name"));
        nameElement.sendKeys("Diegotest");
        
        WebElement emailElement = driver.findElement(By.name("email"));
        
        emailElement.sendKeys(this.emailAdded);
        
        WebElement ageElement = driver.findElement(By.name("age"));
        ageElement.sendKeys("20");       
        
        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]"));
        genderElement.click();
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[1]"));
        genderElement.click();
        genderElement.submit();
        
        this.waitMoment();
        
        WebElement TEST = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form")); 
        String testPass = TEST.getAttribute("class");
        
        
        String expect = "ui form success";
        assertEquals(expect,testPass); 
              
        this.settingUpTestAddFail();
        this.settingUpTestEdit();
        this.settingUpTestDelete();
        this.settingUpTestDelete2();           
    }
    
    @Test
    public void testB_editSuccesss(){
        System.out.print("\n \n\n --------      edit success     ------- \n\n\n");
        driver.get(URL);
        String emailToEdit = "diegotestedit@gmail.com";
        
        WebElement table=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody"));
        List<WebElement> rows=table.findElements(By.tagName("tr"));
        
        for(int rowNum=0;rowNum<rows.size();rowNum++){
            List<WebElement> columns=rows.get(rowNum).findElements(By.tagName("td"));
            String comparison = columns.get(1).getText();
            
            if(emailToEdit.matches(comparison)){
                
                int realRowNumber = rowNum + 1;
                columns.get(4).findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody/tr["+ realRowNumber +"]/td[5]/button[1]")).click();
                
                WebElement nameElement = driver.findElement(By.name("name"));
                nameElement.clear();
                nameElement.clear();
                this.waitMoment();

                nameElement.sendKeys(this.getNewName());
                nameElement.submit();

                this.waitMoment();

                WebElement TEST = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form"));
                String testPass = TEST.getAttribute("class");                
                
                String expect = "ui form success";
                assertEquals(expect,testPass);     
                break;               
            }
        }        
    }
    
    @Test
    public void testC_deleteSuccess(){
        System.out.print("\n \n\n --------      delete success     ------- \n\n\n");
        
        driver.get(URL);
        
        String emailToEdit = "diegotestdelete@gmail.com";
        Boolean expected = true;
        Boolean confirmation = false;
        
        WebElement table=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody"));
        List<WebElement> rows=table.findElements(By.tagName("tr"));
        
        for(int rowNum=0;rowNum<rows.size();rowNum++){
            List<WebElement> columns=rows.get(rowNum).findElements(By.tagName("td"));
            String comparison = columns.get(1).getText();
            
            if(emailToEdit.matches(comparison)){
                
                int realRowNumber = rowNum + 1;
                columns.get(4).findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody/tr["+ realRowNumber +"]/td[5]/button[2]")).click();
                
                this.waitMoment();

                WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/button[1]"));
                button.click();
                confirmation = true;  
                this.waitMoment();                   
                break;               
            }
        }
        
        assertEquals(expected,confirmation); 
    }

    @Test
    public void testD_addFail(){
        System.out.print("\n \n\n --------      add fail     ------- \n\n\n");
        
        driver.get(URL);        
        //CHECAR QUE EL ELEMENTO EXISTA ANTES DE PROBAR
        WebElement button = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/button"));             
        button.click();        
        
        WebElement nameElement = driver.findElement(By.name("name"));
        nameElement.clear();
        nameElement.sendKeys("DiegoTestfail");
        
        WebElement emailElement = driver.findElement(By.name("email"));
        emailElement.clear();
        emailElement.sendKeys("DiegoTestfail@gmail.com");

        WebElement ageElement = driver.findElement(By.name("age"));
        ageElement.clear(); 
        ageElement.sendKeys("21");       

        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]"));
        genderElement.click();
 
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[1]"));
        genderElement.click();
        genderElement.submit();
        
        this.waitMoment();

        WebElement TEST = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form")); 
        String testPass = TEST.getAttribute("class");
        
        String expect = "ui form warning";
        assertEquals(expect,testPass);     
    }

    @Test
    public void testE_editFail(){
        System.out.print("\n \n\n --------      edit fail     ------- \n\n\n");
        
        driver.get(URL);
        String emailToEdit = "diegotestfail@gmail.com";
        
        WebElement table=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody"));
        List<WebElement> rows=table.findElements(By.tagName("tr"));
        
        for(int rowNum=0;rowNum<rows.size();rowNum++){
            List<WebElement> columns=rows.get(rowNum).findElements(By.tagName("td"));
            String comparison = columns.get(1).getText();
            
            
            if(emailToEdit.matches(comparison)){
                int realRowNumber = rowNum + 1;
                columns.get(4).findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody/tr["+ realRowNumber +"]/td[5]/button[1]")).click();
                
                
                WebElement emailElement = driver.findElement(By.name("email")); 

                
                emailElement.clear();
                
                this.waitMoment();
                
                emailElement.sendKeys("diegotestedit@gmail.com");
                emailElement.submit();               

                
                this.waitMoment();

                WebElement TEST = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form"));
                String testPass = TEST.getAttribute("class");                
                
                String expect = "ui form warning";
                assertEquals(expect,testPass); 
                break;               
            }
        }
    }
    
    @Test
    public void testF_deleteFail(){
        System.out.print("\n \n\n --------      delete fail     ------- \n\n\n");
        
        driver.get(URL);
        
        String emailToDelete = "diegotestdelete2@gmail.com";
        Boolean expected = true;
        Boolean confirmation = false;
        
        WebElement table=driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody"));
        List<WebElement> rows=table.findElements(By.tagName("tr"));
        
        for(int rowNum=0;rowNum<rows.size();rowNum++){
            List<WebElement> columns=rows.get(rowNum).findElements(By.tagName("td"));
            String comparison = columns.get(1).getText();                   
            
            if(emailToDelete.matches(comparison)){
                
                int realRowNumber = rowNum + 1;
                columns.get(4).findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/table/tbody/tr["+ realRowNumber +"]/td[5]/button[2]")).click();
                
                this.waitMoment();

                WebElement button = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/button[2]"));
                button.click();
                confirmation = true;  
                this.waitMoment();                   
                break;               
            }
        }
        
        assertEquals(expected,confirmation); 
    }   

    
    public String getRandEmail(){
        String mail = "@gmail.com";
        Random ran = new Random();
        int x = ran.nextInt(1000);
        String completeEmail = x + mail;
        return completeEmail;
    }

    public void waitMoment(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MenCrudTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNewName(){
        Random ran = new Random();
        int x = ran.nextInt(100);
        String regresando = x + "Diegoedittest";
        return regresando;
    }

    public void settingUpTestAddFail(){
               
        WebElement nameElement = driver.findElement(By.name("name"));   
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement ageElement = driver.findElement(By.name("age"));
        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/i"));
        
        
        //Elemento para el test addFail
        nameElement.clear();
        emailElement.clear();
        ageElement.clear(); 
        
        this.waitMoment();
        
        nameElement.clear();
        nameElement.sendKeys("Diego test addfail");
        emailElement.clear();
        emailElement.sendKeys("diegotestfail@gmail.com");
        ageElement.clear(); 
        ageElement.sendKeys("20");       
        
        genderElement.click();
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[1]"));
        genderElement.click();
        genderElement.submit();
                
        this.waitMoment();
    }
    
    public void settingUpTestEdit(){
        //Elemento para los test editSuccess y editFail
        
        WebElement nameElement = driver.findElement(By.name("name"));   
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement ageElement = driver.findElement(By.name("age"));
        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/i"));
        
        
        nameElement.clear();
        emailElement.clear();
        ageElement.clear(); 
        
        this.waitMoment();
        
        nameElement.clear();
        nameElement.sendKeys("Diego test edit");
        emailElement.clear();
        emailElement.sendKeys("diegotestedit@gmail.com");
        ageElement.clear(); 
        ageElement.sendKeys("21");       
        
        genderElement.click();
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[2]"));
        genderElement.click();
        genderElement.submit();
        
        
        this.waitMoment();
    }
    
    public void settingUpTestDelete(){       
        //Elemento para el test deleteSuccesss
        WebElement nameElement = driver.findElement(By.name("name"));   
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement ageElement = driver.findElement(By.name("age"));
        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/i"));
        
        nameElement.clear();
        emailElement.clear();
        ageElement.clear(); 
        
        this.waitMoment();
        
        nameElement.clear();
        nameElement.sendKeys("Diego test delete");
        emailElement.clear();
        emailElement.sendKeys("diegotestdelete@gmail.com");
        ageElement.clear(); 
        ageElement.sendKeys("22");       
        
        genderElement.click();
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[3]"));
        genderElement.click();
        genderElement.submit();
        
        this.waitMoment();
    }
    
    public void settingUpTestDelete2(){       
        //Elemento para el test deleteFail
        WebElement nameElement = driver.findElement(By.name("name"));   
        WebElement emailElement = driver.findElement(By.name("email"));
        WebElement ageElement = driver.findElement(By.name("age"));
        WebElement genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/i"));
        
        nameElement.clear();
        emailElement.clear();
        ageElement.clear(); 
        
        this.waitMoment();
        
        nameElement.clear();
        nameElement.sendKeys("Diego test delete 2");
        emailElement.clear();
        emailElement.sendKeys("diegotestdelete2@gmail.com");
        ageElement.clear();
        ageElement.sendKeys("23");       
        
        genderElement.click();
        genderElement = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[3]/div[2]/div/div[2]/div[3]"));
        genderElement.click();
        genderElement.submit();
        
        this.waitMoment();
    }
    
}