package Utility;
import Objects.Company;
import org.apache.commons.csv.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

public class CSVUtility {
    public String appendToCSVContent(String existingContent, List<Company> companies) {
        StringWriter sw = new StringWriter();
        try {
            CSVPrinter csvPrinter;
            if (existingContent.isEmpty()) {
                csvPrinter = new CSVPrinter(sw, CSVFormat.DEFAULT.withHeader("Name", "Number of Employees", "Number of Customers", "Country"));
            } else {
                csvPrinter = new CSVPrinter(sw, CSVFormat.DEFAULT);
            }

            if (!existingContent.isEmpty()) {
                csvPrinter.println();
            }
            for (Company company : companies) {
                csvPrinter.printRecord(company.getName(), company.getNumberOfEmployees(), company.getNumberOfCustomers(), company.getCountry());
            }
            csvPrinter.flush();
            return existingContent + sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error appending to CSV content", e);
        }
    }

    public boolean compareCSVContent(String content1, String content2) {
        try {
            CSVParser parser1 = CSVParser.parse(content1, CSVFormat.DEFAULT.withHeader());
            CSVParser parser2 = CSVParser.parse(content2, CSVFormat.DEFAULT.withHeader());

            List<CSVRecord> records1 = parser1.getRecords();
            List<CSVRecord> records2 = parser2.getRecords();

            if (records1.size() != records2.size()) {
                return false;
            }

            for (int i = 0; i < records1.size(); i++) {
                CSVRecord record1 = records1.get(i);
                CSVRecord record2 = records2.get(i);

                if (!record1.toMap().equals(record2.toMap())) {
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error comparing CSV content", e);
        }
    }
}
