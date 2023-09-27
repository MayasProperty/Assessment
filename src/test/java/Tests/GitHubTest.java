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
                new Company("CompanyAlpha", 120, 1100, "USA"),
                new Company("CompanyBeta", 220, 2100, "Canada"),
                new Company("CompanyGamma", 320, 3100, "UK")
        );

        // Fetch the CSV content from the main branch.
        String mainBranchContent = gitHubUtility.getCSVContent("master");

        // Append new companies to the existing CSV content.
        String updatedMainBranchContent = csvUtility.appendToCSVContent(mainBranchContent, companies);

        // Update the CSV content in the main branch.
        gitHubUtility.updateCSVContent(updatedMainBranchContent);

        // Merge the main branch into the second branch "branch_2".
        gitHubUtility.mergeBranches("branch_2", "master");

        // Fetch the CSV content from the "branch_2".
        String secondBranchContent = gitHubUtility.getCSVContentFromBranch("branch_2");

        // Assertions to verify if the CSV content in both branches is the same.
        Assertions.assertTrue(csvUtility.compareCSVContent(updatedMainBranchContent, secondBranchContent),
                "The CSV content in both branches should be identical after the merge");
    }
}
