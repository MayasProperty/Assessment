package Tests;

import Objects.Company;
import Utility.CSVUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CSVTest {
    CSVUtility csvUtility = new CSVUtility();

    @Test
    void appendToCSVContentTest() {
        String existingContent = "Name,Number of Employees,Number of Customers,Country\n";
        existingContent += "ABC Corp,100,200,USA\n";

        List<Company> companies = Arrays.asList(
                new Company("XYZ Ltd", 150, 300, "UK"),
                new Company("PQR Inc", 200, 400, "Canada")
        );

        String expectedContent = "Name,Number of Employees,Number of Customers,Country\n";
        expectedContent += "ABC Corp,100,200,USA\n";
        expectedContent += "XYZ Ltd,150,300,UK\n";
        expectedContent += "PQR Inc,200,400,Canada\n";

        String actualContent = csvUtility.appendToCSVContent(existingContent, companies);

        Assertions.assertNotEquals(expectedContent, actualContent);
    }

    @Test
    void compareCSVContentEqualTest() {
        String content1 = "Name,Number of Employees,Number of Customers,Country\n";
        content1 += "ABC Corp,100,200,USA\n";

        String content2 = "Name,Number of Employees,Number of Customers,Country\n";
        content2 += "ABC Corp,100,200,USA\n";

        Assertions.assertTrue(csvUtility.compareCSVContent(content1, content2));
    }

    @Test
    void compareCSVContentNotEqualTest() {
        String content1 = "Name,Number of Employees,Number of Customers,Country\n";
        content1 += "ABC Corp,100,200,USA\n";

        String content2 = "Name,Number of Employees,Number of Customers,Country\n";
        content2 += "XYZ Ltd,150,300,UK\n";

        Assertions.assertFalse(csvUtility.compareCSVContent(content1, content2));
    }
}
