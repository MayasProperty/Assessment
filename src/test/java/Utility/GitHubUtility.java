package Utility;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;

import java.util.HashMap;
import java.util.Map;

public class GitHubUtility {
    private static final String TOKEN = "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";
    private static final String REPO_OWNER = "MayasProperty";
    private static final String REPO_NAME = "Assessment";
    private static final String FILE_PATH = "src/test/resources/company.csv";

    public String getCSVContent() {
        String url = String.format("https://api.github.com/repos/%s/%s/contents/%s", REPO_OWNER, REPO_NAME, FILE_PATH);

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "token " + TOKEN)
                .header("Accept", "application/vnd.github.v3+json");

        Response response = request.get(url);

        if (response.getStatusCode() == 200) {
            String encodedContent = response.jsonPath().getString("content");
            byte[] decodedContent = Base64.decodeBase64(encodedContent);
            return new String(decodedContent);
        } else {
            throw new RuntimeException("Failed to fetch CSV content. HTTP error code: " + response.getStatusCode());
        }
    }

    private String getShaOfCurrentCommit() {
        String url = String.format("https://api.github.com/repos/%s/%s/contents/%s", REPO_OWNER, REPO_NAME, FILE_PATH);

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "token " + TOKEN)
                .header("Accept", "application/vnd.github.v3+json");

        Response response = request.get(url);

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getString("sha");
        } else {
            throw new RuntimeException("Failed to get SHA of current commit. HTTP error code: " + response.getStatusCode());
        }
    }

    public void updateCSVContent(String updatedContent) {
        String url = String.format("https://api.github.com/repos/%s/%s/contents/%s", REPO_OWNER, REPO_NAME, FILE_PATH);
        String sha = getShaOfCurrentCommit();

        String encodedContent = Base64.encodeBase64String(updatedContent.getBytes());

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "token " + TOKEN)
                .header("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("message", "Update CSV content");
        requestBody.put("content", encodedContent);
        requestBody.put("sha", sha);

        Response response = request.body(requestBody).put(url);

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to update CSV content. HTTP error code: " + response.getStatusCode());
        }
    }

    public void mergeBranches() {
        String url = String.format("https://api.github.com/repos/%s/%s/merges", REPO_OWNER, REPO_NAME);

        RequestSpecification request = RestAssured.given()
                .header("Authorization", "token " + TOKEN)
                .header("Content-Type", "application/json");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("base", "SECOND_BRANCH_NAME");
        requestBody.put("head", "main");
        requestBody.put("commit_message", "Merging main into second branch");

        Response response = request.body(requestBody).post(url);

        if (response.getStatusCode() != 201) {
            throw new RuntimeException("Failed to merge branches. HTTP error code: " + response.getStatusCode());
        }
    }
}

