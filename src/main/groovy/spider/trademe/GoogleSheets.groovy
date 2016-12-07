package spider.trademe

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange

/**
 * Created by shenk on 12/5/2016.
 */
class GoogleSheets {

    static String spreadsheetId = "1Lwj-egwrG-5sn2daxGz_GfgODaZXJj6R7p-gmFzi4Zw"

    /** Application name. */
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart"

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance()

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart")

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS)

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR)
        } catch (Throwable t) {
            t.printStackTrace()
            System.exit(1)
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
     static Credential authorize() throws IOException {
        File initialFile = new File("C:/Users/shenk/.credentials/sheets.googleapis.com-java-quickstart/client_secret.json")
        InputStream targetStream = new FileInputStream(initialFile)
        // Load client secrets.
        InputStream is = GoogleSheets.class.getResourceAsStream("/client_secret.json")

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(targetStream))

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build()
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver()).authorize("user")
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath())
        return credential
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    static Sheets getSheetsService() throws IOException {
        Credential credential = authorize()
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build()
    }

    static void uploadToGoogleSheet(List<Object> rowData) {

        Sheets service = getSheetsService()

        String range = "Class Data!A2"

        (0..rowData.size()-1).each { index ->
            if (rowData.get(index) == null) {
                rowData.set(index, '')
            }
        }

        ValueRange inputValues = new ValueRange()
        inputValues.setRange(range)
        List<List<Object>> valueList = new ArrayList<>()
        valueList.add(rowData)
        inputValues.setValues(valueList)
        service.spreadsheets().values().append(spreadsheetId, range, inputValues).setValueInputOption("RAW").execute()
    }

    static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Sheets service = getSheetsService()

        // Prints the names and majors of students in a sample spreadsheet:
        // https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit

        String range = "Class Data!A2:E"
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues()
//        if (values == null || values.size() == 0) {
//            System.out.println("No data found.")
//        } else {
//            System.out.println("Name, Major")
//            for (List row : values) {
//                // Print columns A and E, which correspond to indices 0 and 4.
//                System.out.printf("%s, %s\n", row.get(0), row.get(4))
//            }
//        }

        //update test
//        ValueRange inputValues = new ValueRange();
//        inputValues.setRange("Class Data!A2");
//        List<Object> list = new ArrayList<>();
//        list.add("ccc");
//        List<List<Object>> valueList = new ArrayList<>();
//        valueList.add(list);
//        inputValues.setValues(valueList);
//        service.spreadsheets().values().update(spreadsheetId, "Class Data!A2", inputValues).setValueInputOption("RAW").execute();

        //append test
        ValueRange inputValues = new ValueRange();
        inputValues.setRange("Class Data!A2");
        List<Object> list = new ArrayList<>();
        list.add("ddd");
        List<List<Object>> valueList = new ArrayList<>();
        valueList.add(list);
        inputValues.setValues(valueList);
        service.spreadsheets().values().append(spreadsheetId, "Class Data!A2", inputValues).setValueInputOption("RAW").execute();

    }
}
