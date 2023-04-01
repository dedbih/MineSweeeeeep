module com.example.minesweep1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.minesweep1 to javafx.fxml;
    exports com.example.minesweep1;
}