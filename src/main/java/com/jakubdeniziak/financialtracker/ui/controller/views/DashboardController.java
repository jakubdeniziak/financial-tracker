package com.jakubdeniziak.financialtracker.ui.controller.views;

import com.jakubdeniziak.financialtracker.ui.controller.elements.NavigationController;
import com.jakubdeniziak.financialtracker.util.CurrencyFormatter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class DashboardController implements Initializable {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final NavigationController navigationController;
    private final CurrencyFormatter currencyFormatter;

    @FXML private Label netWorth;
    @FXML private Label totalAssets;
    @FXML private Label totalLiabilities;
    @FXML private Label totalInvestments;
    @FXML private Label totalAvailable;
    @FXML private Label realizedGains;
    @FXML private Label unrealizedGains;
    @FXML private LineChart<String, Double> netWorthOverTimeChart;
    @FXML private PieChart investedAvailableChart;
    @FXML private PieChart assetCategoryChart;
    @FXML private LineChart<String, Double> expensesChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerCurrencyObservers();
        updateSummaryLabels();
        updateNetWorthOverTimeChart();
        updateInvestedAvailableChart();
        updateAssetCategoryChart();
        updateExpensesChart();
    }

    private void registerCurrencyObservers() {
        navigationController.registerCurrencyObserver(this::updateSummaryLabels);
        navigationController.registerCurrencyObserver(this::updateNetWorthOverTimeChart);
        navigationController.registerCurrencyObserver(this::updateInvestedAvailableChart);
        navigationController.registerCurrencyObserver(this::updateAssetCategoryChart);
        navigationController.registerCurrencyObserver(this::updateExpensesChart);
    }

    private void updateSummaryLabels() {
        // TODO: get values from actual services
        String currency = navigationController.getChosenCurrency();
        netWorth.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        totalAssets.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        totalLiabilities.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        totalInvestments.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        totalAvailable.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        realizedGains.setText(currencyFormatter.format(BigDecimal.ONE, currency));
        unrealizedGains.setText(currencyFormatter.format(BigDecimal.ONE, currency));
    }

    private void updateNetWorthOverTimeChart() {
        // TODO: get values from actual services
        Map<ZonedDateTime, BigDecimal> data = Map.of(
                ZonedDateTime.parse("2025-08-01T00:00:00Z"), new BigDecimal("100"),
                ZonedDateTime.parse("2025-08-02T00:00:00Z"), new BigDecimal("125"),
                ZonedDateTime.parse("2025-08-03T00:00:00Z"), new BigDecimal("110")
        );
        netWorthOverTimeChart.getData().clear();
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.getData().addAll(convertToPieChartData(data));
        netWorthOverTimeChart.getData().add(series);
    }

    private void updateInvestedAvailableChart() {
        // TODO: get values from actual services
        BigDecimal totalInvested = BigDecimal.TWO;
        BigDecimal totalAvailable = BigDecimal.TEN;
        investedAvailableChart.getData().clear();
        PieChart.Data investedSlice = new PieChart.Data("Invested", totalInvested.doubleValue());
        PieChart.Data availableSlice = new PieChart.Data("Available", totalAvailable.doubleValue());
        investedAvailableChart.getData().addAll(investedSlice, availableSlice);
    }

    private void updateAssetCategoryChart() {
        // TODO: get values from actual services
        Map<String, BigDecimal> assetsByCategory = Map.of(
                "Stocks", new BigDecimal("100"),
                "Bonds", new BigDecimal("150"),
                "Crypto", new BigDecimal("100")
        );
        assetCategoryChart.getData().clear();
        List<PieChart.Data> pieChartSlices = assetsByCategory.entrySet().stream()
                .map(assetCategory -> new PieChart.Data(assetCategory.getKey(), assetCategory.getValue().doubleValue()))
                .toList();
        assetCategoryChart.getData().addAll(pieChartSlices);
    }

    private void updateExpensesChart() {
        // TODO: get values from actual services
        Map<ZonedDateTime, BigDecimal> expensesOverTime = Map.of(
                ZonedDateTime.parse("2025-08-01T00:00:00Z"), new BigDecimal("100"),
                ZonedDateTime.parse("2025-09-01T00:00:00Z"), new BigDecimal("125"),
                ZonedDateTime.parse("2025-10-01T00:00:00Z"), new BigDecimal("110")
        );
        Map<ZonedDateTime, BigDecimal> sixMonthExpenseAverage = Map.of(
                ZonedDateTime.parse("2025-08-01T00:00:00Z"), new BigDecimal("80"),
                ZonedDateTime.parse("2025-09-01T00:00:00Z"), new BigDecimal("150"),
                ZonedDateTime.parse("2025-10-01T00:00:00Z"), new BigDecimal("80")
        );
        expensesChart.getData().clear();
        XYChart.Series<String, Double> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Expenses");
        expensesSeries.getData().addAll(convertToPieChartData(expensesOverTime));
        XYChart.Series<String, Double> expensesAverageSeries = new XYChart.Series<>();
        expensesAverageSeries.setName("6 month average");
        expensesAverageSeries.getData().addAll(convertToPieChartData(sixMonthExpenseAverage));
        expensesChart.getData().addAll(List.of(expensesSeries, expensesAverageSeries));
    }

    private List<XYChart.Data<String, Double>> convertToPieChartData(Map<ZonedDateTime, BigDecimal> data) {
        return data.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new XYChart.Data<>(
                        entry.getKey().format(FORMATTER),
                        entry.getValue().doubleValue()))
                .toList();
    }

}
