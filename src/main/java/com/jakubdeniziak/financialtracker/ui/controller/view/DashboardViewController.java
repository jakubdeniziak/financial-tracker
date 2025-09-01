package com.jakubdeniziak.financialtracker.ui.controller.view;

import com.jakubdeniziak.financialtracker.domain.Expense;
import com.jakubdeniziak.financialtracker.service.ExpenseService;
import com.jakubdeniziak.financialtracker.ui.controller.element.NavigationViewController;
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
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Component
@RequiredArgsConstructor
public class DashboardViewController implements Initializable {

    private static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final int DEFAULT_EXPENSE_AVERAGE_PERIOD = 6;

    private final ExpenseService expenseService;
    private final CurrencyFormatter currencyFormatter;
    private final NavigationViewController navigationViewController;

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
        navigationViewController.registerCurrencyObserver(this::updateSummaryLabels);
        navigationViewController.registerCurrencyObserver(this::updateNetWorthOverTimeChart);
        navigationViewController.registerCurrencyObserver(this::updateInvestedAvailableChart);
        navigationViewController.registerCurrencyObserver(this::updateAssetCategoryChart);
        navigationViewController.registerCurrencyObserver(this::updateExpensesChart);
    }

    private void updateSummaryLabels() {
        // TODO: get values from actual services
        String currency = navigationViewController.getChosenCurrency();
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
        Map<YearMonth, BigDecimal> data = Map.of(
                YearMonth.parse("2025-08"), new BigDecimal("100"),
                YearMonth.parse("2025-09"), new BigDecimal("125"),
                YearMonth.parse("2025-10"), new BigDecimal("110")
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
        expensesChart.getData().clear();
        XYChart.Series<String, Double> expensesSeries = new XYChart.Series<>();
        expensesSeries.setName("Expenses");
        expensesSeries.getData().addAll(convertToPieChartData(expenseService.readAll()));
        XYChart.Series<String, Double> expensesAverageSeries = new XYChart.Series<>();
        expensesAverageSeries.setName("6 month average");
        expensesAverageSeries.getData().addAll(convertToPieChartData(expenseService.calculateRollingAverage(DEFAULT_EXPENSE_AVERAGE_PERIOD)));
        expensesChart.getData().addAll(List.of(expensesSeries, expensesAverageSeries));
    }

    private List<XYChart.Data<String, Double>> convertToPieChartData(List<Expense> expenses) {
        return expenses.stream()
                .sorted(Comparator.comparing(Expense::getYearMonth))
                .map(expense -> new XYChart.Data<>(
                        expense.getYearMonth().format(YEAR_MONTH_FORMATTER),
                        expense.getAmount().doubleValue()
                ))
                .toList();
    }

    private List<XYChart.Data<String, Double>> convertToPieChartData(Map<YearMonth, BigDecimal> data) {
        return data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> new XYChart.Data<>(
                        entry.getKey().format(YEAR_MONTH_FORMATTER),
                        entry.getValue().doubleValue()))
                .toList();
    }

}
