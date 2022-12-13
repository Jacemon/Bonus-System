package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class PayBonusesController implements Initializable {
    @FXML
    private Label bonuses;
    
    @FXML
    private Button buttonPayBonuses;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private ComboBox<EmployeeDto> comboBoxEmployee;
    
    @FXML
    private CheckBox makePdf;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void payBonusesAction() {
        EmployeeDto employee = comboBoxEmployee.getValue();
        String status;
        Float amount;
        if (employee == null) {
            if (makePdf.isSelected()) {
                makePdfAction();
            }
            amount = EmployeeFunctions.payBonuses(null);
            if (amount == null) {
                status = "Бонусы не были выплачены";
            } else {
                status = "Бонусы были выплачены";
            }
        } else {
            amount = EmployeeFunctions.payBonuses(employee.getId());
            if (amount == null) {
                status = "Бонусы не были выплачены";
            } else {
                status = "Бонусы были выплачены";
            }
        }
        System.out.println(status);
        labelStatus.setText(status);
        reloadBonusesAction();
    }
    
    void makeTxtAction() {
        String path = System.getProperty("user.home") +
            File.separator + "Documents";
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение отчёта");
        fileChooser.setInitialFileName("Отчёт по выплате премий за "
            + Calendar.getInstance().get(Calendar.YEAR) + " год");
        fileChooser.setInitialDirectory(new File(path));
        
        FileChooser.ExtensionFilter extensionFilter =
            new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        
        try {
            File file = fileChooser.showSaveDialog(StageManager.getStage("payBonuses"));
            
            if (file != null) {
                System.out.println(file);
                
                List<EmployeeDto> employees = EmployeeFunctions.readAllEmployees();
                assert employees != null;
                
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("Отчёт по выплате премий за "
                        + Calendar.getInstance().get(Calendar.YEAR) + " год");
                    writer.write('\n');
                    writer.write('\n');
                    
                    for (EmployeeDto employee : employees) {
                        writer.write(employee.getLastName() + " " + employee.getFirstName() + '\t' +
                            EmployeeFunctions.calculateBonuses(employee.getId(), true) + "$");
                        writer.write('\n');
                    }
                    writer.write('\n');
                    writer.write("Итого:\t" +
                        EmployeeFunctions.calculateBonuses(null, true) + "$");
                }
            } else {
                System.out.println("Файл не выбран");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Ошибка сохранения файла");
        }
    }
    
    void makePdfAction() {
        String path = System.getProperty("user.home") +
            File.separator + "Documents";
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение отчёта");
        fileChooser.setInitialFileName("Отчёт по выплате премий за "
            + Calendar.getInstance().get(Calendar.YEAR) + " год");
        fileChooser.setInitialDirectory(new File(path));
        
        FileChooser.ExtensionFilter extensionFilter =
            new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extensionFilter);
        
        try {
            File file = fileChooser.showSaveDialog(StageManager.getStage("payBonuses"));
            if (file != null) {
                System.out.println(file);
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(file));
                
                List<EmployeeDto> employees = EmployeeFunctions.readAllEmployees();
                assert employees != null;
                
                document.open();
                
                BaseFont baseFont = BaseFont.createFont(Objects.requireNonNull(
                    getClass().getResource("/by/jcompany/bonus_system/font/times.ttf")).toURI().toString(),
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                
                Font font = new Font(baseFont, 16, Font.NORMAL);
                Font headerFont = new Font(baseFont, 20, Font.BOLD);
                
                document.add(new Paragraph("Отчёт по выплате премий за "
                    + Calendar.getInstance().get(Calendar.YEAR) + " год", headerFont));
                document.add(new Paragraph("\n", headerFont));
                
                PdfPTable table = new PdfPTable(3);
                Stream.of("Фамилия", "Имя", "Годовая премия")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(2);
                        header.setPhrase(new Phrase(columnTitle));
                        table.addCell(header);
                    });
                
                for (EmployeeDto employee : employees) {
                    table.addCell(new Phrase(employee.getLastName(), font));
                    table.addCell(new Phrase(employee.getFirstName(), font));
                    table.addCell(new Phrase(EmployeeFunctions.calculateBonuses(employee.getId(), true) + "$", font));
                }
                table.addCell(new Phrase("Итого:", font));
                table.addCell(new Phrase());
                table.addCell(new Phrase(EmployeeFunctions.calculateBonuses(null, true) + "$", font));
                
                document.add(table);
                document.close();
            } else {
                System.out.println("Файл не выбран");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Ошибка сохранения файла");
        }
    }
    
    @FXML
    void reloadBonusesAction() {
        Float amount;
        EmployeeDto employee = comboBoxEmployee.getValue();
        if (employee == null) {
            amount = EmployeeFunctions.calculateBonuses(null, true);
            makePdf.setVisible(true);
        } else {
            amount = EmployeeFunctions.calculateBonuses(employee.getId(), true);
            makePdf.setVisible(false);
        }
        if (amount != null) {
            bonuses.setText(amount + "$");
        } else {
            bonuses.setText("");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxEmployee.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeDto employeeDto) {
                return "[" + employeeDto.getId() + "] " + employeeDto.getFirstName() + " " +
                    employeeDto.getLastName();
            }
            
            @Override
            public EmployeeDto fromString(String string) {
                return null;
            }
        });
        
        List<EmployeeDto> employeesDto = EmployeeFunctions.readAllEmployees();
        if (employeesDto != null) {
            for (EmployeeDto employeeDto : employeesDto) {
                employeeDto.setTasks(null);
            }
            employeesDto.add(0, null);
            comboBoxEmployee.setItems(FXCollections.observableArrayList(employeesDto));
        } else {
            comboBoxEmployee.setItems(FXCollections.observableArrayList());
        }
        reloadBonusesAction();
    }
}
