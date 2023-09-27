package Tests;

import Objects.Company;
import Utility.CSVUtility;
import Utility.GitHubUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class GitHubTest {
    GitHubUtility gitHubUtility = new GitHubUtility();
    CSVUtility csvUtility = new CSVUtility();

    @Test
    public void testCSVUpdateAndMerge() {
        // Initialize a list of companies
        List<Company> companies = Arrays.asList(
                new Company("Company1", 100, 1000, "USA"),
                new Company("Company2", 200, 2000, "Canada"),
                new Company("Company3", 300, 3000, "UK")
        );

        // Fetch the CSV content from the main branch.
        String mainBranchContent = gitHubUtility.getCSVContent();

        // Append new companies to the existing CSV content.
        String updatedMainBranchContent = csvUtility.appendToCSVContent(mainBranchContent, companies);

        // Update the CSV content in the main branch.
        gitHubUtility.updateCSVContent(updatedMainBranchContent);

        // Merge the main branch into the second branch.
        gitHubUtility.mergeBranches();

        // Fetch the CSV content from the second branch.
        String secondBranchContent = gitHubUtility.getCSVContent(); // Consider modifying GitHubUtility to fetch content from a specific branch.

        // Assertions to verify if the CSV content in both branches is the same.
        Assertions.assertTrue(csvUtility.compareCSVContent(updatedMainBranchContent, secondBranchContent),
                "The CSV content in both branches should be identical after the merge");
    }
}
