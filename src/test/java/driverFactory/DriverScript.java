package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import commonMethods.ReusableMethods;
import utilites.ExcelFileUtil;

public class DriverScript {

	public static WebDriver driver;

	String Inputpath = System.getProperty("user.dir") +"//FileInput//Controller1.xlsx";
	String Outputpath = System.getProperty("user.dir") +"//FileOutput//HybridReults.xlsx";
	String TCSheet = "MasterTestCases";
	String TCModule = "";
	ExtentReports repoters;
	ExtentTest logger;
	public void StartTest() throws Throwable
	{

		String Module_Status = " ";
		String Module_New = " ";
		//Call Excel file uitls
		ExcelFileUtil xl = new ExcelFileUtil(Inputpath);
		//iterate all rows in tc sheet
		for(int i=1;i<=xl.rowCount(TCSheet);i++)
		{
			if(xl.getCellData(TCSheet, i,2).equalsIgnoreCase("Y"))
			{
				
				// store coresponding sheet or testcase in TC Module
				TCModule = xl.getCellData(TCSheet, i,1);
				repoters = new ExtentReports("./target/ExtentReports/"+TCModule+ ReusableMethods.genratdate()+".html");
				logger = repoters.startTest(TCModule);
				logger.assignAuthor("Dasu");
				//inherited sheet values
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//Each module to call TCmodule sheet
					String Description = xl.getCellData(TCModule, j, 0); 
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Ltype = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j,3);
					String Test_Data = xl.getCellData(TCModule, j,4);
					try
					{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = ReusableMethods.startBrowser();
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							ReusableMethods.openUrl();
						}
						if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							ReusableMethods.waitForElement(Ltype, Lvalue, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							ReusableMethods.typeAction(Ltype, Lvalue, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							ReusableMethods.clickAction(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							ReusableMethods.validateTitle(Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							ReusableMethods.closeBrowser();
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							ReusableMethods.dropDownAction(Ltype, Lvalue, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("Capstock"))
						{
							ReusableMethods.Capstock(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("StockTable"))
						{
							ReusableMethods.StockTable();
						}
						if(Object_Type.equalsIgnoreCase("capsup"))
						{
							ReusableMethods.capsup(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							ReusableMethods.supplierTable();
						}
						if(Object_Type.equalsIgnoreCase("captuerCustomer"))
						{
							ReusableMethods.captuerCustomer(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							ReusableMethods.customerTable();
						}
						if(Object_Type.equalsIgnoreCase("capnum"))
						{
							ReusableMethods.capnum(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("capdate"))
						{
							ReusableMethods.capdate(Ltype, Lvalue);
						}
						if(Object_Type.equalsIgnoreCase("purchaseTable"))
						{
							ReusableMethods.purchaseTable();
						}
						//write as pass in status row
						xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
						Module_Status = "True";
					}catch(Exception e)
					{
						System.out.println("Error: " + e.getMessage());
						System.out.println("--------------------------");
						//write as fail in status row
						xl.setCellData(TCModule, j, 5, "Fail", Outputpath);

						Module_New = "False";
					}
					repoters.endTest(logger);
					repoters.flush();
					if(Module_Status.equalsIgnoreCase("True"))
					{
						xl.setCellData(TCSheet, i, 3, "Pass", Outputpath);
						
					}
					if(Module_New.equalsIgnoreCase("False"))
					{
						xl.setCellData(TCSheet, i, 3, "Fail", Outputpath);
					}
				}

			}
			else
			{
				xl.setCellData(TCSheet, i,3,"Blocked",Outputpath);
			}
		}



	}


}
